package model.statement;

import exceptions.ErrorFileCloseException;
import exceptions.FileNotFoundException;
import exceptions.InvalidTypeException;
import exceptions.KeyNotFoundException;
import model.adt.MyDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.StringType;
import model.value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStatement(IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) {
        var value = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeException();
        String filename = value.toString();
        try {
            BufferedReader file = programState.getFileTable().get(filename);
            file.close();
            programState.getFileTable().remove(filename);

        } catch (IOException e) {
            throw new ErrorFileCloseException();
        } catch (KeyNotFoundException e) {
            throw new FileNotFoundException();
        }
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseRFile(%s)", expression.toString());
    }
}
