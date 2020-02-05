package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import maxwell_lt.socialmediaproject.service.UserService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AccountController {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private PostlikesService postlikesService;
    private PrettyTime prettyTime;
    private UserUtil userUtil;

    @Autowired
    public AccountController(UserService userService,
                             PostService postService,
                             CommentService commentService,
                             PostlikesService postlikesService,
                             PrettyTime prettyTime,
                             UserUtil userUtil) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.postlikesService = postlikesService;
        this.prettyTime = prettyTime;
        this.userUtil = userUtil;
    }

    @GetMapping("/account/{user}")
    public ModelAndView accountInfo(@PathVariable(value = "user") int userId,
                                    @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "size", defaultValue = "20") int pageSize) {
        Optional<User> currentUser = userUtil.getCurrentUser();
        return getModelFromUserId(userId, currentUser, pageNumber, pageSize);
    }

    @GetMapping("/account")
    public ModelAndView myAccountInfo(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                      @RequestParam(value = "size", defaultValue = "20") int pageSize) {
        Optional<User> currentUser = userUtil.getCurrentUser();
        return currentUser
                .map(user -> getModelFromUserId(user.getId(), Optional.of(user), pageNumber, pageSize))
                .orElseGet(() -> new ModelAndView("account"));
    }

    private ModelAndView getModelFromUserId(int userId, Optional<User> currentUser, int pageNumber, int pageSize) {
        ModelAndView mav = new ModelAndView("account");

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Page<Post> posts = postService.getPostsByUser(user,
                    PageRequest.of(pageNumber - 1, pageSize, Sort.by("timestamp").descending()));
            Collection<Comment> comments = commentService.getCommentsByUser(user);
            Map<Integer, Integer> totalLikes = posts.stream()
                    .collect(Collectors.toMap(Post::getId, postlikesService::getLikes));
            Map<Integer, Integer> myLikes = currentUser.map(value -> posts.stream()
                    .collect(Collectors.toMap(Post::getId,
                            post -> postlikesService.getLikesByUser(post, value))))
                    .orElseGet(HashMap::new);

            mav.addObject("user", user);
            mav.addObject("posts", posts);
            mav.addObject("totallikes", totalLikes);
            mav.addObject("mylikes", myLikes);
            mav.addObject("comments", comments);
            mav.addObject(prettyTime);

            int totalPages = posts.getTotalPages();
            if (totalPages > 0) {
                mav.addObject("pagenumbers", IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList()));
            }
        }
        return mav;
    }
}
