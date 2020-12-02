package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;

import Server.Entities.AdminEntity;
import Server.Entities.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "checkSingInClient":
                commands = command.split(",", 4);
                result = UserCommands.checkSingInClient(commands[2],commands[3]);
                break;
            case "checkSingInAdmin":
                commands = command.split(",", 4);
                result = UserCommands.checkSingInAdmin(commands[2],commands[3]);
                break;
            case "checkLogin":
                commands = command.split(",", 3);
                result = UserCommands.checkSingLogin(commands[2]);
                break;
            case "addClient":
                commands = command.split(",", 4);
                result = UserCommands.addClient(commands[2], commands[3]);
                break;
            case "addAdmin":
                commands = command.split(",", 4);
                result = UserCommands.addAdmin(commands[2], commands[3]);
                break;
            case "deleteUser":
                commands = command.split(",", 3);
                result = UserCommands.deleteUser(commands[2]);
                break;
            case "showUser":
                result = UserCommands.showUser();
                break;
        }
        return result;
    }

    private static String addAdmin(String login, String password) {
        UsersEntity user = new UsersEntity(login,password);
        AdminEntity admin=new AdminEntity(user);
        UserCommands.save(admin);
        return "success";
    }

    private static String deleteUser(String idUserString) {
        int idUSer= Integer.parseInt(idUserString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        UsersEntity user=session.get(UsersEntity.class,idUSer);
        session.close();
        UserCommands.delete(user);
        return "success";
    }

    private static Object showUser() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity").list();
    }

    private static String addClient(String login, String password) {
        UsersEntity user = new UsersEntity(login,password);
        UserCommands.save(user);
        return "success";
    }

    private static String checkSingLogin(String loginTemp) {
        List<UsersEntity> list =null;
        list=HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity where login=:name").setParameter("name", loginTemp).list();
        if(list!= null) return "success";
        else return "fail";
    }

    public static String checkSingInClient(String login, String password) {
        UsersEntity users= (UsersEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity where login=:log and password=:pas").
                setParameter("log",login).setParameter("pas",password).uniqueResult();
        if(users==null)return "fail";
        return String.valueOf(users.getIdUser());
    }

    public static String checkSingInAdmin(String login, String password) {
        AdminEntity admin= (AdminEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM AdminEntity where  user.login=:log and user.password=:pas").
                setParameter("log",login).setParameter("pas",password).uniqueResult();
        if(admin==null) return"fail";
        return "successAdmin";
    }

    private static void save(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    private static void delete(UsersEntity user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
}