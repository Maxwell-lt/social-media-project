package maxwell_lt.socialmediaproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -4075652021712217438L;
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Timestamp creationDate;
    private BigDecimal currentLikes;
    private boolean hasPublicLikes;
    private boolean hasAdminPermissions;
    private boolean hasModeratorPermissions;
    private boolean isDeleted;
    @JsonBackReference
    private Collection<Comment> comments;
    @JsonBackReference
    private Collection<Post> posts;
    @JsonBackReference
    private Collection<Postlikes> postlikes;
    @JsonBackReference
    private Collection<Purchase> purchases;

    public User() {
        this.setHasPublicLikes(false);
        this.setHasAdminPermissions(false);
        this.setHasModeratorPermissions(false);
        this.setIsDeleted(false);
    }

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
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 254)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "creationDate", nullable = false)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "currentLikes", nullable = false, precision = 2)
    public BigDecimal getCurrentLikes() {
        return currentLikes;
    }

    public void setCurrentLikes(BigDecimal currentLikes) {
        this.currentLikes = currentLikes;
    }

    @Basic
    @Column(name = "hasPublicLikes", nullable = false)
    public boolean isHasPublicLikes() {
        return hasPublicLikes;
    }

    public void setHasPublicLikes(boolean hasPublicLikes) {
        this.hasPublicLikes = hasPublicLikes;
    }

    @Basic
    @Column(name = "hasAdminPermissions", nullable = false)
    public boolean isHasAdminPermissions() {
        return hasAdminPermissions;
    }

    public void setHasAdminPermissions(boolean hasAdminPermissions) {
        this.hasAdminPermissions = hasAdminPermissions;
    }

    @Basic
    @Column(name = "hasModeratorPermissions", nullable = false)
    public boolean isHasModeratorPermissions() {
        return hasModeratorPermissions;
    }

    public void setHasModeratorPermissions(boolean hasModeratorPermissions) {
        this.hasModeratorPermissions = hasModeratorPermissions;
    }

    @Basic
    @Column(name = "isDeleted", nullable = false)
    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                hasPublicLikes == user.hasPublicLikes &&
                hasAdminPermissions == user.hasAdminPermissions &&
                hasModeratorPermissions == user.hasModeratorPermissions &&
                isDeleted == user.isDeleted &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(creationDate, user.creationDate) &&
                Objects.equals(currentLikes, user.currentLikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, creationDate, currentLikes, hasPublicLikes, hasAdminPermissions, hasModeratorPermissions, isDeleted);
    }

    @OneToMany(mappedBy = "user")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Postlikes> getPostlikes() {
        return postlikes;
    }

    public void setPostlikes(Collection<Postlikes> postlikes) {
        this.postlikes = postlikes;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Collection<Purchase> purchases) {
        this.purchases = purchases;
    }
}
