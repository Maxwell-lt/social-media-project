package maxwell_lt.socialmediaproject.utilities;

import com.nulabinc.zxcvbn.Zxcvbn;
import maxwell_lt.socialmediaproject.dto.UserDTO;
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
    private Zxcvbn zxcvbn;

    @Autowired
    public UserUtil(PasswordEncoder passwordEncoder, Zxcvbn zxcvbn) {
        this.passwordEncoder = passwordEncoder;
        this.zxcvbn = zxcvbn;
    }

    public boolean isUserDTOValid(UserDTO user) {
        return (zxcvbn.measure(user.getRawPassword()).getScore() > 2
                || user.getUsername().length() > 50
                || user.getEmail().length() > 254);
    }

    public User createUserFromUserDTO(UserDTO userDTO) {
        String hashedPassword = passwordEncoder.encode(userDTO.getRawPassword());

        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(hashedPassword);
        userEntity.setCurrentLikes(new BigDecimal(0));
        userEntity.setCreationDate(Timestamp.from(Instant.now()));
        return userEntity;
    }
}
