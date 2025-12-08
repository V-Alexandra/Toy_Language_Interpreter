package model.statement;
import model.adt.*;
import model.program_state.ProgramState;
import model.value.IValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement {
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIStack<IStatement> stack = new MyStack<>();
        stack.push(statement);
        MyIDictionary<String, IValue> symbolTable = programState.getSymTable()
                .clone(); //prevents child threads from
        // modifying the parent's variables in the memory shared
        MyIDictionary<String, BufferedReader> fileTable = programState.getFileTable();
        MyIList<IValue> out = programState.getOut();
        MyIHeap heap = programState.getHeap();

        return new ProgramState(stack, symbolTable, out, fileTable, heap);

    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }
}
