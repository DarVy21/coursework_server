package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.UsersEntity;


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
        }
        return result;
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


}