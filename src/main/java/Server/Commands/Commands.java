package Server.Commands;



public class Commands {

    public static Object split(String command) {
        String com =command;
        String[] commandNumber = command.split(",", 2);
        String[] commands;
        Object result = true;
        switch (commandNumber[0]) {
            case "User":
                result = UserCommands.split(command);
                break;
            case "Book":
                result = BookCommands.split(command);
                break;
            case "Basket":
                result = BasketCommands.split(command);
                break;
            case "Order":
                result = OrderCommands.split(command);
                break;
        }
        return result;
    }

}
