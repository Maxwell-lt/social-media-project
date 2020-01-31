package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.UserRegistrationForm;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;
    private UserUtil userUtil;

    @Autowired
    public RegisterController(UserService userService, UserUtil userUtil) {
        this.userService = userService;
        this.userUtil = userUtil;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registrationform", new UserRegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute @Valid UserRegistrationForm userRegistrationForm,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/register";
        }

        User userEntity = userUtil.createUserFromRegistrationForm(userRegistrationForm);
        userService.createUser(userEntity);

        return "index";
    }
}
