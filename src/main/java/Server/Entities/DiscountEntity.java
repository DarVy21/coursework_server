package Server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "discounts")
public class DiscountEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_discount;

    private int user_id;
    private String promocod;
    @Column(name = "discount_size")
    private int discountSize;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "user_id", referencedColumnName = "id_user",  insertable=false, updatable=false)
    private UsersEntity user;

    public DiscountEntity(){};

    public DiscountEntity(int id_discount, int user_id, String promocod, int discountSize) {
        this.id_discount = id_discount;
        this.user_id = user_id;
        this.promocod = promocod;
        this.discountSize = discountSize;
    }
    public DiscountEntity( int user_id, String promocod, int discountSize) {
        this.user_id = user_id;
        this.promocod = promocod;
        this.discountSize = discountSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountEntity)) return false;
        DiscountEntity that = (DiscountEntity) o;
        return getId_discount() == that.getId_discount() &&
                getUser_id() == that.getUser_id() &&
                getDiscountSize() == that.getDiscountSize() &&
                getPromocod().equals(that.getPromocod()) &&
                getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_discount(), getUser_id(), getPromocod(), getDiscountSize(), getUser());
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
    public UsersEntity getUser() {
        return user;
    }
    public int getId_discount() {
        return id_discount;
    }

    public void setId_discount(int id_discount) {
        this.id_discount = id_discount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPromocod() {
        return promocod;
    }

    public void setPromocod(String promocod) {
        this.promocod = promocod;
    }

    public int getDiscountSize() {
        return discountSize;
    }

    public void setDiscountSize(int discountSize) {
        this.discountSize = discountSize;
    }
}
