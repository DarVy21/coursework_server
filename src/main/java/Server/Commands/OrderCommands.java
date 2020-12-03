package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Entities.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "addToOrder":
                commands = command.split(",", 3);
                result = OrderCommands.addToOrder(commands[2]);
                break;
            case "showOrder":
                commands = command.split(",", 3);
                result = OrderCommands.showOrder(commands[2]);
                break;
            case "showNotifications":
                commands = command.split(",", 3);
                result = OrderCommands.showNotifications(commands[2]);
                break;
            case "showOrderAdmin":
                commands = command.split(",", 2);
                result = OrderCommands.showOrderAdmin();
                break;
            case "changeStatus":
                commands = command.split(",", 4);
                result = OrderCommands.changeStatus(commands[2],commands[3]);
                break;
            case "showPrice":
                commands = command.split(",", 3);
                result = OrderCommands.showPrice(commands[2]);
                break;
            case "discountPrice":
                commands = command.split(",", 4);
                result = OrderCommands.discountPrice(commands[2],commands[3]);
                break;
        }
        return result;
    }

    private static String changeStatus(String idOrderS,String statusOrderS){
        int idOrder= Integer.parseInt(idOrderS);
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            List<OrdersEntity> list = session.createQuery("from OrdersEntity WHERE orderNumber=:id").setParameter("id", idOrder).list();
            OrdersEntity order = (OrdersEntity) list.get(0);
            order.setStatus(statusOrderS);
            session.close();
            OrderCommands.updateOrder(order);
            String orderNumber= String.valueOf(order.getOrderNumber());
            String status = order.getStatus();
            String message = "изменение статуса в заказе №" + orderNumber;
           if (!status.equals("В обработке")){
                NotificationEntity notification= new NotificationEntity(order.getUser().getLogin(),order.getOrderNumber(),message);
                OrderCommands.save(notification);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return "success";

    }

    private static List<String> showNotifications(String idString) {
        int idUser= Integer.parseInt(idString);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<String> list= session. createQuery("select message from NotificationEntity WHERE order.user.id_user=:id").setParameter("id",idUser).list();
        List<NotificationEntity> listnot= session. createQuery("from NotificationEntity WHERE order.user.id_user=:id").setParameter("id",idUser).list();
        session.close();
        for (NotificationEntity not: listnot){
            OrderCommands.delete(not);
        }
       return list;



    }

    private static String discountPrice(String idUserS,String promocodS){
        int idUser= Integer.parseInt(idUserS);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<OrdersEntity> list= session.createQuery("FROM OrdersEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
        List<DiscountEntity> discList=session.createQuery("FROM DiscountEntity where promocod=:id").setParameter("id",promocodS).list();
        session.close();
        if (discList.size() ==0 ) {
            return "fail";
        }
        else {
            OrdersEntity order = (OrdersEntity) list.get(list.size() - 1);
            DiscountEntity discEnt = (DiscountEntity) discList.get(0);
            int discountSize = discEnt.getDiscountSize();
            double price = order.getTotalPrice();
            double discountPrice = price * ((100 - discountSize) * 0.01);
            order.setTotalPrice(discountPrice);
            OrderCommands.updateOrder(order);
            String result = String.valueOf(discountPrice);
            return result;
        }

    }

    private static String showPrice(String idString) {
        int idUser= Integer.parseInt(idString);
        List<OrdersEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from OrdersEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
         OrdersEntity order = (OrdersEntity) list.get(list.size()-1);
        String result = String.valueOf(order.getTotalPrice());

        return result;
    }

    private static Object showOrder(String idString) {
        int idUser= Integer.parseInt(idString);
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from OrdersEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
    }

    private static Object showOrderAdmin () {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from OrdersEntity ").list();

    }


    private static String addToOrder(String idUserString) {
        int idUser= Integer.parseInt(idUserString);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<UsersEntity> user= session.createQuery("FROM UsersEntity WHERE id_user=:id").setParameter("id",idUser).list();
        List<BasketEntity> list=session.createQuery("FROM BasketEntity where user.id_user=:id").setParameter("id",idUser).list();
        session.close();
        double totalPrice=0;
        int totalAmount=0;
        for (BasketEntity basket:list) {
            totalPrice += basket.getPrice();
            totalAmount += basket.getAmount();
        }
        OrdersEntity order=new OrdersEntity();
        order.setUser(user.get(0));
        order.setTotalPrice(totalPrice);
        order.setTotalAmount(totalAmount);
        OrderCommands.save(order);
        BasketCommands.deleteAll(idUser);
        return "success";
    }
    private static void save(OrdersEntity order) {
        try {

            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(order);
            tx1.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void save(NotificationEntity notification) {
        try {

            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(notification);
            tx1.commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void updateOrder(OrdersEntity order){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(order);
        tx1.commit();
        session.close();
    }
    private static void delete(NotificationEntity notification) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(notification);
        tx1.commit();
        session.close();
    }


}