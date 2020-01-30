package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Purchase;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findAllByUser(User user);

    List<Purchase> findAllByTimestampBefore(Timestamp timestamp);

    List<Purchase> findAllByTimestampAfter(Timestamp timestamp);

    List<Purchase> findAllByTimestampBetween(Timestamp t1, Timestamp t2);
}
