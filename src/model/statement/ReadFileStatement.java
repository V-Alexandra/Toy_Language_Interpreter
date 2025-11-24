
package model.statement;

import exceptions.*;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntegerValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(IExpression expression, String variableName) implements IStatement {
    //TODO: add throws exception for all methods

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIDictionary<String, IValue> symTable = programState.getSymTable();
        if (!symTable.containsKey(variableName)) {
            throw new VariableNotDefinedException();
        }
        if(!symTable.get(variableName).getType().equals(new IntType()))
            throw new InvalidTypeException();
        var result = expression.evaluate((MyDictionary<String, IValue>) symTable, programState.getHeap());
        if(!result.getType().equals(new StringType()))
            throw new InvalidTypeException();
        String filename = result.toString();
        if(!programState.getFileTable().containsKey(filename))
            throw new FileNotFoundException();

        BufferedReader file = programState.getFileTable().get(filename);
        try{
            String line = file.readLine();
            if (line == null) {
                // End of file reached - set variable to default value 0
                programState.getSymTable().put(variableName, new IntegerValue(0));
            } else {
                int value = Integer.parseInt(line);
                IntegerValue intValue = new IntegerValue(value);
                programState.getSymTable().put(variableName, intValue);
            }

        } catch (IOException e) {
            throw new ErrorReadingFileException();
        }
        catch (NumberFormatException e) {
            throw new NotNumberException();
        }
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }
    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expression.toString(), variableName);
    }
}