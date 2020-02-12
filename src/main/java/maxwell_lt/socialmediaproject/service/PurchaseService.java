package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Purchase;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PurchaseRepository;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;
    private UserRepository userRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void buyLikes(User user, int likeCount, BigDecimal price) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setLikesBought(likeCount);
        purchase.setPricePaid(price);
        purchase.setTimestamp(Timestamp.from(Instant.now()));

        user.setCurrentLikes(user.getCurrentLikes().add(new BigDecimal(likeCount)));

        purchaseRepository.save(purchase);
        userRepository.save(user);
    }

    public List<Purchase> getPurchasesByUser(User user) {
        return purchaseRepository.findAllByUser(user);
    }
}
