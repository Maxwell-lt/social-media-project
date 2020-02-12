package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Purchase;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PurchaseRepository;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(0);
        user.setCurrentLikes(new BigDecimal("0"));

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void whenUserMakesPurchase_ThenPurchaseIsSavedAndUserHasMoreLikes() {
        BigDecimal price = new BigDecimal("0.99");
        purchaseService.buyLikes(user, 100, price);

        verify(purchaseRepository, times(1))
                .save(argThat(p ->
                        p.getLikesBought() == 100 &&
                                p.getPricePaid().compareTo(price) == 0));

        verify(userRepository, times(1))
                .save(user);

        assertThat(user.getCurrentLikes())
                .isEqualByComparingTo("100");
    }

    @Test
    void givenUserHasPurchases_WhenGetPurchasesByUser_ThenGetPurchases() {
        Purchase purchase = new Purchase();
        purchase.setId(0);
        purchase.setUser(user);

        when(purchaseRepository.findAllByUser(user))
                .thenReturn(Lists.list(purchase));

        assertThat(purchaseService.getPurchasesByUser(user))
                .isNotEmpty()
                .contains(purchase);
    }

    @Test
    void givenUserHasNoPurchases_WhenGetPurchasesByUser_ThenGetEmptyList() {
        when(purchaseRepository.findAllByUser(user))
                .thenReturn(Lists.emptyList());

        assertThat(purchaseService.getPurchasesByUser(user))
                .isEmpty();
    }
}