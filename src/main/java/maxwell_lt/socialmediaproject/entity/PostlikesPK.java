package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PostlikesPK implements Serializable {
    private int post;
    private int user;

    public PostlikesPK() {
    }

    public PostlikesPK(int userid, int postid) {
        user = userid;
        post = postid;
    }

    @Column(name = "post", nullable = false)
    @Id
    public int getPostId() {
        return post;
    }

    public void setPostId(int post) {
        this.post = post;
    }

    @Column(name = "user", nullable = false)
    @Id
    public int getUserId() {
        return user;
    }

    public void setUserId(int user) {
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
