package Interpreter;

import Commands.CommandFactory;

public class VarNumber implements Expression {
	CommandFactory cf;
	String str;
	Expression e;
	
	public VarNumber(String str, CommandFactory cf) {
		this.cf = cf;
		this.str =str;
		
	}

	@Override
	public Double calculate() {
		
		if (cf.StringSymbolTable.containsKey(str)) {
			return (cf.PathTable.get(cf.StringSymbolTable.get(str)));
		}
		
		 return (this.cf.DoubleSymbolTable.get(str));

	}

}
