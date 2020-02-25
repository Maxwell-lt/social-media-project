package maxwell_lt.socialmediaproject.repository;

import maxwell_lt.socialmediaproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByUsernameAndDeletedFalse(String username);

    List<User> findByHasAdminPermissionsIsTrue();

    List<User> findByHasModeratorPermissionsIsTrue();

    List<User> findByCreationDateAfter(Timestamp timestamp);

    List<User> findByCreationDateBefore(Timestamp timestamp);

    @Modifying
    @Query("update User u set u.isDeleted = true where u.id = :userId")
    void deleteUserById(int userId);
}
