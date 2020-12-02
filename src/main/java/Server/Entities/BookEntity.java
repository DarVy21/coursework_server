package Server.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "books")
public class BookEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_book;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "type")
    private String type;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private double price;
   @OneToMany(fetch = FetchType.LAZY,
           mappedBy = "book",
            cascade = CascadeType.ALL)
    private List<BasketEntity> basketList;

    public BookEntity(){}

    public BookEntity(int idbook, String name, String author, String type, int amount,double price ){
        this.id_book=idbook;
        this.type=type;
        this.name=name;
        this.author=author;
        this.amount=amount;
        this.price=price;
    }
    public BookEntity( String name, String author, String type,int amount,double price ){

        this.type=type;
        this.name=name;
        this.author=author;
        this.amount=amount;
        this.price=price;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int idbook) {
        this.id_book = idbook;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<BasketEntity> getBasketList() {
        return basketList;
    }

    public void setBasketList(List<BasketEntity> basketList) {
        this.basketList = basketList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEntity)) return false;
        BookEntity that = (BookEntity) o;
        return getId_book() == that.getId_book() &&
                getAmount() == that.getAmount() &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                getName().equals(that.getName()) &&
                getAuthor().equals(that.getAuthor()) &&
                getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_book(), getName(), getAuthor(), getType(), getAmount(), getPrice());
    }
}
