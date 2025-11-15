package repository;

import exceptions.ErrorWriteToFileException;
import model.program_state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    List<ProgramState> programStates;
    String logFilePath;

    public Repository(ProgramState programState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return this.programStates;
    }

    @Override
    public void addProgram(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public ProgramState getCurrentState() {
        return this.programStates.get(0);
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(this.programStates.get(0).toString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}

