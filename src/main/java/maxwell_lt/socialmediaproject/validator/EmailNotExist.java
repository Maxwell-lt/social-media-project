package maxwell_lt.socialmediaproject.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({TYPE, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailNotExistValidator.class)
@Documented
public @interface EmailNotExist {
    String message() default "Email already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
