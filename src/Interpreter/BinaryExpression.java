package Interpreter;

public abstract class BinaryExpression implements Expression {

    Expression right;
    Expression left;

    public BinaryExpression(Expression right, Expression left) {
        this.right = right;
        this.left = left;
    }

}
