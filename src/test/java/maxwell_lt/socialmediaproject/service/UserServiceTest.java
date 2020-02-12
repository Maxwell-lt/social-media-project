package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(0);
        user.setEmail("email@email.com");
        user.setUsername("username");

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void whenCreateUser_ThenUserSaved() {
        userService.createUser(user);

        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void givenUserExists_WhenFindUserById_ThenGetUser() {
        assertThat(userService.getUserById(user.getId()))
                .isNotEmpty()
                .containsSame(user);

        verify(userRepository, times(1))
                .findById(user.getId());
    }

    @Test
    void givenUserDoesNotExist_WhenFindUserById_ThenGetEmptyOptional() {
        assertThat(userService.getUserById(1))
                .isEmpty();

        verify(userRepository, times(1))
                .findById(anyInt());
    }

    @Test
    void givenUserExists_WhenFindUserByUsername_ThenGetUser() {
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        assertThat(userService.getUserByUsername(user.getUsername()))
                .isNotEmpty()
                .containsSame(user);

        verify(userRepository, times(1))
                .findByUsername(user.getUsername());
    }

    @Test
    void givenUserDoesNotExist_WhenFindUserByUsername_ThenGetEmptyOptional() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThat(userService.getUserByUsername("username"))
                .isEmpty();

        verify(userRepository, times(1))
                .findByUsername(anyString());
    }

    @Test
    void givenUserExists_WhenFindUserByEmail_ThenGetUser() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        assertThat(userService.getUserByEmail(user.getEmail()))
                .isNotEmpty()
                .containsSame(user);

        verify(userRepository, times(1))
                .findByEmail(user.getEmail());
    }

    @Test
    void givenUserDoesNotExist_WhenFindUserByEmail_ThenGetEmptyOptional() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThat(userService.getUserByEmail("email"))
                .isEmpty();

        verify(userRepository, times(1))
                .findByEmail(anyString());
    }

    @Test
    void whenUpdateUsername_ThenUsernameIsUpdated() {
        userService.updateUsername(user.getId(), "newUsername");

        assertThat(user.getUsername())
                .isEqualTo("newUsername");

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void givenUserDoesNotExist_WhenUpdateUsername_ThenThrowException() {
        assertThatThrownBy(() -> userService.updateUsername(1, "newUsername"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void whenUpdateEmail_ThenEmailIsUpdated() {
        userService.updateEmail(user.getId(), "newemail@email.com");

        assertThat(user.getEmail())
                .isEqualTo("newemail@email.com");

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void givenUserDoesNotExist_WhenUpdateEmail_ThenThrowException() {
        assertThatThrownBy(() -> userService.updateEmail(1, "newemail@email.com"))
                .isInstanceOf(RuntimeException.class);
    }
}