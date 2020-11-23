package Server.Commands;

public class OrderCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "checkSingInClient":
                commands = command.split(",", 4);
               // result = UserCommands.checkSingInClient(commands[2],commands[3]);
                break;
        }
        return result;
    }
}
