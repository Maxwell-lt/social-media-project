package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.Postlikes;
import maxwell_lt.socialmediaproject.entity.PostlikesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostlikesRepository extends JpaRepository<Postlikes, PostlikesPK> {

    @Query("select sum(pl.likesUsed) from Postlikes pl join Post p where pl.post = :post")
    int findTotalLikesByPost(@Param("post") Post post);
}
