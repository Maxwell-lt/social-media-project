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
import maxwell_lt.socialmediaproject.validator.EmailNotExist;
import maxwell_lt.socialmediaproject.validator.PasswordStrength;
import maxwell_lt.socialmediaproject.validator.UsernameNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        return getModelFromUserId(userId, currentUser, pageNumber, pageSize, sortOrder);
    }

    @GetMapping("/account")
    public ModelAndView myAccountInfo() {
        Optional<User> currentUser = userUtil.getCurrentUser();
        return currentUser
                .map(user -> new ModelAndView("redirect:/account/" + user.getId()))
                .orElseGet(() -> new ModelAndView("redirect:/login"));
    }

    private static Map<String, Object> getStatusMap(boolean status) {
        return getStatusMap(status, "");
    }

    private static Map<String, Object> getStatusMap(boolean status, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", Boolean.toString(status));
        result.put("message", message);
        return result;
    }

    private ModelAndView getModelFromUserId(int userId, Optional<User> currentUser, int pageNumber, int pageSize, Sort order) {

        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        ModelAndView mav = new ModelAndView("account");
        User user = userOptional.get();

        Page<Post> posts = order.isUnsorted()
                ? postService.getPostsAsPageByUserByPopularity(user, PageRequest.of(pageNumber - 1, pageSize))
                : postService.getPostsByUser(user, PageRequest.of(pageNumber - 1, pageSize, order));
        Collection<Comment> comments = commentService.getCommentsByUser(user);

        Map<Integer, Integer> totalLikes = posts.stream()
                .collect(Collectors.toMap(Post::getId, postlikesService::getLikes));

        Map<Integer, Integer> myLikes = currentUser.map(value -> posts.stream()
                .collect(Collectors.toMap(Post::getId,
                        post -> postlikesService.getLikesByUser(post, value))))
                .orElseGet(HashMap::new);


        if (currentUser.isPresent()) {
            mav.addObject("mypage", currentUser.get().getId() == userId);
        } else {
            mav.addObject("mypage", false);
        }

        mav.addObject("pageuser", user);
        mav.addObject("posts", posts);
        mav.addObject("totallikes", totalLikes);
        mav.addObject("mylikes", myLikes);
        mav.addObject("comments", comments);

        int totalPages = posts.getTotalPages();
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
                                       @RequestParam("newpass")
                                       @NotNull
                                       @NotEmpty(message = "Password cannot be empty!")
                                       @Size(max = 48)
                                       @PasswordStrength
                                       @Valid String newPassword,
                                       BindingResult result) {
        User user = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new);
        if (result.hasErrors()) {
            return getStatusMap(false, result.getModel());
        }
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            userService.updatePassword(user, passwordEncoder.encode(newPassword));
            return getStatusMap(true);
        }
        return getStatusMap(false);
    }

    @PutMapping("/manage_account/username")
    public @ResponseBody
    Map<String, Object> updateUsername(@RequestParam("username")
                                       @NotNull
                                       @NotEmpty(message = "Username cannot be empty!")
                                       @Size(max = 50, message = "Size must be between 1 and 50 characters!")
                                       @UsernameNotExist
                                       @Valid String username,
                                       BindingResult result) {
        if (result.hasErrors()) {
            return getStatusMap(false);
        }
        int userId = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new).getId();
        return getStatusMap(userService.updateUsername(userId, username));
    }

    @PutMapping("/manage_account/email")
    public @ResponseBody
    Map<String, Object> updateEmail(@RequestParam("email") @NotNull
                                    @NotEmpty(message = "Email cannot be empty!")
                                    @Size(max = 254)
                                    @Email
                                    @EmailNotExist
                                    @Valid String email,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return getStatusMap(false);
        }
        int userId = userUtil.getCurrentUser().orElseThrow(NotAuthenticatedException::new).getId();
        return getStatusMap(userService.updateEmail(userId, email));
    }
}
