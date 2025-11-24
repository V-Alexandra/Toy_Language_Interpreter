package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    public TextMenu(){
        commands = new HashMap<>();
    }
    void addCommand(Command c){
        commands.put(c.getKey(), c);
    }
    private void printMenu()
    {
        for(Command c : commands.values()) {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }
    public void show()
    {
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Enter a command: ");
            String key=scanner.nextLine();
            Command c= commands.get(key);
            if(c!=null) {
                c.execute();
            }
            else {
                System.out.println("Invalid command");
            }
        }
    }
}

