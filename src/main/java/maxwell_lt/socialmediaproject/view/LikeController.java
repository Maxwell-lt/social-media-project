package maxwell_lt.socialmediaproject.view;

import maxwell_lt.socialmediaproject.dto.LikeResult;
import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.service.PostlikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class LikeController {

    private PostlikesService postlikesService;

    @Autowired
    public LikeController(PostlikesService postlikesService) {
        this.postlikesService = postlikesService;
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LikeResult likePost(
            @RequestParam("postid") int postId,
            @RequestParam("number") int likeCount) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> currentUser = Optional.empty();
        if (principal instanceof UserPrincipal) {
            currentUser = Optional.of(((UserPrincipal) principal).getUser());
        }
        boolean success = currentUser.map(user -> postlikesService.likePost(user.getId(), postId, likeCount))
                .orElse(false);

        // These values are only needed client-side if the operation failed.
        int userLikes = -1;
        int totalLikes = -1;
        if (!success) {
            userLikes = currentUser
                    .map(user -> postlikesService.getLikesByUser(postId, user))
                    .orElse(0);

            totalLikes = postlikesService.getLikes(postId);
        }

        return new LikeResult(success, userLikes, totalLikes);
    }
}
