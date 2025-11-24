package model.statement;

import exceptions.InvalidTypeException;
import model.adt.MyDictionary;
import model.adt.MyIStack;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.value.BooleanValue;
import model.value.IValue;

public record IfStatement(IExpression expression, IStatement thenStatement,
                          IStatement elseStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) {
        IValue result = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap());
        if (!(result instanceof BooleanValue(boolean boolVal)))
            throw new InvalidTypeException();
        IStatement chosenStatement = boolVal ? thenStatement : elseStatement;
        MyIStack<IStatement> stack = programState.getExeStack();
        stack.push(chosenStatement);
        programState.setExeStack(stack);
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}", expression.toString(), thenStatement.toString(), elseStatement.toString());
    }
}
