package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.OrdersEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
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
        }
        return result;
    }

    private static Object showOrder(String idString) {
        int idUser= Integer.parseInt(idString);
        List<OrdersEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from OrdersEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
        ArrayList<String> list2=new ArrayList<>();
        for (OrdersEntity order:list) {
            String status=order.getStatus();
            String price= String.valueOf(order.getTotalPrice());
            String orderNumber= String.valueOf(order.getOrderNumber());
            list2.add(orderNumber+","+price+","+status);
        }
        return list2;
    }

    private static String addToOrder(String idUserString) {
        int idUser= Integer.parseInt(idUserString);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<UsersEntity> user= session.createQuery("FROM UsersEntity WHERE id_user=:id").setParameter("id",idUser).list();
        List<BasketEntity> list=session.createQuery("FROM BasketEntity where user.id_user=:id").setParameter("id",idUser).list();
        session.close();
        int totalPrice=0;
        for (BasketEntity basket:list)
            totalPrice+=basket.getPrice();
        OrdersEntity order=new OrdersEntity();
        order.setUser(user.get(0));
        order.setTotalPrice(totalPrice);
        OrderCommands.save(order);
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
}