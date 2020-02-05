package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.service.PostService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private PostService postService;
    private PrettyTime prettyTime;

    @Autowired
    public IndexController(PostService postService, PrettyTime prettyTime) {
        this.postService = postService;
        this.prettyTime = prettyTime;
    }

    @GetMapping({"/"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("posts", postService.getPostsAsPage(PageRequest.of(0, 20, Sort.by("timestamp").descending())).getContent());
        mav.addObject(prettyTime);
        return mav;
    }
}
