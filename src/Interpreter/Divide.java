package Interpreter;

public class Divide extends BinaryExpression {

    public Divide(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Double calculate() {
        return right.calculate() / left.calculate();
    }
}
