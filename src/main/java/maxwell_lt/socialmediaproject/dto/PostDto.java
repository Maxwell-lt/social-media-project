package maxwell_lt.socialmediaproject.dto;

import maxwell_lt.socialmediaproject.entity.Post;
import maxwell_lt.socialmediaproject.entity.User;

import java.util.Objects;
import java.util.StringJoiner;

public class PostDto {
    private Post post;
    private User user;
    private int likes;
    private int userLikes;

    public PostDto(Post post, User user, int likes, int userLikes) {
        this.post = post;
        this.user = user;
        this.likes = likes;
        this.userLikes = userLikes;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto postDto = (PostDto) o;
        return likes == postDto.likes &&
                userLikes == postDto.userLikes &&
                post.equals(postDto.post) &&
                user.equals(postDto.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user, likes, userLikes);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PostDto.class.getSimpleName() + "[", "]")
                .add("post=" + post)
                .add("user=" + user)
                .add("likes=" + likes)
                .add("userLikes=" + userLikes)
                .toString();
    }
}
