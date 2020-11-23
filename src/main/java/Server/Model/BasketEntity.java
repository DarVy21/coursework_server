package Server.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "baskets")
public class BasketEntity implements Serializable {
    @Id
    @Column(name = "id_basket")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBasket;
    @Column(name = "price")
    private double price;
    @Column(name = "amount")
    private int amount;
    @ManyToOne(fetch= FetchType.LAZY,
            cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UsersEntity user;
    @ManyToOne(fetch= FetchType.LAZY,
            cascade= CascadeType.ALL)
    @JoinColumn(name = "book_name", referencedColumnName = "name")
    private BookEntity book;
    public BasketEntity(){}

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
    public UsersEntity getUser() {
        return user;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketEntity that = (BasketEntity) o;
        return idBasket == that.idBasket &&
                Double.compare(that.price, price) == 0 &&
                amount == that.amount;
    }
}