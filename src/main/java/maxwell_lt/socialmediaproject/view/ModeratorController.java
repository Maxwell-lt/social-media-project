package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.CommentNotFoundException;
import maxwell_lt.socialmediaproject.exception.UserNotFoundException;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModeratorController {

    private PostService postService;
    private UserService userService;
    private CommentService commentService;

    @Autowired
    public ModeratorController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/moderator/allusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/moderator/userdetails/{id}")
    public User getUserDetails(@PathVariable("id") int userId) {
        return userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping("/moderator/allposts")
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }

    @GetMapping("/moderator/postdetails/{id}")
    public Post getPostDetails(@PathVariable("id") int postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/moderator/deletepost/{id}")
    public void deletePost(@PathVariable("id") int postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/moderator/allcomments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/moderator/commentsbyuser/{id}")
    public List<Comment> getCommentsByUser(@PathVariable("id") int userId) {
        return commentService.getCommentsByUser(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @GetMapping("/moderator/commentsbypost/{id}")
    public List<Comment> getCommentsByPost(@PathVariable("id") int postId) {
        return commentService.getCommentsByPost(postService.getPostById(postId));
    }

    @GetMapping("/moderator/commentdetails/{id}")
    public Comment getCommentDetails(@PathVariable("id") int commentId) {
        return commentService.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @DeleteMapping("/moderator/deletecomment/{id}")
    public void deleteComment(@PathVariable("id") int commentId) {
        commentService.deleteComment(commentId);
    }
}
