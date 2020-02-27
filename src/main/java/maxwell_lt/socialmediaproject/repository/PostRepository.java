package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUserAndDeletedFalse(User user);

    Page<Post> findByUserAndDeletedFalse(User user, Pageable pageable);

    List<Post> findAllByUserIdAndDeletedFalse(int userId);

    @Query(
            value = "SELECT * " +
                    "FROM post p1 " +
                    "WHERE p1.deleted = false " +
                    "ORDER BY ( " +
                    "SELECT SUM(pl.likesUsed) / (-TIME_TO_SEC(TIMEDIFF(p1.timestamp, CURRENT_TIMESTAMP()))/86400) " +
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
                    "AND p1.deleted = false " +
                    "ORDER BY ( " +
                    "SELECT SUM(pl.likesUsed) / (-TIME_TO_SEC(TIMEDIFF(p1.timestamp, CURRENT_TIMESTAMP()))/86400) " +
                    "FROM post p2 " +
                    "JOIN postlikes pl ON p2.id = pl.post " +
                    "WHERE p1.id = p2.id " +
                    "GROUP BY p2.id) DESC " +
                    "LIMIT ?#{#pageable.offset},?#{#pageable.pageSize}",
            countQuery = "SELECT COUNT(id) FROM post WHERE user = ?1",
            nativeQuery = true
    )
    Page<Post> findAllByUserOrderByPopularity(User user, Pageable pageable);

    @Modifying
    @Query("update Post p set p.deleted = true where p.id = :postId")
    void deletePostById(int postId);
}
