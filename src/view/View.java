//package view;
//
//
//import controller.Controller;
//import model.adt.*;
//import model.expression.ArithmeticExpression;
//import model.expression.ValueExpression;
//import model.expression.VariableExpression;
//import model.program_state.ProgramState;
//import model.statement.*;
//import model.type.BooleanType;
//import model.type.IntType;
//import model.value.BooleanValue;
//import model.value.IValue;
//import model.value.IntegerValue;
//import repository.IRepository;
//import repository.Repository;
//
//import java.util.Scanner;
//
//public class View {
//    public void start() {
//        boolean done = false;
//        while (!done) {
//            try {
//                printMenu();
//                Scanner readOption = new Scanner(System.in);
//                int option = readOption.nextInt();
//                if (option == 0) {
//                    done = true;
//                } else if (option == 1) {
//                    runProgram1();
//                } else if (option == 2) {
//                    runProgram2();
//                } else if (option == 3) {
//                    runProgram3();
//                } else {
//                    System.out.println("Invalid input!");
//                }
//            } catch (Exception exception) {
//                System.out.println(exception.getMessage());
//            }
//        }
//    }
//
//    private static void printMenu() {
//        System.out.println("MENU: ");
//        System.out.println("0. Exit.");
//        System.out.println("1. Run the first program: \n\tint v;v=2;Print(v)");
//        System.out.println("2. Run the second program: \n\tint a;int b;a=2+3*5;b=a-4/2+7;Print(b)");
//        System.out.println("3. Run the third program: \n\tbool a;int v;a=true;(If a Then v=2 Else v=3);Print(v)");
//        System.out.println("Choose an option: ");
//    }
//
//    private void runProgram1() {
//        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                        new PrintStatement(new VariableExpression("v"))));
//        runStatement(ex1);
//    }
//
//    private void runProgram2() {
//        IStatement ex2 = new CompoundStatement(
//                new VariableDeclarationStatement("a", new IntType()),
//                new CompoundStatement(
//                        new AssignmentStatement("a",
//                                new ArithmeticExpression('+',
//                                        new ValueExpression(new IntegerValue(2)),
//                                        new ArithmeticExpression('*',
//                                                new ValueExpression(new IntegerValue(3)),
//                                                new ValueExpression(new IntegerValue(5))))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("b", new IntType()),
//                                new CompoundStatement(
//                                        new AssignmentStatement("b",
//                                                new ArithmeticExpression('+',
//                                                        new ArithmeticExpression('-',
//                                                                new VariableExpression("a"),
//                                                                new ArithmeticExpression('/',
//                                                                        new ValueExpression(new IntegerValue(4)),
//                                                                        new ValueExpression(new IntegerValue(2)))),
//                                                        new ValueExpression(new IntegerValue(7)))),
//                                        new PrintStatement(new VariableExpression("b"))))));
//        runStatement(ex2);
//    }
//
//    private void runProgram3() {
//        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BooleanType()),
//                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
//                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
//                                new CompoundStatement(new IfStatement(new VariableExpression("a"),
//                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))),
//                                        new PrintStatement(new VariableExpression("v"))))));
//        runStatement(ex3);
//    }
//
//    private void runStatement(IStatement statement) {
//        MyIStack<IStatement> executionStack = new MyStack<>();
//        MyIDictionary<String, IValue> symbolTable = new MyDictionary<>();
//        MyIList<IValue> output = new MyList<>();
//        ProgramState state = new ProgramState(executionStack, symbolTable, output, statement);
//        IRepository repository = new Repository(state);
//        Controller controller = new Controller(repository);
//        controller.setDisplayFlag(true);
//        controller.allSteps();
//        System.out.println("Result is " + state.getOut().toString().replace('[', ' ').replace(']', ' '));
//    }
//
