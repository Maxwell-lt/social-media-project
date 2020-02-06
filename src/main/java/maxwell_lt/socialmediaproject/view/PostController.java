package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.CommentForm;
import maxwell_lt.socialmediaproject.dto.PostDto;
import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.CommentService;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import maxwell_lt.socialmediaproject.utilities.CommentUtil;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;
    private PostlikesService postlikesService;
    private CommentService commentService;
    private PrettyTime prettyTime;
    private UserUtil userUtil;
    private CommentUtil commentUtil;

    @Autowired
    public PostController(PostService postService, PostlikesService postlikesService, CommentService commentService, PrettyTime prettyTime, UserUtil userUtil, CommentUtil commentUtil) {
        this.postService = postService;
        this.postlikesService = postlikesService;
        this.commentService = commentService;
        this.prettyTime = prettyTime;
        this.userUtil = userUtil;
        this.commentUtil = commentUtil;
    }

    @GetMapping("/post")
    public String getPost(@RequestParam(value = "post") int postId, Model model, @ModelAttribute("commentform") CommentForm commentForm) {
        Optional<Post> postOptional = postService.getPostById(postId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> currentUser = Optional.empty();
        if (principal instanceof UserPrincipal) {
            currentUser = Optional.of(((UserPrincipal) principal).getUser());
        }
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            PostDto postDto = new PostDto(post, post.getUser(),
                    postlikesService.getLikes(post),
                    currentUser.map(u -> postlikesService.getLikesByUser(post, u)).orElse(0));
            model.addAttribute("postdto", postDto);
        }
        currentUser.ifPresent(u -> model.addAttribute("currentuser", u));
        model.addAttribute(prettyTime);
        return "posts";
    }

    @PostMapping("/newcomment")
    public ModelAndView newComment(@RequestParam("commentform") @Valid CommentForm commentForm, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/post?post=" + commentForm.getPostId());

        if (bindingResult.hasErrors()) {
            return mav;
        }
        Optional<User> userOptional = userUtil.getCurrentUser();
        userOptional.ifPresent(user -> commentService.createComment(commentUtil.createCommentFromCommentFormAndUser(commentForm, user)));
        return mav;
    }
}
