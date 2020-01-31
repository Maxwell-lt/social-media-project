package maxwell_lt.socialmediaproject.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Documented
public @interface PasswordStrength {
    String message() default "Password too weak";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
