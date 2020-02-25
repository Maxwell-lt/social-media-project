package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.Comment;
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
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByUserAndDeletedFalse(User user);

    Page<Comment> findAllByUserAndDeletedFalse(User user, Pageable pageable);

    Page<Comment> findAllByPostAndDeletedFalse(Post post, Pageable pageable);

    List<Comment> findAllByPostAndDeletedFalse(Post post);

    @Modifying
    @Query("update Comment c set c.deleted = true where c.id = :commentId")
    void deleteCommentById(int commentId);
}
