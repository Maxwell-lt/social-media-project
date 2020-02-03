package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public String getPost(@RequestParam(value = "post") int postId, Model model) {
        Optional<Post> postOptional = postService.getPostById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            model.addAttribute("title", post.getTitle());
            model.addAttribute("text", post.getText());
        }
        return "posts";
    }
}
