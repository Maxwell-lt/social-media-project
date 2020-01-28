package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class UserService extends AbstractService {
    public void createUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public Optional<User> getUserById(int id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @SuppressWarnings("unchecked")
    public Optional<User> getUserByUsername(String username) {
        Query getByUsername = em.createNamedQuery("findUserByUsername");
        getByUsername.setParameter("username", username);
        List<User> results = getByUsername.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @SuppressWarnings("unchecked")
    public Optional<User> getUserByEmail(String email) {
        Query getByEmail = em.createNamedQuery("findUserByEmail");
        getByEmail.setParameter("email", email);
        List<User> results = getByEmail.getResultList();
        getByEmail.getSingleResult();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public boolean updateUsername(int id, String username) {
        if (getUserByUsername(username).isPresent()) {
            return false;
        }
        User user = em.find(User.class, id);
        if (user != null) {
            user.setUsername(username);
            em.getTransaction().commit();
            return true;
        } else {
            em.getTransaction().rollback();
            return false;
        }
    }

    public boolean updateEmail(int id, String email) {
        if (getUserByEmail(email).isPresent()) {
            return false;
        }
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            user.setEmail(email);
            em.getTransaction().commit();
            return true;
        } else {
            em.getTransaction().rollback();
            return false;
        }
    }
}
