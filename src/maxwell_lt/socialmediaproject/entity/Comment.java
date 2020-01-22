package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Comment {
    private int id;
    private String imageId;
    private String text;
    private int user;
    private int post;
    private Timestamp timestamp;
    private boolean deleted;
    private User userByUser;
    private Post postByPost;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "imageId", nullable = true, length = 36)
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "text", nullable = false, length = 5000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "user", nullable = false)
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Basic
    @Column(name = "post", nullable = false)
    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    @Basic
    @Column(name = "timestamp", nullable = false)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                user == comment.user &&
                post == comment.post &&
                deleted == comment.deleted &&
                Objects.equals(imageId, comment.imageId) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(timestamp, comment.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageId, text, user, post, timestamp, deleted);
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUserByUser() {
        return userByUser;
    }

    public void setUserByUser(User userByUser) {
        this.userByUser = userByUser;
    }

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "id", nullable = false)
    public Post getPostByPost() {
        return postByPost;
    }

    public void setPostByPost(Post postByPost) {
        this.postByPost = postByPost;
    }
}