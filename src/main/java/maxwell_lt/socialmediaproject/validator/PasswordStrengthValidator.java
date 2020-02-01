package maxwell_lt.socialmediaproject.validator;

import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

    @Autowired
    Zxcvbn zxcvbn;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return zxcvbn.measure(password).getScore() > 2;
    }
}
