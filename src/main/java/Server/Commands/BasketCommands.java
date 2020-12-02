package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Entities.BasketEntity;

import Server.Entities.BookEntity;
import Server.Entities.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BasketCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "addToBasket":
                commands = command.split(",", 5);
                result = BasketCommands.addToBasket(commands[2], commands[3], commands[4]);
                break;
            case "ShowBasket":
                commands = command.split(",", 3);
                result = BasketCommands.showBasket(commands[2]);
                break;
            case "deleteBasket":
                commands = command.split(",", 3);
                result = BasketCommands.deleteBasket(commands[2]);
                break;
        }
        return result;
    }

    public static void deleteAll(int idUser) {
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BasketEntity> list= session.createQuery(" from BasketEntity where user.id_user=:id").setParameter("id",idUser).list();
        session.close();
        for(BasketEntity basket: list)
            BasketCommands.delete(basket);
    }

    private static String deleteBasket(String id_temp) {
        int id= Integer.parseInt(id_temp);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BasketEntity basket= session.get(BasketEntity.class,id);
        session.close();
        if(basket==null)return"fail";
        BasketCommands.delete(basket);
        return "success";
    }

    private static Object showBasket(String idString) {
        int idUser= Integer.parseInt(idString);
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from BasketEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
    }


    private static String addToBasket(String id_book,String amountString, String id_user) {
        int idBook=Integer.parseInt(id_book);
        int idUser=Integer.parseInt(id_user);
        int amount= Integer.parseInt(amountString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BookEntity book= session.get(BookEntity.class,idBook);
        if(book==null|| book.getAmount()<amount) return "fail";
        UsersEntity user= session.get(UsersEntity.class,idUser);
        session.close();
        BasketEntity basket=new BasketEntity(book.getPrice()*amount,amount,user,book);
        BasketCommands.save(basket);
        book.setAmount(book.getAmount()-amount);
        BookCommands.updateBook(book);
        return "success";
    }
    private static void save(BasketEntity basket) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(basket);
        tx1.commit();
        session.close();
    }
    private static void delete(BasketEntity basket) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(basket);
        tx1.commit();
        session.close();
    }
}