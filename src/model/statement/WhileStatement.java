package model.statement;

import exceptions.InvalidTypeException;
import model.adt.MyDictionary;
import model.adt.MyIStack;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;

public record WhileStatement(IExpression expression, IStatement statement) implements IStatement {

    @Override
    public ProgramState execute(ProgramState programState) {
        IValue conditionValue = expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap());
        if (!(conditionValue.getType() instanceof BooleanType))
            throw new InvalidTypeException();
        BooleanValue conditionBoolValue = (BooleanValue) conditionValue;
        if (conditionBoolValue.value()) {
            MyIStack<IStatement> stack = programState.getExeStack();
            stack.push(this); //for next iteration
            stack.push(statement); //body
        }
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + expression.toString() + ") " + statement.toString();
    }
}