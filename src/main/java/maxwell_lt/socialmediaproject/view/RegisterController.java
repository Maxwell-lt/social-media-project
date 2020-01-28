package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.UserDTO;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userdto", new UserDTO());
        return "register";
    }

    @PostMapping("/newuser")
    public String registerPost(@ModelAttribute UserDTO userDTO) {
        if (!UserUtil.isUserDTOValid(userDTO)) {
            return "index";
        }

        User userEntity = UserUtil.createUserFromUserDTO(userDTO);
        try (UserService us = new UserService()) {
            us.createUser(userEntity);
        }

        return "index";
    }
}