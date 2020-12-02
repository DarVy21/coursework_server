package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;

import Server.Entities.DiscountEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    private static Object showDiscountAdmin () {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from DiscountEntity ").list();

    }
    private static Object showDiscount(String idString) {
        int idUser= Integer.parseInt(idString);
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from DiscountEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
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