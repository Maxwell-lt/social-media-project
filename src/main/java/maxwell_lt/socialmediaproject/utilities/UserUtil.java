package maxwell_lt.socialmediaproject.utilities;

import com.nulabinc.zxcvbn.Zxcvbn;
import maxwell_lt.socialmediaproject.dto.UserDTO;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

public class UserUtil {
    public static boolean isUserDTOValid(UserDTO user) {
        Zxcvbn passwordTester = new Zxcvbn();
        return passwordTester.measure(user.getRawPassword()).getScore() > 2
                || user.getUsername().length() > 50
                || user.getEmail().length() > 254;
    }

    public static User createUserFromUserDTO(UserDTO userDTO) {
        String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.getRawPassword());

        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setCurrentLikes(new BigDecimal(0));
        userEntity.setCreationDate(Timestamp.from(Instant.now()));
        return userEntity;
    }
}
