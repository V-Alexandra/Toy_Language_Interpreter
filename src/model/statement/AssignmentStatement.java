package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDefinedException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.value.IValue;

public record AssignmentStatement(String key, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIDictionary<String, IValue> symbolTable = programState.getSymTable();
        if (!symbolTable.containsKey(key)) {
            throw new VariableNotDefinedException();
        }
        IValue value = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap()); //error
        if (symbolTable.get(key).getType().equals(value.getType())) {
            symbolTable.update(key, value);
        } else
            throw new InvalidTypeException();
        programState.setSymTable(symbolTable);
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(key, expression.deepCopy());
    }

    @Override
    public String toString() {
        return key + " = " + expression.toString();
    }
}
