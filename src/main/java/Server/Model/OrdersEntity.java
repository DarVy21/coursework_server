package Server.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrder;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "total_amount")
    private int totalAmount;
    @Column(name = "status")
    private String status;


    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return idOrder == that.idOrder &&
                Double.compare(that.totalPrice, totalPrice) == 0 &&
                totalAmount == that.totalAmount &&
                Objects.equals(status, that.status);
    }

}