package model.statement;

import model.adt.MyDictionary;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;


public class HeapAllocationStatement implements IStatement{
    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState programState) {
        var symTable = programState.getSymTable();
        var heap = programState.getHeap();

        if (!symTable.containsKey(variableName)) {
            throw new RuntimeException("Variable " + variableName + " is not declared in the Symbol Table.");
        }

        IValue varValue = symTable.lookup(variableName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new RuntimeException("Variable " + variableName + " is not of type RefType.");
        }

        RefType refType = (RefType) varValue.getType();
        IType locationType = refType.getInnerType();

        IValue expValue = null;
        try {
            expValue = expression.evaluate((MyDictionary<String, IValue>) symTable, heap);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        if (!expValue.getType().equals(locationType)) {
            throw new RuntimeException("Type of the expression does not match the referenced location type.");
        }

        int newAddress = heap.add(expValue);

        symTable.update(variableName, new RefValue(newAddress, locationType));

        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ")";
    }
}
