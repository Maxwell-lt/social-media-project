package maxwell_lt.socialmediaproject.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Post {
    private int id;
    private String title;
    private String imageId;
    private String text;
    private Timestamp timestamp;
    private boolean deleted;
    private Collection<Comment> comments;
    private User user;
    private Collection<Postlikes> postlikes;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        Post post = (Post) o;
        return id == post.id &&
                deleted == post.deleted &&
                Objects.equals(title, post.title) &&
                Objects.equals(imageId, post.imageId) &&
                Objects.equals(text, post.text) &&
                Objects.equals(timestamp, post.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, imageId, text, timestamp, deleted);
    }

    @OneToMany(mappedBy = "post")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "post")
    public Collection<Postlikes> getPostlikes() {
        return postlikes;
    }

    public void setPostlikes(Collection<Postlikes> postlikes) {
        this.postlikes = postlikes;
    }
}
