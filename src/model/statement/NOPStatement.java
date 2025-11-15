package model.statement;

import model.program_state.ProgramState;

public class NOPStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new NOPStatement();
    }

    @Override
    public String toString() {
        return "";
    }
}
