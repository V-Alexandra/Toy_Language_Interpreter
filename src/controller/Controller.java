package controller;

import exceptions.StackIsEmptyException;
import model.adt.MyIStack;
import model.program_state.ProgramState;
import model.statement.IStatement;
import repository.IRepository;
import java.io.IOException;

public class Controller {
    IRepository repository;
    boolean displayFlag = false;
    private final GarbageCollector garbageCollector = new GarbageCollector();

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState state) {
        MyIStack<IStatement> stack = state.getExeStack();
        if(stack.isEmpty())
            throw new StackIsEmptyException();
        IStatement currentStatement = stack.pop();
        state.setExeStack(stack);
        return currentStatement.execute(state);
    }

    public void allSteps() throws IOException {
        ProgramState program = this.repository.getCurrentState();
        repository.emptyLogFile();
        display(program);
        repository.logProgramStateExecution(program);
        while(!program.getExeStack().isEmpty()) {
            program = oneStep(program);
            program.getHeap().setContent(garbageCollector.safeGarbageCollector(
                    garbageCollector.getAddrFromSymTable(program.getSymTable().getContent().values()),
                    program.getHeap().getContent()));

            display(program);
            repository.logProgramStateExecution(program);
        }

    }
    private void display(ProgramState program){
        if(displayFlag){
            System.out.println(program.toString());
        }
    }

    public IRepository getRepository() {
        return repository;
    }
}