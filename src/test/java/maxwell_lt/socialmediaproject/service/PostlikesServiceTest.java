package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.Postlikes;
import maxwell_lt.socialmediaproject.entity.PostlikesPK;
import maxwell_lt.socialmediaproject.entity.User;
import maxwell_lt.socialmediaproject.repository.PostRepository;
import maxwell_lt.socialmediaproject.repository.PostlikesRepository;
import maxwell_lt.socialmediaproject.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostlikesServiceTest {

    @Autowired
    private PostlikesService postlikesService;

    @MockBean
    private PostlikesRepository postlikesRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    private User liker;
    private User poster;
    private Post post;
    private Postlikes postlikes;

    @BeforeEach
    void setUp() {
        liker = new User();
        poster = new User();
        post = new Post();
        postlikes = new Postlikes();

        liker.setId(0);
        poster.setId(1);
        post.setId(0);
        post.setUser(poster);
        postlikes.setUser(poster);
        postlikes.setUserId(poster.getId());
        postlikes.setPost(post);
        postlikes.setPostId(post.getId());
        liker.setCurrentLikes(new BigDecimal("15"));
        poster.setCurrentLikes(new BigDecimal("0"));

        when(userRepository.findById(0)).thenReturn(Optional.of(liker));
        when(userRepository.findById(1)).thenReturn(Optional.of(poster));
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        when(postRepository.findById(0)).thenReturn(Optional.of(post));
        when(postRepository.findById(1)).thenReturn(Optional.empty());
    }

    @AfterEach
    void tearDown() {
        liker = null;
        poster = null;
        post = null;
        postlikes = null;
    }

    @Test
    void givenPostNotAlreadyLiked_WhenUserHasEnoughLikes_ThenLikesAreGiven() {
        when(postlikesRepository.findById(any(PostlikesPK.class))).thenReturn(Optional.empty());

        postlikesService.likePost(liker.getId(), post.getId(), 10);

        assertThat(liker.getCurrentLikes())
                .isEqualByComparingTo("5");
        assertThat(poster.getCurrentLikes())
                .isEqualByComparingTo("0.1");
        verify(postlikesRepository, times(1))
                .save(argThat(argument -> argument.getLikesUsed() == 10));
    }

    @Test
    void givenPostAlreadyLiked_WhenUserHasEnoughLikes_ThenLikesAreGiven() {
        postlikes.setLikesUsed(35);

        when(postlikesRepository.findById(any(PostlikesPK.class))).thenReturn(Optional.of(postlikes));

        postlikesService.likePost(liker.getId(), post.getId(), 10);

        assertThat(liker.getCurrentLikes())
                .isEqualByComparingTo("5");
        assertThat(poster.getCurrentLikes())
                .isEqualByComparingTo("0.1");
        verify(postlikesRepository, times(1))
                .save(argThat(argument -> argument.getLikesUsed() == 45));
    }

    @Test
    void whenUserDoesNotExist_ThenThrowsError() {
        assertThatThrownBy(() -> postlikesService.likePost(2, post.getId(), 10))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void whenPostDoesNotExist_ThenThrowsError() {
        assertThatThrownBy(() -> postlikesService.likePost(liker.getId(), 1, 10))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void whenUserDoesNotHaveEnoughLikes_ThenDoesNotGrantLikes() {
        assertThat(postlikesService.likePost(liker.getId(), post.getId(), 100))
                .isFalse();
        verify(postlikesRepository, never()).save(any());
    }

    @Test
    void whenUserHasAlmostEnoughLikes_ThenDoesNotGrantLikes() {
        liker.setCurrentLikes(new BigDecimal("14.99"));
        assertThat(postlikesService.likePost(liker.getId(), post.getId(), 15))
                .isFalse();
        verify(postlikesRepository, never()).save(any());
    }

    @Test
    void whenUserHasJustEnoughLikes_ThenLikesAreGiven() {
        assertThat(postlikesService.likePost(liker.getId(), post.getId(), 15))
                .isTrue();
        assertThat(liker.getCurrentLikes())
                .isEqualByComparingTo("0");
        verify(postlikesRepository, times(1))
                .save(argThat(argument -> argument.getLikesUsed() == 15));
    }

    @Test
    void givenPostDoesNotHaveLikesByUser_WhenLikesAreRetrieved_ThenGetZero() {
        postlikes.setLikesUsed(35);
        when(postlikesRepository.findByPostIdAndUser(post.getId(), poster))
                .thenReturn(Optional.of(postlikes));
        when(postlikesRepository.findByPostAndUser(post, liker))
                .thenReturn(Optional.empty());

        assertThat(postlikesService.getLikesByUser(post, liker))
                .isEqualTo(0);
    }

    @Test
    void givenPostHasLikesByUser_WhenUserLikesAreRetrievedByPost_ThenGetCorrectValue() {
        // Poster's likes
        postlikes.setLikesUsed(35);
        when(postlikesRepository.findByPostAndUser(post, poster))
                .thenReturn(Optional.of(postlikes));

        Postlikes usersPostlikes = new Postlikes();
        usersPostlikes.setPost(post);
        usersPostlikes.setUser(liker);
        usersPostlikes.setLikesUsed(25);
        when(postlikesRepository.findByPostAndUser(post, liker))
                .thenReturn(Optional.of(usersPostlikes));

        assertThat(postlikesService.getLikesByUser(post, liker))
                .isEqualTo(25);
        verify(postlikesRepository, times(1))
                .findByPostAndUser(post, liker);
    }

    @Test
    void givenPostHasLikesByUser_WhenUserLikesAreRetrievedByPostId_ThenGetCorrectValue() {
        // Poster's likes
        postlikes.setLikesUsed(35);
        when(postlikesRepository.findByPostIdAndUser(post.getId(), poster))
                .thenReturn(Optional.of(postlikes));

        Postlikes usersPostlikes = new Postlikes();
        usersPostlikes.setPost(post);
        usersPostlikes.setUser(liker);
        usersPostlikes.setLikesUsed(25);
        when(postlikesRepository.findByPostIdAndUser(post.getId(), liker))
                .thenReturn(Optional.of(usersPostlikes));

        assertThat(postlikesService.getLikesByUser(post.getId(), liker))
                .isEqualTo(25);
        verify(postlikesRepository, times(1))
                .findByPostIdAndUser(post.getId(), liker);
    }

    @Test
    void givenPostDoesNotHaveLikes_WhenTotalLikesAreRetrievedByPost_ThenGetZero() {
        when(postlikesRepository.findTotalLikesByPost(post))
                .thenReturn(Optional.empty());

        assertThat(postlikesService.getLikes(post))
                .isEqualTo(0);

        verify(postlikesRepository, times(1))
                .findTotalLikesByPost(post);
    }

    @Test
    void givenPostHasLikes_WhenTotalLikesAreRetrievedByPost_ThenGetCorrectValue() {
        when(postlikesRepository.findTotalLikesByPost(post))
                .thenReturn(Optional.of(52));

        assertThat(postlikesService.getLikes(post))
                .isEqualTo(52);

        verify(postlikesRepository, times(1))
                .findTotalLikesByPost(post);
    }

    @Test
    void givenPostHasLikes_WhenTotalLikesAreRetrievedByPostId_ThenGetCorrectValue() {
        when(postlikesRepository.findTotalLikesByPostId(post.getId()))
                .thenReturn(Optional.of(23));

        assertThat(postlikesService.getLikes(post.getId()))
                .isEqualTo(23);

        verify(postlikesRepository, times(1))
                .findTotalLikesByPostId(post.getId());
    }
}