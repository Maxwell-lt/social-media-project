package maxwell_lt.socialmediaproject.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class AbstractService {
    private static final String PU_NAME = "social-media-persistence";

    protected static EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory(PU_NAME).createEntityManager();
    }

    protected static void cleanupTransaction(EntityManager em) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        if (em.isOpen()) {
            em.close();
        }
    }
}
