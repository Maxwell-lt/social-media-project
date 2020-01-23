package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@IdClass(PostlikesPK.class)
public class Postlikes {
    private int likesUsed;
    private int postId;
    private int userId;
    private Post post;
    private User user;

    public Postlikes() {
    }

    public Postlikes(int userid, int postid, int likes) {
        userId = userid;
        postId = postid;
        likesUsed = likes;
    }

    @Basic
    @Column(name = "likesUsed", nullable = false)
    public int getLikesUsed() {
        return likesUsed;
    }

    public void setLikesUsed(int likesUsed) {
        this.likesUsed = likesUsed;
    }

    @Id
    @Column(name = "post", nullable = false)
    public int getPostId() {
        return postId;
    }

    public void setPostId(int post) {
        this.postId = post;
    }

    @Id
    @Column(name = "user", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int user) {
        this.userId = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postlikes postlikes = (Postlikes) o;
        return likesUsed == postlikes.likesUsed &&
                postId == postlikes.postId &&
                userId == postlikes.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(likesUsed, postId, userId);
    }

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id", nullable = false)
    public Post getPost() {
        return post;
    }

    public void setPost(Post postByPost) {
        this.post = postByPost;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUser) {
        this.user = userByUser;
    }
}
