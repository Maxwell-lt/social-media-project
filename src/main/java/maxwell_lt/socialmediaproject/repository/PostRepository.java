package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);

    Page<Post> findByUser(User user, Pageable pageable);

    @Query("select p from Post p where p.user = :user order by p.timestamp")
    Page<Post> findAllByUser(User user, Pageable pageable);

    @Query(
            value = "SELECT * " +
                    "FROM post p1 " +
                    "ORDER BY ( " +
                    "SELECT pl.likesUsed / (-TIME_TO_SEC(TIMEDIFF(p1.timestamp, CURRENT_TIMESTAMP()))/86400) " +
                    "FROM post p2 " +
                    "JOIN postlikes pl ON p2.id = pl.post " +
                    "WHERE p1.id = p2.id " +
                    "GROUP BY p2.id) DESC " +
                    "LIMIT ?#{#pageable.offset},?#{#pageable.pageSize}",
            countQuery = "SELECT COUNT(id) FROM post",
            nativeQuery = true
    )
    Page<Post> findAllOrderByPopularity(Pageable pageable);

    @Query(
            value = "SELECT * " +
                    "FROM post p1 " +
                    "WHERE p1.user = ?1 " +
                    "ORDER BY ( " +
                    "SELECT pl.likesUsed / (-TIME_TO_SEC(TIMEDIFF(p1.timestamp, CURRENT_TIMESTAMP()))/86400) " +
                    "FROM post p2 " +
                    "JOIN postlikes pl ON p2.id = pl.post " +
                    "WHERE p1.id = p2.id " +
                    "GROUP BY p2.id) DESC " +
                    "LIMIT ?#{#pageable.offset},?#{#pageable.pageSize}",
            countQuery = "SELECT COUNT(id) FROM post WHERE user = ?1",
            nativeQuery = true
    )
    Page<Post> findAllByUserOrderByPopularity(User user, Pageable pageable);
}
