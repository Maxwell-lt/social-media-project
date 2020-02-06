package maxwell_lt.socialmediaproject.validator;

import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostExistValidator implements ConstraintValidator<PostExist, Integer> {

    @Autowired
    UserService userService;

    @Override
    public boolean isValid(Integer postId, ConstraintValidatorContext constraintValidatorContext) {
        return userService.getUserById(postId).isPresent();
    }
}
