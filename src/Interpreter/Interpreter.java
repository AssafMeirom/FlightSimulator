package Interpreter;

public interface Interpreter {

    Double calculateExpression(String[] expression);

    Double evaluatePostfix(String[] exp);
}
