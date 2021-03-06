package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return commentRepository.findAllByUserAndDeletedFalse(user);
    }

    public Page<Comment> getCommentsByPost(Post post, Pageable pageable) {
        return commentRepository.findAllByPostAndDeletedFalse(post, pageable);
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Page<Comment> getCommentsAsPageByUser(User user, Pageable pageable) {
        return commentRepository.findAllByUserAndDeletedFalse(user, pageable);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findAllByPostAndDeletedFalse(post);
    }

    @Transactional
    public void deleteComment(int commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    public List<Comment> getCommentsByUser(int userId) {
        return commentRepository.findAllByUserIdAndDeletedFalse(userId);
    }

    public List<Comment> getCommentsByPost(int postId) {
        return commentRepository.findAllByPostIdAndDeletedFalse(postId);
    }
}
