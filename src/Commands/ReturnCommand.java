package Commands;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Interpreter.ShuntingYard;
import test.MyInterpreter;

public class ReturnCommand implements Command {
	public CommandFactory cf;
    protected Map<String, Double> DoubleSymbolTable;
	Double answer;

	public ReturnCommand( CommandFactory cf) {
		this.cf =cf;	
	}
	
	@Override
	public void doCommand(String[] arguments) {
		// "return y*x+z"
		// "return "+rand+" * 5 - (8+2)"
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ShuntingYard sy = new ShuntingYard();
		MyInterpreter.Value =sy.calc(arguments[1], cf).calculate();
		
	
		
		
		
		
		
		}

		
		
	
	

}
