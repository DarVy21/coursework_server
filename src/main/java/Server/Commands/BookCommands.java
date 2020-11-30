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
            case "deleteBook":
                commands = command.split(",", 3);
                result = BookCommands.deleteBook(commands[2]);
                break;
            case "editBook":
                commands = command.split(",", 3);
                result = BookCommands.editBook(commands[2]);
                break;

        }
        return result;
    }

    private static Object editBook(String idString) {
        int id= Integer.parseInt(idString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BookEntity> product=session.createQuery("from BookEntity where id_book=:id").setParameter("id",id).list();
        if(product==null)return "fail";
        /*String type=product.get(0).getType();
        String name=product.get(0).getName();
        String amount= String.valueOf(product.get(0).getAmount());
        String price= String.valueOf(product.get(0).getPrice());
        try {
        //ServerThread.output.writeObject(type+","+name+","+amount+","+price);
        //String edit= (String) ServerThread.input.readObject();
        String[] info=edit.split(",",4);
        product.get(0).setType(info[0]);
            product.get(0).setName(info[1]);
            product.get(0).setAmount(Integer.parseInt(info[2]));
            product.get(0).setPrice(Double.parseDouble(info[3]));
            ProductCommands.updateProduct(product.get(0));
        } catch (IOException |ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return "success";
    }

    private static String deleteBook(String idString) {
        int id= Integer.parseInt(idString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        //List<ProductEntity> product = session.createQuery("from ProductEntity where id_product=:id").setParameter("id", id).list();
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