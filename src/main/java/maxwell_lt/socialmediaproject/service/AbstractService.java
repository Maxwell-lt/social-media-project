package maxwell_lt.socialmediaproject.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Closeable;

public abstract class AbstractService implements Closeable {
    protected EntityManagerFactory emf;
    protected EntityManager em;

    public AbstractService() {
        this.emf = Persistence.createEntityManagerFactory("social-media-persistence");
        this.em = emf.createEntityManager();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
