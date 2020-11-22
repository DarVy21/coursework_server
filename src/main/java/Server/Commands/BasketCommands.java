package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.BookEntity;

import Server.Model.UsersEntity;
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
                commands = command.split(",", 4);
                result = BasketCommands.addToBasket(commands[2], commands[3]);
                break;
        }
        return result;
    }

    private static String addToBasket(String id_book, String id_user) {
        int idBook=Integer.parseInt(id_book);
        int idUser=Integer.parseInt(id_user);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BookEntity> book= session.createQuery("FROM BookEntity WHERE id_book=:id").setParameter("id",idBook).list();
        List<UsersEntity> user= session.createQuery("FROM UsersEntity WHERE id_user=:id").setParameter("id",idUser).list();
        session.close();
        BasketEntity basket=new BasketEntity();
        basket.setBook(book.get(0));
        basket.setUser(user.get(0));
        basket.setPrice(book.get(0).getPrice());
        basket.setAmount(1);
        BasketCommands.save(basket);
        return "success";
    }
    public static void save(BasketEntity basket) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(basket);
        tx1.commit();
        session.close();
    }
}