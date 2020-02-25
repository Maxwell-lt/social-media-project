package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.dto.UserPrincipal;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setEmail("email@email.com");
        user.setPassword("$hashed$password$");
        when(userRepository.findByUsernameAndIsDeletedFalse(user.getUsername()))
                .thenReturn(Optional.of(user));
        when(userRepository.findByUsernameAndIsDeletedFalse("notAUser"))
                .thenReturn(Optional.empty());
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void givenUserExists_WhenLoadUser_ThenReturnDetails() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.getUsername())
                .isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword())
                .isEqualTo(user.getPassword());

        assertThat(userDetails)
                .isInstanceOf(UserPrincipal.class);
        assertThat(((UserPrincipal) userDetails).getUser())
                .isEqualTo(user);

        verify(userRepository, times(1))
                .findByUsernameAndIsDeletedFalse(anyString());
    }

    @Test
    void givenUserDoesNotExist_WhenLoadUser_ThenThrowException() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("notAUser"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void givenUserHasDefaultAuthorities_WhenGetAuthorities_ThenAuthoritiesOnlyContainsUser() {
        assertThat(userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities())
                .hasSize(1)
                .anyMatch(a -> a.getAuthority().equals("user"));
    }

    @Test
    void givenUserIsAdmin_WhenGetAuthorities_ThenAuthoritiesContainsAdmin() {
        user.setHasAdminPermissions(true);
        assertThat(userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities())
                .hasSize(2)
                .anyMatch(a -> a.getAuthority().equals("admin"))
                .anyMatch(a -> a.getAuthority().equals("user"));
    }

    @Test
    void givenUserIsModerator_WhenGetAuthorities_ThenAuthoritiesContainsModerator() {
        user.setHasModeratorPermissions(true);
        assertThat(userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities())
                .hasSize(2)
                .anyMatch(a -> a.getAuthority().equals("moderator"))
                .anyMatch(a -> a.getAuthority().equals("user"));
    }

    @Test
    void givenUserIsAdminAndModerator_WhenGetAuthorities_ThenAuthoritiesContainsAdminAndModerator() {
        user.setHasAdminPermissions(true);
        user.setHasModeratorPermissions(true);
        assertThat(userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities())
                .hasSize(3)
                .anyMatch(a -> a.getAuthority().equals("admin"))
                .anyMatch(a -> a.getAuthority().equals("moderator"))
                .anyMatch(a -> a.getAuthority().equals("user"));
    }

    @Test
    void givenAccountIsNotDisabled_WhenCheckStatus_ThenIsEnabled() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.isAccountNonExpired())
                .isTrue();
        assertThat(userDetails.isAccountNonLocked())
                .isTrue();
        assertThat(userDetails.isCredentialsNonExpired())
                .isTrue();
        assertThat(userDetails.isEnabled())
                .isTrue();
    }

    @Test
    void givenAccountIsDisabled_WhenCheckStatus_ThenIsDisabled() {
        user.setIsDeleted(true);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        assertThat(userDetails.isAccountNonExpired())
                .isFalse();
        assertThat(userDetails.isAccountNonLocked())
                .isFalse();
        assertThat(userDetails.isCredentialsNonExpired())
                .isFalse();
        assertThat(userDetails.isEnabled())
                .isFalse();
    }
}