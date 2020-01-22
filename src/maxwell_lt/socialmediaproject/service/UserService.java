package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static maxwell_lt.socialmediaproject.service.UpdateResult.*;

public class UserService extends AbstractService {
    public static UpdateResult createUser(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();
        } catch (EntityExistsException e) {
            cleanupTransaction(em);
            return DUPLICATE;
        }
        return SUCCESS;
    }

    public static Optional<User> getUserById(int id) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return Optional.ofNullable(user);
    }

    public static Optional<User> getUserByUsername(String username) {
        EntityManager em = getEntityManager();
        Query getByUsername = em.createNamedQuery("findUserByUsername");
        getByUsername.setParameter("username", username);
        List<User> results = getByUsername.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public static Optional<User> getUserByEmail(String email) {
        EntityManager em = getEntityManager();
        Query getByEmail = em.createNamedQuery("findUserByEmail");
        getByEmail.setParameter("email", email);
        List<User> results = getByEmail.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public static UpdateResult updateUsername(int id, String username) {
        if (getUserByUsername(username).isPresent()) {
            return DUPLICATE;
        }
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user == null) {
            em.getTransaction().rollback();
            em.close();
            return NOT_FOUND;
        }
        user.setUsername(username);
        em.getTransaction().commit();
        em.close();
        return SUCCESS;
    }

    public static UpdateResult updateEmail(int id, String email) {
        if (getUserByEmail(email).isPresent()) {
            return DUPLICATE;
        }
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user == null) {
            em.getTransaction().rollback();
            em.close();
            return NOT_FOUND;
        }
        user.setEmail(email);
        em.getTransaction().commit();
        em.close();
        return SUCCESS;
    }
}
