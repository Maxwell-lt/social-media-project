package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.Postlikes;
import maxwell_lt.socialmediaproject.entity.PostlikesPK;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PostlikesRepository extends JpaRepository<Postlikes, PostlikesPK> {

    @Query("select sum(pl.likesUsed) from Postlikes pl where pl.post = :post")
    Optional<Integer> findTotalLikesByPost(@Param("post") Post post);

    Optional<Postlikes> findByPostAndUser(Post post, User user);
}
