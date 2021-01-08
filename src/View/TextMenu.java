package View;

import Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commandMap;
    public TextMenu(){
        this.commandMap = new HashMap<>();
    }

    public void addCommand(Command newCommand){
        this.commandMap.put(newCommand.getKey(),newCommand);
    }
    private void printMenu(){
        for(Command c : commandMap.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Your choice: ");
            String key = scanner.nextLine();
            Command command = commandMap.get(key);
            if(command == null){
                System.out.println("Invalid key!");
                continue;
            }
            command.execute();

        }
    }
}
