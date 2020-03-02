package maxwell_lt.socialmediaproject.utilities;

import com.nulabinc.zxcvbn.Zxcvbn;
import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.dto.UserRegistrationForm;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserUtil {

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private Zxcvbn zxcvbn;

    @Autowired
    public UserUtil(PasswordEncoder passwordEncoder, UserService userService, Zxcvbn zxcvbn) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.zxcvbn = zxcvbn;
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

    public boolean validateUsername(String username) {
        return (username != null &&
                !username.isEmpty() &&
                username.length() <= 50 &&
                !userService.getUserByUsername(username).isPresent());
    }

    public boolean validateEmail(String email) {
        boolean validAddress;
        try {
            new InternetAddress(email).validate();
            validAddress = true;
        } catch (AddressException e) {
            validAddress = false;
        }
        return (email != null &&
                !email.isEmpty() &&
                email.length() <= 254 &&
                validAddress &&
                !userService.getUserByEmail(email).isPresent());
    }

    public boolean validatePassword(String password) {
        return (password != null &&
                !password.isEmpty() &&
                password.length() <= 48 &&
                zxcvbn.measure(password).getScore() > 2);
    }
}
