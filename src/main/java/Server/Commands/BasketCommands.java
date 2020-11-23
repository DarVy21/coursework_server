package Server.Commands;


import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.BookEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
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
            case "ShowBasket":
                result = BasketCommands.showBasket();
                break;
            case "deleteBasket":
                commands = command.split(",", 3);
                result = BasketCommands.deleteBasket(commands[2]);
                break;
        }
        return result;
    }

    private static String deleteBasket(String id_temp) {
        int id= Integer.parseInt(id_temp);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BasketEntity> list= session.createQuery("from BasketEntity where idBasket=:id").setParameter("id",id).list();
        session.close();
        BasketEntity basket=new BasketEntity();
        basket= list.get(0);
        BasketCommands.delete(basket);
        return "success";
    }

    private static Object showBasket() {
        List<BasketEntity> list = HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from BasketEntity").list();
        ArrayList<String> list2=new ArrayList<>();
        for (BasketEntity basket:list) {
            String name=basket.getBook().getName();
            String amount= String.valueOf(basket.getAmount());
            String price= String.valueOf(basket.getPrice());
            String id= String.valueOf(basket.getIdBasket());
            list2.add(name+" "+amount+" "+price+" "+id);
        }
        return list2;
    }

    private static String addToBasket(String id_book, String id_user) {
        int idBook=Integer.parseInt(id_book);
        int idUser=Integer.parseInt(id_user);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BookEntity> book= session.createQuery("FROM BookEntity WHERE id_book=:id").setParameter("id",idBook).list();
        List<UsersEntity> user= session.createQuery("FROM UsersEntity WHERE id_user=:id").setParameter("id",idUser).list();
       session.close();
        BasketEntity basket=new BasketEntity();
       basket.setBook(book.get(0));
       basket.setUser(user.get(0));
       basket.setPrice(book.get(0).getPrice());
       basket.setAmount(1);
       BasketCommands.save(basket);
        book.get(0).setAmount(book.get(0).getAmount()-1);
       BookCommands.updateBook(book.get(0));

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
