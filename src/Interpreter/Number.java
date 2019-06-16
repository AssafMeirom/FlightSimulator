package Interpreter;

public class Number implements Expression {

    private Double value;

    public Number(Double value) {
        this.value = value;
    }

    public Number() {
        this.value = (double) 0;
    }

    @Override
    public Double calculate() {
        return value;
    }
}
