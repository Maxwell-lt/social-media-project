package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.dto.UserRegistrationForm;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserUtil {

    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public UserUtil(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public User createUserFromRegistrationForm(UserRegistrationForm userRegistrationForm) {
        String hashedPassword = passwordEncoder.encode(userRegistrationForm.getRawPassword());

        User userEntity = new User();
        userEntity.setUsername(userRegistrationForm.getUsername());
        userEntity.setEmail(userRegistrationForm.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setCurrentLikes(new BigDecimal(1000));
        userEntity.setCreationDate(Timestamp.from(Instant.now()));
        return userEntity;
    }

    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            int userId = ((UserPrincipal) principal).getUser().getId();
            return userService.getUserById(userId);
        }
        return Optional.empty();
    }
}
