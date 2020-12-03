package Server.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notifications")
public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_notification")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_notification;
    private String user_name;
    private int order_number;
    private String message;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "order_number", referencedColumnName = "order_number", insertable=false, updatable=false)
    private OrdersEntity order;


    public NotificationEntity() {
    }

    public NotificationEntity(String user_name, int order_number, String message) {
        this.user_name=user_name;
        this.order_number=order_number;
        this.message=message;
    }
    public NotificationEntity(OrdersEntity order, String message) {
        this.order=order;
        this.message=message;
    }

    public int getId_notification() {
        return id_notification;
    }

    public void setId_notification(int id_notification) {
        this.id_notification = id_notification;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrdersEntity getOrder() {
        return order;
    }

    public void setOrder(OrdersEntity order) {
        this.order = order;
    }
}
