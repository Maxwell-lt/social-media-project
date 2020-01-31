package maxwell_lt.socialmediaproject.validator;

import com.nulabinc.zxcvbn.Zxcvbn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

   @Override
   public void initialize(PasswordStrength constraint) {
   }

   @Override
   public boolean isValid(String password, ConstraintValidatorContext context) {
      return new Zxcvbn().measure(password).getScore() > 2;
   }
}
