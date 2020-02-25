package maxwell_lt.socialmediaproject.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8936360229690544513L;

    public UserNotFoundException(int id) {
        super(String.format("User with ID %d does not exist.", id));
    }

    public UserNotFoundException() {
        super("User does not exist.");
    }
}
