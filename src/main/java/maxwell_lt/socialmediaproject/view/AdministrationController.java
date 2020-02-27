package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.CommentNotFoundException;
import maxwell_lt.socialmediaproject.exception.UserNotFoundException;
import maxwell_lt.socialmediaproject.modelassembler.CommentModelAssembler;
import maxwell_lt.socialmediaproject.modelassembler.PostModelAssembler;
import maxwell_lt.socialmediaproject.modelassembler.UserModelAssembler;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdministrationController {

    private PostService postService;
    private UserService userService;
    private CommentService commentService;

    private UserModelAssembler userModelAssembler;
    private PostModelAssembler postModelAssembler;
    private CommentModelAssembler commentModelAssembler;

    @Autowired
    public AdministrationController(PostService postService,
                                    UserService userService,
                                    CommentService commentService,
                                    UserModelAssembler userModelAssembler,
                                    PostModelAssembler postModelAssembler,
                                    CommentModelAssembler commentModelAssembler) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.userModelAssembler = userModelAssembler;
        this.postModelAssembler = postModelAssembler;
        this.commentModelAssembler = commentModelAssembler;
    }

    @GetMapping("/moderator/users")
    public CollectionModel<EntityModel<User>> getAllUsers() {
        return userModelAssembler.toCollectionModel(userService.getAllUsers());
    }

    @GetMapping("/moderator/users/{id}")
    public EntityModel<User> getUserDetails(@PathVariable("id") int userId) {
        return userModelAssembler.toModel(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @GetMapping("/moderator/posts")
    public CollectionModel<EntityModel<Post>> getAllPosts() {
        return postModelAssembler.toCollectionModel(postService.getPosts());
    }

    @GetMapping("/moderator/posts/{id}")
    public EntityModel<Post> getPostDetails(@PathVariable("id") int postId) {
        return postModelAssembler.toModel(postService.getPostById(postId));
    }

    @DeleteMapping("/moderator/posts/{id}")
    public EntityModel<Post> deletePost(@PathVariable("id") int postId) {
        postService.deletePost(postId);
        return postModelAssembler.toModel(postService.getPostById(postId));
    }

    @GetMapping("/moderator/comments")
    public CollectionModel<EntityModel<Comment>> getAllComments() {
        return commentModelAssembler.toCollectionModel(commentService.getAllComments());
    }

    @GetMapping("/moderator/user/{id}/comments")
    public CollectionModel<EntityModel<Comment>> getCommentsByUser(@PathVariable("id") int userId) {
        return commentModelAssembler.toCollectionModel(commentService.getCommentsByUser(userId));
    }

    @GetMapping("/moderator/user/{id}/posts")
    public CollectionModel<EntityModel<Post>> getPostsByUser(@PathVariable("id") int userId) {
        return postModelAssembler.toCollectionModel(postService.getPostsByUser(userId));
    }

    @GetMapping("/moderator/post/{id}/comments")
    public CollectionModel<EntityModel<Comment>> getCommentsByPost(@PathVariable("id") int postId) {
        return commentModelAssembler.toCollectionModel(commentService.getCommentsByPost(postId));
    }

    @GetMapping("/moderator/comments/{id}")
    public EntityModel<Comment> getCommentDetails(@PathVariable("id") int commentId) {
        return commentModelAssembler.toModel(commentService.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId)));
    }

    @DeleteMapping("/moderator/comments/{id}")
    public EntityModel<Comment> deleteComment(@PathVariable("id") int commentId) {
        commentService.deleteComment(commentId);
        return commentModelAssembler.toModel(commentService.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId)));

    }

    @PutMapping("/admin/setrole/{id}")
    public EntityModel<User> setRole(
            @PathVariable("id") int userId,
            @RequestParam("role") String role) {

        if ("admin".equals(role)) {
            userService.setAdminRole(userId, true);
        }
        if ("moderator".equals(role)) {
            userService.setModeratorRole(userId, true);
        }
        return userModelAssembler.toModel(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @DeleteMapping("/admin/users/{id}")
    public EntityModel<User> deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
        return userModelAssembler.toModel(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }
}
