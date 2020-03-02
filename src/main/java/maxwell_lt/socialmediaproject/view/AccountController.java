package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Comment;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.NotAuthenticatedException;
import maxwell_lt.socialmediaproject.exception.UserNotFoundException;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import maxwell_lt.socialmediaproject.service.UserService;
import maxwell_lt.socialmediaproject.utilities.PostUtil;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    private UserUtil userUtil;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(UserService userService,
                             PostService postService,
                             CommentService commentService,
                             PostlikesService postlikesService,
                             UserUtil userUtil,
                             PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.postlikesService = postlikesService;
        this.userUtil = userUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/account/{user}")
    public ModelAndView accountInfo(@PathVariable(value = "user") int userId,
                                    @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                    @RequestParam(value = "show", defaultValue = "posts") String show,
                                    @RequestParam(value = "sort", defaultValue = "popular") String sort) {
        Optional<User> currentUser = userUtil.getCurrentUser();
        Sort sortOrder = PostUtil.getSortFromParam(sort);
        return getModelFromUserId(userId, currentUser, pageNumber, pageSize, sortOrder, show);
    }

    @GetMapping("/account")
    public ModelAndView myAccountInfo() {
        Optional<User> currentUser = userUtil.getCurrentUser();
        return currentUser
                .map(user -> new ModelAndView("redirect:/account/" + user.getId()))
                .orElseGet(() -> new ModelAndView("redirect:/login"));
    }

    private static Map<String, Object> getStatusMap(boolean status) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", status);
        return result;
    }

    private ModelAndView getModelFromUserId(int userId, Optional<User> currentUser, int pageNumber, int pageSize, Sort order, String show) {

        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        ModelAndView mav = new ModelAndView(show.equals("posts") ? "account" : "accountcomments");
        User user = userOptional.get();

        Page<Post> posts = order.isUnsorted()
                ? postService.getPostsAsPageByUserByPopularity(user, PageRequest.of(pageNumber - 1, pageSize))
                : postService.getPostsByUser(user, PageRequest.of(pageNumber - 1, pageSize, order));
        Page<Comment> comments = order.isUnsorted()
                ? commentService.getCommentsAsPageByUser(user, PageRequest.of(pageNumber - 1, pageSize))
                : commentService.getCommentsAsPageByUser(user, PageRequest.of(pageNumber - 1, pageSize, order));

        Map<Integer, Integer> totalLikes = posts.stream()
                .collect(Collectors.toMap(Post::getId, postlikesService::getLikes));

        Map<Integer, Integer> myLikes = currentUser.map(value -> posts.stream()
                .collect(Collectors.toMap(Post::getId,
                        post -> postlikesService.getLikesByUser(post, value))))
                .orElseGet(HashMap::new);


        mav.addObject(
                "mypage",
                currentUser.isPresent() && currentUser.get().getId() == userId);

        mav.addObject("pageuser", user);
        mav.addObject("posts", posts);
        mav.addObject("totallikes", totalLikes);
        mav.addObject("mylikes", myLikes);
        mav.addObject("comments", comments);

        int totalPages = show.equals("posts")
                ? posts.getTotalPages()
                : comments.getTotalPages();
        if (totalPages > 0) {
            mav.addObject("pagenumbers", IntStream.rangeClosed(
                    Integer.max(pageNumber - 3, 1),
                    Integer.min(pageNumber + 3, totalPages))
                    .boxed()
                    .collect(Collectors.toList()));
        }
        return mav;
    }

    @PutMapping("/manage_account/password")
    public @ResponseBody
    Map<String, Object> updatePassword(@RequestParam("oldpass") String oldPassword,
                                       @RequestParam("newpass") String newPassword) {
        User user = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new);
        if (!userUtil.validatePassword(newPassword)) {
            return getStatusMap(false);
        }
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            userService.updatePassword(user, passwordEncoder.encode(newPassword));
            return getStatusMap(true);
        }
        return getStatusMap(false);
    }

    @PutMapping("/manage_account/username")
    public @ResponseBody
    Map<String, Object> updateUsername(@RequestParam("username") String username) {
        if (!userUtil.validateUsername(username)) {
            return getStatusMap(false);
        }
        int userId = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new).getId();
        return getStatusMap(userService.updateUsername(userId, username));
    }

    @PutMapping("/manage_account/email")
    public @ResponseBody
    Map<String, Object> updateEmail(@RequestParam("email") String email) {
        if (!userUtil.validateEmail(email)) {
            return getStatusMap(false);
        }
        int userId = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new).getId();
        return getStatusMap(userService.updateEmail(userId, email));
    }
}
