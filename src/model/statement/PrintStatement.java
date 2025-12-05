package model.statement;

import model.adt.MyDictionary;
import model.adt.MyIList;
import model.expression.IExpression;
import model.program_state.ProgramState;
import model.value.IValue;

public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        MyIList<IValue> out = programState.getOut();
        out.add(expression.evaluate((MyDictionary<String, IValue>) programState.getSymTable(), programState.getHeap())); //error
        programState.setOut(out);
        return programState;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("print(%s)", expression.toString());
    }
}
