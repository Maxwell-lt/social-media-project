package maxwell_lt.socialmediaproject.exception;

public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1561163686774176311L;

    public PostNotFoundException(int id) {
        super(String.format("Post with ID %d does not exist.", id));
    }

    public PostNotFoundException() {
        super("Post does not exist.");
    }
}
