package Interpreter;

import Commands.CommandFactory;

public class Multiply extends BinaryExpression {

    public Multiply(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Double calculate() {
        return right.calculate() * left.calculate();
    }
}
