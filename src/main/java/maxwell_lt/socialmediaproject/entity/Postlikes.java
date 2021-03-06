package maxwell_lt.socialmediaproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(PostlikesPK.class)
@Table(name = "postlikes")
public class Postlikes implements Serializable {
    private static final long serialVersionUID = -4574093077889577396L;
    private int postId;
    private int userId;
    private int likesUsed;
    @JsonManagedReference
    private Post post;
    @JsonManagedReference
    private User user;

    public Postlikes() {
    }

    public Postlikes(int userId, int postId, int likes) {
        this.userId = userId;
        this.postId = postId;
        likesUsed = likes;
    }

    @Column(name = "post")
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Column(name = "user")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "likesUsed", nullable = false)
    public int getLikesUsed() {
        return likesUsed;
    }

    public void setLikesUsed(int likesUsed) {
        this.likesUsed = likesUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postlikes postlikes = (Postlikes) o;
        return likesUsed == postlikes.likesUsed &&
                post.equals(postlikes.post) &&
                user.equals(postlikes.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likesUsed, post, user);
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Post getPost() {
        return post;
    }

    public void setPost(Post postByPost) {
        this.post = postByPost;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUser) {
        this.user = userByUser;
    }
}
