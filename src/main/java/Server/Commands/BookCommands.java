package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BookEntity;


import java.util.ArrayList;
import java.util.List;

public class BookCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 2);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "ShowBook":
                result = BookCommands.showBook();
                break;
        }
        return result;
    }

    private static Object showBook() {
        List<BookEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from BookEntity ").list();
        ArrayList<String> list2=new ArrayList<>();
        for (BookEntity book:list) {
            String id= String.valueOf(book.getIdbook());
            String type=book.getType();
            String name=book.getName();
            String author=book.getAuthor();
            String amount= String.valueOf(book.getAmount());
            String price= String.valueOf(book.getPrice());
            list2.add(id+"  "+name+" "+author+" "+type+" "+amount+" "+price);
        }
        return list2;
    }
}