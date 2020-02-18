package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import maxwell_lt.socialmediaproject.utilities.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexController {

    private PostService postService;
    private PostlikesService postlikesService;
    private UserUtil userUtil;

    @Autowired
    public IndexController(PostService postService,
                           PostlikesService postlikesService,
                           UserUtil userUtil) {
        this.postService = postService;
        this.postlikesService = postlikesService;
        this.userUtil = userUtil;
    }

    @GetMapping({"/"})
    public ModelAndView index(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        ModelAndView mav = new ModelAndView("index");
        Page<Post> posts = postService.getPostsAsPageByPopularity(PageRequest.of(pageNumber - 1, pageSize));
        Map<Integer, Integer> totalLikes = posts.stream()
                .collect(Collectors.toMap(Post::getId, postlikesService::getLikes));
        Optional<User> currentUser = userUtil.getCurrentUser();
        Map<Integer, Integer> myLikes = currentUser.map(value -> posts.stream()
                .collect(Collectors.toMap(Post::getId,
                        post -> postlikesService.getLikesByUser(post, value))))
                .orElseGet(HashMap::new);

        mav.addObject("posts", posts);
        mav.addObject("totallikes", totalLikes);
        mav.addObject("mylikes", myLikes);
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
}
