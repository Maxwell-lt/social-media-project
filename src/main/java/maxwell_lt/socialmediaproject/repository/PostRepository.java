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
}
