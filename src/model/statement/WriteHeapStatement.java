package model.statement;

import exceptions.VariableNotDefinedException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.value.IValue;
import model.value.RefValue;



public class WriteHeapStatement implements IStatement{
    String variableName;
    IExpression expression;
    public WriteHeapStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIDictionary<String, IValue> symTable = programState.getSymTable();
        MyIHeap heap = programState.getHeap();

        if (!symTable.containsKey(variableName)) {
            throw new RuntimeException("Variable " + variableName + " not defined in the symbol table.");
        }

        IValue value = symTable.lookup(variableName);

        if (!(value instanceof RefValue)) {
            throw new RuntimeException("Variable " + variableName + " is not a reference.");
        }

        int address = ((RefValue) value).getAddress();
        if (!heap.isDefined(address)) {
            throw new RuntimeException("Address " + address + " is not defined in the heap.");
        }

        IValue newVal = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap());
        IValue heapValue = programState.getHeap().lookup(address);
        if (!newVal.getType().equals(heapValue.getType())) {
            throw new RuntimeException("Type of the expression does not match the type of the referenced location.");
        }

        programState.getHeap().update(address, newVal);
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression.deepCopy());
    }
}

