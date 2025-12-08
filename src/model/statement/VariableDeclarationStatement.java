package model.statement;

import exceptions.VariableIsAlreadyDeclared;
import model.adt.MyIDictionary;
import model.program_state.ProgramState;
import model.type.IType;
import model.value.IValue;

public record VariableDeclarationStatement(String name, IType type) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        MyIDictionary<String, IValue> symTable = programState.getSymTable();
        if (symTable.containsKey(name)) {
            throw new VariableIsAlreadyDeclared();
        }
        symTable.put(name, type.getDefaultValue());
        programState.setSymTable(symTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(name, type.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("%s=(%s)", name, type.toString());
    }
}
