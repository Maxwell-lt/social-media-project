package maxwell_lt.socialmediaproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotAuthenticatedAdvice {

    @ResponseBody
    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String notAuthenticatedHandler(NotAuthenticatedException e) {
        return e.getMessage();
    }
}
