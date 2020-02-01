package maxwell_lt.socialmediaproject.validator;

import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotExistValidator implements ConstraintValidator<EmailNotExist, String> {

    @Autowired
    UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.getUserByEmail(email).isPresent();
    }
}
