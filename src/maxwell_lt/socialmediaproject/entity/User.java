package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "findUserByUsername",
                query = "select object(u) from User u where u.username = :username"
        ),
        @NamedQuery(
                name = "findUserByEmail",
                query = "select object(u) from User u where u.email = :email"
        )})
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Timestamp creationDate;
    private BigDecimal currentLikes;
    private boolean hasPublicLikes;
    private boolean hasAdminPermissions;
    private boolean hasModeratorPermissions;
    private boolean isDeleted;
    private Collection<Comment> commentsByUser;
    private Collection<Post> postsByUser;
    private Collection<Postlikes> postlikesByUser;
    private Collection<Purchase> purchasesByUser;

    @Id
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
    public boolean getHasPublicLikes() {
        return hasPublicLikes;
    }

    public void setHasPublicLikes(boolean hasPublicLikes) {
        this.hasPublicLikes = hasPublicLikes;
    }

    @Basic
    @Column(name = "hasAdminPermissions", nullable = false)
    public boolean getHasAdminPermissions() {
        return hasAdminPermissions;
    }

    public void setHasAdminPermissions(boolean hasAdminPermissions) {
        this.hasAdminPermissions = hasAdminPermissions;
    }

    @Basic
    @Column(name = "hasModeratorPermissions", nullable = false)
    public boolean getHasModeratorPermissions() {
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
    public Collection<Comment> getCommentsByUser() {
        return commentsByUser;
    }

    public void setCommentsByUser(Collection<Comment> commentsById) {
        this.commentsByUser = commentsById;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Post> getPostsByUser() {
        return postsByUser;
    }

    public void setPostsByUser(Collection<Post> postsById) {
        this.postsByUser = postsById;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Postlikes> getPostlikesByUser() {
        return postlikesByUser;
    }

    public void setPostlikesByUser(Collection<Postlikes> postlikesById) {
        this.postlikesByUser = postlikesById;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Purchase> getPurchasesByUser() {
        return purchasesByUser;
    }

    public void setPurchasesByUser(Collection<Purchase> purchasesById) {
        this.purchasesByUser = purchasesById;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("email='" + email + "'")
                .add("creationDate=" + creationDate)
                .add("currentLikes=" + currentLikes)
                .add("hasPublicLikes=" + hasPublicLikes)
                .add("hasAdminPermissions=" + hasAdminPermissions)
                .add("hasModeratorPermissions=" + hasModeratorPermissions)
                .add("isDeleted=" + isDeleted)
                .toString();
    }
}
