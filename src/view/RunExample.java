package view;

import controller.Controller;
import java.util.Scanner;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    public void execute() throws InterruptedException {

        System.out.println("Do you want to display the steps?[true/false]");
        Scanner readOption = new Scanner(System.in);
        boolean option = readOption.nextBoolean();
        controller.setDisplayFlag(option);
        controller.allSteps();

    }
}