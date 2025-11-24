package model.statement;

import exceptions.FileAlreadyOpenException;
import exceptions.InvalidTypeException;
import model.adt.MyDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.StringType;
import model.value.IValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public record OpenRFileStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        var value = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException();
        String filename = value.toString();

        var filetable = programState.getFileTable();
        if(filetable.containsKey(filename))
            throw new FileAlreadyOpenException();

        try{
            var bufferedReader = new BufferedReader(new FileReader(filename));
            programState.getFileTable().put(filename, bufferedReader);
        } catch (FileNotFoundException e) {
            throw new exceptions.FileNotFoundException();
        }

        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }
    @Override
    public String toString() {
        return String.format("OpenRFile(%s)", expression.toString());
    }
}
