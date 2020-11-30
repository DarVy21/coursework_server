package Server.Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javafx.fxml.Initializable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class OrdersEntity  {
    @Id
    @Column(name = "order_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNumber;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "total_amount")
    private int totalAmount;
    @Column(name = "status")
    private String status="в обработке";
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "user_name", referencedColumnName = "login")
    private UsersEntity user;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int idOrder) {
        this.orderNumber = idOrder;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderNumber == that.orderNumber &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                Objects.equals(status, that.status);
    }

}