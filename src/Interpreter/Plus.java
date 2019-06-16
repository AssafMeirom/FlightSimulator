package Interpreter;

import Commands.CommandFactory;

public class Plus extends BinaryExpression {
	
    public Plus(Expression right, Expression left) {
        super(right, left);
        
    }

    @Override
    public Double calculate() {

        return right.calculate() + left.calculate();
    }
}
