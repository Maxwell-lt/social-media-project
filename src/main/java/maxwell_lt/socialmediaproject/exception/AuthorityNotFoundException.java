package maxwell_lt.socialmediaproject.exception;

public class AuthorityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5435901040700089474L;

    public AuthorityNotFoundException(String role) {
        super(String.format("Role %s does not exist.", role));
    }
}
