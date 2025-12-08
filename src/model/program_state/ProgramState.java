package model.program_state;

import exceptions.StackIsEmptyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statement.IStatement;
import model.value.IValue;

import java.io.BufferedReader;
import java.util.Map;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private final MyIDictionary<String, BufferedReader> fileTable;
    private IStatement originalProgram;
    private MyIHeap heap;

    private final int id;
    private static int lastId = 0;
    private static synchronized int newProgramStateId() {
        lastId++;
        return lastId;
    }
    public int getId() {
        return id;
    }
    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, IStatement originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = heap;
        this.exeStack.push(originalProgram);
        this.id = newProgramStateId();
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = newProgramStateId();
    }
    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public ProgramState oneStep() {
        if(exeStack.isEmpty())
            throw new StackIsEmptyException();
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);

    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public String exeStackToString() {
        return exeStack.toString();
    }

    public String symTableToString() {
        return symTable.toString();
    }

    public String outListToString() {
        return out.toString();
    }

    public MyIHeap getHeap() {
        return heap;
    }
    @Override
    public String toString(){
        String str = "Program state\n" +
                "Id: "+id+"\n"+
                "Exe Stack: " + exeStack.toString() + " \n" +
                "Sym Table: " + symTable + " \n" +
                "File Table: " + fileTable + "\n" +
                "Heap Table: " + heap.toString() + "\n" +
                "Output Console: " + out + " \n";
        return str;
    }

}

