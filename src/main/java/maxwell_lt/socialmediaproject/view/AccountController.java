package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
}
