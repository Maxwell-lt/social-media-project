package maxwell_lt.socialmediaproject.exception;

import org.springframework.security.core.GrantedAuthority;

public class NotAuthenticatedException extends RuntimeException {

    private static final long serialVersionUID = -578660757314746728L;

    public NotAuthenticatedException() {
        super("Must be authenticated to access this page.");
    }

    public NotAuthenticatedException(GrantedAuthority authority) {
        super(String.format("Must have role %s to access this page.", authority.getAuthority()));
    }
}
