package Server.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "baskets")
public class BasketEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_basket")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBasket;
    @Column(name = "price")
    private double price;
    @Column(name = "amount")
    private int amount;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "user_id", referencedColumnName = "id_user")
    private UsersEntity user;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "book_name", referencedColumnName = "name")
    private BookEntity book;
    public BasketEntity(){}

    public BasketEntity(double price, int amount, UsersEntity user, BookEntity book) {
        this.price = price;
        this.amount = amount;
        this.user = user;
        this.book = book;
    }

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

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public BookEntity getBook() {
        return book;
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