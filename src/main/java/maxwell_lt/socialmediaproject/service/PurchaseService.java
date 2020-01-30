package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Purchase;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void createPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public List<Purchase> getPurchasesByUser(User user) {
        return purchaseRepository.findAllByUser(user);
    }
}
