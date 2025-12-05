package model.statement;

import model.adt.MyIStack;
import model.program_state.ProgramState;

public record CompoundStatement(IStatement firstStatement, IStatement secondStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        MyIStack<IStatement> stack = programState.getExeStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        programState.setExeStack(stack);
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s;%s)", firstStatement.toString(), secondStatement.toString());
    }
}
