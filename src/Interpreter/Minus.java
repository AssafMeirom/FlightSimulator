package Interpreter;

public class Minus extends BinaryExpression {

    public Minus(Expression right, Expression left) {
        super(right, left);
    }

    @Override
    public Double calculate() {
        return right.calculate() - left.calculate();
    }
}
