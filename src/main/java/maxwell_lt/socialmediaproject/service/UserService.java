package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.exception.UserNotFoundException;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean updateUsername(int id, String username) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        } else {
            user.setUsername(username);
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    public boolean updateEmail(int id, String email) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (userRepository.findByEmail(email).isPresent()) {
            return false;
        } else {
            user.setEmail(email);
            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    public User setAdminRole(int id, boolean hasRole) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setHasAdminPermissions(hasRole);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User setModeratorRole(int id, boolean hasRole) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setHasModeratorPermissions(hasRole);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(int userId) {
        userRepository.deleteUserById(userId);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
