package maxwell_lt.socialmediaproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
    private static final long serialVersionUID = -5652455383562659476L;
    private int id;
    private BigDecimal pricePaid;
    private int likesBought;
    private Timestamp timestamp;
    @JsonManagedReference
    private User user;

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
    @Column(name = "pricePaid", nullable = false, precision = 4)
    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    @Basic
    @Column(name = "likesBought", nullable = false)
    public int getLikesBought() {
        return likesBought;
    }

    public void setLikesBought(int likesBought) {
        this.likesBought = likesBought;
    }

    @Basic
    @Column(name = "timestamp", nullable = false)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id &&
                likesBought == purchase.likesBought &&
                Objects.equals(pricePaid, purchase.pricePaid) &&
                Objects.equals(timestamp, purchase.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pricePaid, likesBought, timestamp);
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
