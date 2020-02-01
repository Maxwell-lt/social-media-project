package maxwell_lt.socialmediaproject.validator;

import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameNotExistValidator implements ConstraintValidator<UsernameNotExist, String> {

    @Autowired
    UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.getUserByUsername(username).isPresent();
    }
}
