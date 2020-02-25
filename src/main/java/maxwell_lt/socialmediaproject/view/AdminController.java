package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.AuthorityNotFoundException;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/admin/setrole/{id}")
    public User setRole(
            @PathVariable("id") int userId,
            @RequestParam("role") String role) {

        if ("admin".equals(role)) {
            return userService.setAdminRole(userId, true);
        }
        if ("moderator".equals(role)) {
            return userService.setModeratorRole(userId, true);
        }
        throw new AuthorityNotFoundException(role);
    }

    @DeleteMapping("/admin/deleteuser/{id}")
    public void deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
    }
}
