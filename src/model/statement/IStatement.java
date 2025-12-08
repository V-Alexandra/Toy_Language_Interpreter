package model.statement;

import model.program_state.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState programState);

    String toString();

    IStatement deepCopy();
}
