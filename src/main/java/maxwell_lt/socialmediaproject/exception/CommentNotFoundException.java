package maxwell_lt.socialmediaproject.exception;

public class CommentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2057209203592613167L;

    public CommentNotFoundException(int commentId) {
        super(String.format("Comment with ID %d does not exist.", commentId));
    }
}
