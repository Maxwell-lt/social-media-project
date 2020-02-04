package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.PostDto;
import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;
    private PostlikesService postlikesService;
    private PrettyTime prettyTime;

    @Autowired
    public PostController(PostService postService, PostlikesService postlikesService, PrettyTime prettyTime) {
        this.postService = postService;
        this.postlikesService = postlikesService;
        this.prettyTime = prettyTime;
    }

    @GetMapping("/post")
    public String getPost(@RequestParam(value = "post") int postId, Model model) {
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
}
