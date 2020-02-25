package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.Pack;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.NotAuthenticatedException;
import maxwell_lt.socialmediaproject.service.PurchaseService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PurchaseController {

    private PurchaseService purchaseService;
    private UserUtil userUtil;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, UserUtil userUtil) {
        this.purchaseService = purchaseService;
        this.userUtil = userUtil;
    }

    @GetMapping("/purchase")
    public ModelAndView purchasePage() {
        ModelAndView mav = new ModelAndView("purchase");
        mav.addObject("packs", Pack.values());
        return mav;
    }

    @PostMapping("/purchase")
    public ModelAndView purchaseLikes(@RequestParam("size") Pack likePack) {
        User user = userUtil.getCurrentUser()
                .orElseThrow(NotAuthenticatedException::new);

        purchaseService.buyLikes(user, likePack.getSize(), likePack.getPrice());
        return new ModelAndView("redirect:/");
    }
}
