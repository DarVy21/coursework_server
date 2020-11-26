package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.AdminEntity;
import Server.Model.BasketEntity;
import Server.Model.UsersEntity;
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
        UsersEntity user = new UsersEntity();
        AdminEntity admin=new AdminEntity();
        user.setLogin(login);
        user.setPassword(password);
        admin.setUser(user);
        UserCommands.save(admin);
        return "success";
    }

    private static String deleteUser(String idUserString) {
        int idUSer= Integer.parseInt(idUserString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<UsersEntity> list=session.createQuery("from UsersEntity  where id_user=:id").setParameter("id",idUSer).list();
        session.close();
        UsersEntity user=list.get(0);
        UserCommands.delete(user);
        return "success";
    }

    private static Object showUser() {
        List<UsersEntity> users=HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity").list();
        ArrayList<String>list=new ArrayList();
        for (UsersEntity user:users){
            String id= String.valueOf(user.getIdUser());
            String login=user.getLogin();
            String password=user.getPassword();
            list.add(id+","+login+","+password);
        }
        return list;
    }

    private static String addClient(String login, String password) {
        UsersEntity user = new UsersEntity();
        user.setLogin(login);
        user.setPassword(password);
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

    public static String checkSingInClient(String username, String password) {
        String message="" ;
        List<UsersEntity> users= HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity ").list();
        try {
            for (UsersEntity user : users) {
                String tableLogin = user.getLogin();
                String tablePassword = user.getPassword();
                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    message = Integer.toString(user.getIdUser());
                    break;
                } else message = "fail";
            }
        } catch (Exception var12) {
            System.out.println("Exception in Table of users");
        }
        return message;
    }

    public static String checkSingInAdmin(String username, String password) {
        String message = "";
        List<UsersEntity> users= HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity ").list();
        try {
            for (UsersEntity user : users) {
                String tableLogin = user.getLogin();
                String tablePassword = user.getPassword();
                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    message = "successAdmin";

                    break;
                } else message = "fail";
            }
        } catch (Exception var12) {
            System.out.println("Exception in Table of users");
        }
        return message;
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
