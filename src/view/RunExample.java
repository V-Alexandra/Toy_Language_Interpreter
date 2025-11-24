package view;

import controller.Controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    public void execute() {
        try {
            System.out.println("Display the steps?(yes/no)");
            Scanner option = new Scanner(System.in);
            String optionString = option.next();
            boolean displaySteps = Objects.equals(optionString, "yes");
            controller.setDisplayFlag(displaySteps);
            controller.allSteps();
            if (!displaySteps) {
                System.out.println("\nFinal Output: " + controller.getRepository().getCurrentState().getOut().toString());
            }

            System.out.println("Program executed successfully! Check the log file for details.");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}