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
    private int post;
    private int user;
    private Post postByPost;
    private User userByUser;

    public Postlikes() {
    }

    public Postlikes(int userid, int postid, int likes) {
        user = userid;
        post = postid;
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
    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    @Id
    @Column(name = "user", nullable = false)
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
        Postlikes postlikes = (Postlikes) o;
        return likesUsed == postlikes.likesUsed &&
                post == postlikes.post &&
                user == postlikes.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(likesUsed, post, user);
    }

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id", nullable = false)
    public Post getPostByPost() {
        return postByPost;
    }

    public void setPostByPost(Post postByPost) {
        this.postByPost = postByPost;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUserByUser() {
        return userByUser;
    }

    public void setUserByUser(User userByUser) {
        this.userByUser = userByUser;
    }
}
