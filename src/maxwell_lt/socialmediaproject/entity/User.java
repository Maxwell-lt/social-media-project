package maxwell_lt.socialmediaproject.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

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
    private Collection<Comment> commentsById;
    private Post postById;
    private Collection<Postlikes> postlikesById;
    private Collection<Purchase> purchasesById;

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

    @OneToMany(mappedBy = "userByUser")
    public Collection<Comment> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Collection<Comment> commentsById) {
        this.commentsById = commentsById;
    }

    @OneToOne(mappedBy = "userById")
    public Post getPostById() {
        return postById;
    }

    public void setPostById(Post postById) {
        this.postById = postById;
    }

    @OneToMany(mappedBy = "userByUser")
    public Collection<Postlikes> getPostlikesById() {
        return postlikesById;
    }

    public void setPostlikesById(Collection<Postlikes> postlikesById) {
        this.postlikesById = postlikesById;
    }

    @OneToMany(mappedBy = "userByUser")
    public Collection<Purchase> getPurchasesById() {
        return purchasesById;
    }

    public void setPurchasesById(Collection<Purchase> purchasesById) {
        this.purchasesById = purchasesById;
    }
}