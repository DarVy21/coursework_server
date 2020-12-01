package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;

import Server.Model.DiscountEntity;
import Server.Model.OrdersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DiscountCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "showDiscount":
                commands = command.split(",", 3);
                result = DiscountCommands.showDiscount(commands[2]);
                break;
            case "showDiscountAdmin":
                commands = command.split(",", 2);
                result = DiscountCommands.showDiscountAdmin();
                break;
            case "addDiscount":
                commands = command.split(",", 5);
                result = DiscountCommands.addDiscount(commands[2], commands[3], commands[4]);
                break;
            case "deleteDiscount":
                commands = command.split(",", 3);
                result = DiscountCommands.deleteDiscount(commands[2]);
                break;

        }
        return result;
    }


    private static String deleteDiscount(String idString) {
        int id= Integer.parseInt(idString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        DiscountEntity discount=session.get(DiscountEntity.class,id);
        session.close();
        if(discount==null)return "fail";
        DiscountCommands.delete(discount);
        return "success";
    }

    private static String addDiscount(String clientIdS,String promocodS, String discountS) {
        DiscountEntity discount = new DiscountEntity(Integer.parseInt(clientIdS),promocodS,Integer.parseInt(discountS));
        DiscountCommands.save(discount);
        return "success";
    }

    private static Object showDiscountAdmin() {
        List<DiscountEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from DiscountEntity ").list();
        ArrayList<String> list2=new ArrayList<>();
        for (DiscountEntity discount:list) {
            String id_discount= String.valueOf(discount.getId_discount());
            String idUser= String.valueOf(discount.getUser_id());
            String promocod=discount.getPromocod();
            String discountSize=String.valueOf(discount.getDiscountSize());
            list2.add(id_discount+","+promocod+","+discountSize+","+idUser);
        }
        return list2;
    }
    private static Object showDiscount(String idString) {
        int idUser= Integer.parseInt(idString);
        List<DiscountEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from DiscountEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
        // OrdersEntity order = (OrdersEntity) list.get(0);
        ArrayList<String> list2=new ArrayList<>();
        for (DiscountEntity discount:list) {
            String id_discount= String.valueOf(discount.getId_discount());
            String promocod=discount.getPromocod();
            String discountSize=String.valueOf(discount.getDiscountSize());
            list2.add(id_discount+","+promocod+","+discountSize);
        }
        return list2;
    }
    public static void updateDiscount(DiscountEntity discount){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(discount);
        tx1.commit();
        session.close();
    }
    private static void delete(DiscountEntity discount) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(discount);
        tx1.commit();
        session.close();
    }
    private static void save(DiscountEntity discount) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(discount);
        tx1.commit();
        session.close();
    }
}