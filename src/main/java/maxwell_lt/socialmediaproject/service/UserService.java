package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUsername(int id, String username) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        user.setUsername(username);
        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(int id, String email) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        user.setEmail(email);
        userRepository.save(user);
    }
}
