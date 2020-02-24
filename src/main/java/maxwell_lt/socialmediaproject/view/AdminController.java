package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/allusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/admin/setrole/{id}")
    public @ResponseBody
    boolean setRole(
            @PathVariable("id") int userId,
            @RequestParam("role") String role) {
        try {
            if ("admin".equals(role)) {
                userService.setAdminRole(userId, true);
            } else if ("moderator".equals(role)) {
                userService.setModeratorRole(userId, true);
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    @GetMapping("/admin/userdetails/{id}")
    public User getUserDetails(@PathVariable("id") int userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }
}
