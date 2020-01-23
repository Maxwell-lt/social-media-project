package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.Postlikes;
import maxwell_lt.socialmediaproject.entity.PostlikesPK;
import maxwell_lt.socialmediaproject.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PostlikesService extends AbstractService {
    public boolean likePost(int userid, int postid, int numberOfLikes) {
        em.getTransaction().begin();
        User user = em.find(User.class, userid);
        Post post = em.find(Post.class, postid);
        if (user == null || post == null || user.getCurrentLikes().compareTo(new BigDecimal(numberOfLikes)) < 0) {
            em.getTransaction().rollback();
            return false;
        }

        // Update or add entry to postlikes table
        Postlikes pl = em.find(Postlikes.class, new PostlikesPK(userid, postid));
        if (pl == null) {
            pl = new Postlikes(userid, postid, numberOfLikes);
            em.persist(pl);
        } else {
            pl.setLikesUsed(pl.getLikesUsed() + numberOfLikes);
        }

        // Decrement likes for user spending them
        user.setCurrentLikes(user.getCurrentLikes().subtract(new BigDecimal(numberOfLikes)));

        // Increment fractional likes for original poster
        post.getUser().setCurrentLikes(post.getUser()
                .getCurrentLikes()
                .add(new BigDecimal(numberOfLikes)
                        .divide(new BigDecimal(100), 2, RoundingMode.UNNECESSARY)));


        em.getTransaction().commit();
        return true;
    }
}
