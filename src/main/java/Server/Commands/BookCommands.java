package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;

import Server.Model.BasketEntity;
import Server.Model.BookEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BookCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "ShowBook":
                result = BookCommands.showBook();
                break;
            case "addBook":
                commands = command.split(",", 7);
                result = BookCommands.addBook(commands[2], commands[3], commands[4], commands[5], commands[6]);


                break;
        }
        return result;
    }

    private static String addBook(String nameS,String authorS, String typeS, String amountS,String priceS) {

        BookEntity book = new BookEntity(nameS, authorS, typeS, Integer.parseInt(amountS), Double.parseDouble(priceS));
        BookCommands.save(book);
        return "success";
    }

    private static Object showBook() {
        List<BookEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from BookEntity ").list();
        ArrayList<String> list2=new ArrayList<>();
        for (BookEntity book:list) {
            String id= String.valueOf(book.getId_book());
            String author=book.getAuthor();
            String type=book.getType();
            String name=book.getName();
            String amount= String.valueOf(book.getAmount());
            String price= String.valueOf(book.getPrice());
            list2.add(id+","+name+","+author+","+type+","+amount+","+price);
        }
        return list2;
    }
    public static void updateBook(BookEntity book){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(book);
        tx1.commit();
        session.close();
    }
    private static void save(BookEntity book) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(book);
        tx1.commit();
        session.close();
    }
}