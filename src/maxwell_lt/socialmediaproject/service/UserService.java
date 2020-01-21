package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;
import org.eclipse.persistence.internal.jpa.querydef.CriteriaQueryImpl;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class UserService {
    private static final String PU_NAME = "social-media-persistence";

    public static void createUser(User user) {
        EntityManager em = Persistence.createEntityManagerFactory(PU_NAME).createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public static Optional<User> getUserById(int id) {
        EntityManager em = Persistence.createEntityManagerFactory(PU_NAME).createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return Optional.ofNullable(user);
    }

    public static Optional<User> getUserByUsername(String username) {
        EntityManager em = Persistence.createEntityManagerFactory(PU_NAME).createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root e = cq.from(User.class);
        cq.where(cb.equal(e.get("username"), username));
        Query query = em.createQuery(cq);
        User user = (User) query.getSingleResult();
        return Optional.ofNullable(user);
    }

}
