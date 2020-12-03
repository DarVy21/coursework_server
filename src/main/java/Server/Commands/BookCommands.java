package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;

import Server.Entities.BookEntity;
import Server.ServerThread;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            case "deleteBook":
                commands = command.split(",", 3);
                result = BookCommands.deleteBook(commands[2]);
                break;
            case "editBook":
                commands = command.split(",", 3);
                result = BookCommands.editBook(commands[2]);
                break;
            case "showTypes":
                result = BookCommands.showTypes();
                break;
        }
        return result;
    }

    private static Object editBook(String idString) {
        int id = Integer.parseInt(idString);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BookEntity book = session.get(BookEntity.class, id);
        session.close();
        if (book == null) return "fail";
        try {
            ServerThread.getOutput().writeObject(book);
            book= (BookEntity) ServerThread.getInput().readObject();
            BookCommands.updateBook(book);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "success";
    }


    private static String deleteBook(String idString) {
        int id= Integer.parseInt(idString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BookEntity book=session.get(BookEntity.class,id);
        session.close();
        if(book==null)return "fail";
        BookCommands.delete(book);
        return "success";
    }

    private static String addBook(String nameS,String authorS, String typeS, String amountS,String priceS) {
        BookEntity book = new BookEntity(nameS, authorS, typeS, Integer.parseInt(amountS), Double.parseDouble(priceS));
        BookCommands.save(book);
        return "success";
    }

    private static Object showBook () {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from BookEntity ").list();

    }
    private static ArrayList<String> showTypes () {
        List<String> listType= HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("select type from BookEntity ").list();
        double novel = 0;
        double detective = 0;
        double science = 0;
        double utopia = 0;
        double antiutopia = 0;
        double psychology = 0;
        double other = 0;

            for (String type:listType){
                if (type.equals("Роман")) novel ++;
                if (type.equals("Детектив")) detective ++;
                if (type.equals("Научные")) science ++;
                if (type.equals("Утопия")) utopia ++;
                if (type.equals("Антиутопия")) antiutopia ++;
                if (type.equals("Психология")) psychology ++;
                if (type.equals("Другое")) other ++;

            }
            ArrayList<String> list = new ArrayList<>();
            double all =(novel+detective + science + utopia+ antiutopia + psychology + other);
            double novelPart = novel / all;
            double detectivePart = detective / all;
            double sciencePart = science / all;
            double antiutopiaPart = antiutopia /all;
            double psychologyPart = psychology / all;
            double utopiaPart = utopia / all;
            double otherPart = other / all;
            list.add(Double.toString(novelPart));
            list.add(Double.toString(detectivePart));
            list.add(Double.toString(sciencePart));
            list.add(Double.toString(antiutopiaPart));
            list.add(Double.toString(psychologyPart));
            list.add(Double.toString(utopiaPart));
            list.add(Double.toString(otherPart));

        return list;

    }
    public static void updateBook(BookEntity book){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(book);
        tx1.commit();
        session.close();
    }
    private static void delete(BookEntity book) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(book);
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