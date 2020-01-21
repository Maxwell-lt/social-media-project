package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PostlikesPK implements Serializable {
    private int post;
    private int user;

    @Column(name = "post", nullable = false)
    @Id
    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    @Column(name = "user", nullable = false)
    @Id
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostlikesPK that = (PostlikesPK) o;
        return post == that.post &&
                user == that.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }
}
