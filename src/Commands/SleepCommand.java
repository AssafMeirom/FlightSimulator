package Commands;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import Interpreter.ShuntingYard;
import test.MyInterpreter;

public class SleepCommand implements Command {
	public CommandFactory cf;
    protected Map<String, Double> DoubleSymbolTable;

	public SleepCommand( CommandFactory cf) {
		this.cf =cf;	
	}
	
	@Override
	public void doCommand(String[] arguments) {
		String newArgument = arguments[1].trim();
		
		try {
			Thread.currentThread().sleep(  Long.parseLong(newArgument)    );
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		}
}
