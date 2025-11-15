package repository;

import model.program_state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStates();

    void addProgram(ProgramState programState);

    ProgramState getCurrentState();

    void setProgramStates(List<ProgramState> programStates);

    void logProgramStateExecution(ProgramState programState) throws IOException;

    void emptyLogFile() throws IOException;
}
