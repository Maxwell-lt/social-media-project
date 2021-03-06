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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;
    private PostlikesService postlikesService;
    private CommentService commentService;
    private UserUtil userUtil;
    private CommentUtil commentUtil;

    @Autowired
    public PostController(PostService postService, PostlikesService postlikesService, CommentService commentService, UserUtil userUtil, CommentUtil commentUtil) {
        this.postService = postService;
        this.postlikesService = postlikesService;
        this.commentService = commentService;
        this.userUtil = userUtil;
        this.commentUtil = commentUtil;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable(value = "id") int postId, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> currentUser = Optional.empty();
        if (principal instanceof UserPrincipal) {
            currentUser = Optional.of(((UserPrincipal) principal).getUser());
        }
            Post post = postService.getPostById(postId);
        PostDto postDto = new PostDto(post, post.getUser(),
                postlikesService.getLikes(post),
                currentUser.map(u -> postlikesService.getLikesByUser(post, u)).orElse(0));
        model.addAttribute("postdto", postDto);
        model.addAttribute("comments", commentService.getCommentsByPost(post, PageRequest.of(0, 20)));
        currentUser.ifPresent(u -> model.addAttribute("currentuser", u));
        return "posts";
    }

    @PostMapping("/newcomment")
    public ModelAndView newComment(@RequestParam("postId") int postId,
                                   @RequestParam("text") String text,
                                   @RequestParam(value = "image", required = false) MultipartFile image) {
        CommentForm commentForm = new CommentForm(postId, text, image);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/post/" + commentForm.getPostId());

        Optional<User> userOptional = userUtil.getCurrentUser();
        userOptional.ifPresent(user -> commentService.createComment(commentUtil.createCommentFromCommentFormAndUser(commentForm, user)));
        return mav;
    }
}
