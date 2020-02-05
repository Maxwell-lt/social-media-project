package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.UserService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class AccountController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private PrettyTime prettyTime;

    @Autowired
    public AccountController(UserService userService, PostService postService, CommentService commentService, PrettyTime prettyTime) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.prettyTime = prettyTime;
    }

    @GetMapping("/account/{user}")
    public ModelAndView accountInfo(@PathVariable(value = "user") int userId) {
        return getModelFromUserId(userId);
    }

    @GetMapping("/account")
    public ModelAndView myAccountInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = null;
        if (principal instanceof UserPrincipal) {
            currentUser = ((UserPrincipal) principal).getUser();
            if (currentUser != null) {
                return getModelFromUserId(currentUser.getId());
            }
        }
        return new ModelAndView("account");
    }

    private ModelAndView getModelFromUserId(int userId) {
        ModelAndView mav = new ModelAndView("account");

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Collection<Post> posts = postService.getPostsByUser(user);
            Collection<Comment> comments = commentService.getCommentsByUser(user);
            mav.addObject("user", user);
            mav.addObject("posts", posts);
            mav.addObject("comments", comments);
            mav.addObject(prettyTime);
        }
        return mav;
    }
}
