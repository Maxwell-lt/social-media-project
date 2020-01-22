package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class UserService extends AbstractService {
    public static void createUser(User user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
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
}
