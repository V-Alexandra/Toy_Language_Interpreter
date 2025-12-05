package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDefinedException;
import model.adt.MyDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;


public record HeapAllocationStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        var symTable = programState.getSymTable();
        var heap = programState.getHeap();

        if (!symTable.containsKey(variableName)) {
            throw new InvalidTypeException();
        }

        IValue varValue = symTable.lookup(variableName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new InvalidTypeException();
        }

        RefType refType = (RefType) varValue.getType();
        IType innerType = refType.getInnerType();
        IValue expValue;
        try {
            expValue = expression.evaluate((MyDictionary<String, IValue>) symTable, heap);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (!expValue.getType().equals(innerType)) {
            throw new VariableNotDefinedException();
        }
        //alocate a new address on the heap and store the expValue and return the new address
        int newAddress = heap.add(expValue);
        symTable.update(variableName, new RefValue(newAddress, innerType));

        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("new(varName='%s', expression='%s')", variableName, expression.toString());
    }
}
