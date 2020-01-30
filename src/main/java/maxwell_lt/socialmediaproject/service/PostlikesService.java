package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.Postlikes;
import maxwell_lt.socialmediaproject.entity.PostlikesPK;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PostRepository;
import maxwell_lt.socialmediaproject.repository.PostlikesRepository;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PostlikesService {

    private PostlikesRepository postlikesRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public PostlikesService(PostlikesRepository plr, UserRepository ur, PostRepository pr) {
        this.postlikesRepository = plr;
        this.userRepository = ur;
        this.postRepository = pr;
    }

    public int getLikes(Post post) {
        return postlikesRepository.findTotalLikesByPost(post);
    }

    @Transactional
    public void likePost(int userId, int postId, int numberOfLikes) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        if (user.getCurrentLikes().intValue() < numberOfLikes) {
            return;
        }

        PostlikesPK key = new PostlikesPK(userId, postId);
        Optional<Postlikes> postlikesOptional = postlikesRepository.findById(key);
        Postlikes postlikes;
        if (postlikesOptional.isPresent()) {
            postlikes = postlikesOptional.get();
            postlikes.setLikesUsed(postlikes.getLikesUsed() + numberOfLikes);
        } else {
            postlikes = new Postlikes(userId, postId, numberOfLikes);
        }
        postlikesRepository.save(postlikes);

        user.setCurrentLikes(user.getCurrentLikes().subtract(new BigDecimal(numberOfLikes)));

        post.getUser().setCurrentLikes(post.getUser()
                .getCurrentLikes()
                .add(new BigDecimal(numberOfLikes)
                        .divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY)));
    }
}
