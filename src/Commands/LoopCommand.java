package Commands;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


import Lexer.Lexer;
import Parser.Parser;
import Interpreter.ShuntingYard;
import ServerClient.MyServer;
import ServerClient.Server;

public class LoopCommand implements Command {


    private CommandFactory commandFactory;
    ShuntingYard sy;
    private MyServer server;


    public LoopCommand(MyServer server2, CommandFactory commandFactory) {
        this.server = server2;
        this.commandFactory = commandFactory;
        sy = new ShuntingYard();
    }


    @Override
    public void doCommand(String[] arguments) {
    	 if (arguments.length<4) {
         	return;
         }
    	ShuntingYard sy = new ShuntingYard();
    	String TheCondition= arguments[0];
    	TheCondition.split(" ");
        String variable = TheCondition.substring(6, 9);
        String operator = TheCondition.substring(10, 11);
        String value = TheCondition.substring(12, (TheCondition.length()-3));
       
        String[] NewArgument = Arrays.copyOfRange(arguments, 1, (arguments.length-1));
        int  i =0;
    	int j = 0;
        while (condition(variable, operator, value)) {
 
        	
    		Lexer l = new Lexer();
    		Parser p = new Parser(this.commandFactory);
    		for (String str : NewArgument) {
    			p.parse(l.processLine(str));	
    		
    		}
    			
    			
    		
    		}
			/*
			 * else if (NewArgument[i].trim().equals("sleep") ||
			 * NewArgument[j].equals("sleep")){ String[] goSleep =
			 * Arrays.copyOfRange(NewArgument, i, NewArgument.length); Command command =
			 * commandFactory.getCommand(NewArgument[i]); command.doCommand(goSleep); if
			 * ((NewArgument.length-1) == i+1 ) { i =2; j=0; } } else if
			 * (NewArgument[i].trim().equals("print")) { String[] goPrint =
			 * Arrays.copyOfRange(NewArgument, i, NewArgument.length); Command command =
			 * commandFactory.getCommand(NewArgument[i]); command.doCommand(goPrint); }
			 */
        	
        	
		/*
		 * commandFactory.PathTable.put(commandFactory.StringSymbolTable.get(NewArgument
		 * [j].trim()), sy.calc(NewArgument[i],commandFactory).calculate() ); i = i+3; j
		 * = j+3;
		 */
       }
    

	private boolean condition(String variable, String operator, String value) {

        switch (operator) {
            case "<":
                return sy.calc(variable,commandFactory).calculate() < sy.calc(value, commandFactory).calculate();
            case ">":
                 return sy.calc(variable,commandFactory).calculate() > sy.calc(value, commandFactory).calculate();
            case "<=":
                 return sy.calc(variable,commandFactory).calculate() <= sy.calc(value, commandFactory).calculate();
            case ">=":
                return sy.calc(variable,commandFactory).calculate() >= sy.calc(value, commandFactory).calculate();
            case "==":
                return sy.calc(variable,commandFactory).calculate() == sy.calc(value, commandFactory).calculate();
            case "!=":
                 return sy.calc(variable,commandFactory).calculate() != sy.calc(value, commandFactory).calculate();
            default:
                return false;
        }

    }
    

}