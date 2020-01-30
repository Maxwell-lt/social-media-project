package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findAllByUser(user);
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }
}
