package model.statement;

import exceptions.InvalidTypeException;
import exceptions.VariableNotDefinedException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.value.IValue;
import model.value.RefValue;


public record WriteHeapStatement(String variableName, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) {
        int address = getAddress(programState);

        IValue newVal = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(),
                programState.getHeap());
        IValue heapValue = programState.getHeap()
                .lookup(address);
        if (!newVal.getType()
                .equals(heapValue.getType())) {
            throw new InvalidTypeException();
        }

        programState.getHeap()
                .update(address, newVal);
        return null;
    }

    private int getAddress(ProgramState programState) {
        MyIDictionary<String, IValue> symTable = programState.getSymTable();
        MyIHeap heap = programState.getHeap();

        if (!symTable.containsKey(variableName)) {
            throw new VariableNotDefinedException();
        }

        IValue value = symTable.lookup(variableName);

        if (!(value instanceof RefValue)) {
            throw new InvalidTypeException();
        }

        int address = ((RefValue) value).getAddress();
        if (!heap.isDefined(address)) {
            throw new VariableNotDefinedException();
        }
        return address;
    }

    @Override
    public String toString() {
        return String.format("writeHeap(varName='%s', expression=%s)", variableName, expression.toString());
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression.deepCopy());
    }
}

