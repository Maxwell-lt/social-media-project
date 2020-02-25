package maxwell_lt.socialmediaproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String postNotFoundHandler(PostNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String commentNotFoundHandler(CommentNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AuthorityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String authorityNotFoundHandler(AuthorityNotFoundException e) {
        return e.getMessage();
    }
}
