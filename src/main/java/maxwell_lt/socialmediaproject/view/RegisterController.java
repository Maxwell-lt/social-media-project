package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.UserRegistrationForm;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class RegisterController {

    private UserService userService;
    private UserUtil userUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public RegisterController(UserService userService, UserUtil userUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userUtil = userUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String registerGet(@ModelAttribute("registrationform") UserRegistrationForm userRegistrationForm) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("registrationform") @Valid UserRegistrationForm userRegistrationForm,
                               BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "register";
        }

        User userEntity = userUtil.createUserFromRegistrationForm(userRegistrationForm);
        userService.createUser(userEntity);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userRegistrationForm.getUsername(), userRegistrationForm.getRawPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        request.getSession(true).setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return "redirect:/";
    }
}
