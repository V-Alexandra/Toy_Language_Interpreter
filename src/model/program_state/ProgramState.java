package model.program_state;

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

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap, IStatement originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = heap;
        this.exeStack.push(originalProgram);
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
    public String toString() {
        return "ExeStack:\n" + exeStack.toString() + "\nSymTable:\n" + symTable.toString() + "\nHeapTable\n" + heap.toString() + "\nOutput:\n" + out.toString() + "\n" + fileTable.toString() + "\n";
    }

}

