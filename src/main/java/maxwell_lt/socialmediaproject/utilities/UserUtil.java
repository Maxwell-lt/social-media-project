package maxwell_lt.socialmediaproject.utilities;

import maxwell_lt.socialmediaproject.dto.UserRegistrationForm;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserUtil {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User createUserFromRegistrationForm(UserRegistrationForm userRegistrationForm) {
        String hashedPassword = passwordEncoder.encode(userRegistrationForm.getRawPassword());

        User userEntity = new User();
        userEntity.setUsername(userRegistrationForm.getUsername());
        userEntity.setEmail(userRegistrationForm.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setCurrentLikes(new BigDecimal(0));
        userEntity.setCreationDate(Timestamp.from(Instant.now()));
        return userEntity;
    }
}
