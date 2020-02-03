package maxwell_lt.socialmediaproject.view;


import maxwell_lt.socialmediaproject.dto.PostForm;
import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.PostService;
import maxwell_lt.socialmediaproject.utilities.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class NewPostController {

    private PostService postService;
    private PostUtil postUtil;

    @Autowired
    public NewPostController(PostService postService, PostUtil postUtil) {
        this.postService = postService;
        this.postUtil = postUtil;
    }

    @GetMapping("/newpost")
    public String newPostGet(@ModelAttribute("postform") PostForm postForm) {
        return "post";
    }

    @PostMapping("/newpost")
    public ModelAndView newPost(@ModelAttribute("postform") @Valid PostForm postForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("post");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            User user = ((UserPrincipal) principal).getUser();
            int postId = postService.createPost(postUtil.createPostFromPostFormAndUser(postForm, user));
            return new ModelAndView("redirect:/post?post=" + postId);
        }

        return new ModelAndView("post");
    }
}
