package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Interpreter.ShuntingYard;
import Interpreter.VarNumber;
import ServerClient.Client;
import ServerClient.MyClient;

public class VarDeclarationCommand implements Command {
	
	public CommandFactory cf;
    protected Map<String, String> StringSymbolTable;
    private MyClient client;
    VarNumber putInDoublemap;

    
    public VarDeclarationCommand(MyClient client2, CommandFactory cf) {
        this.client = client2;
        this.cf =cf;
    }

    @Override
    public void doCommand(String[] arguments) {
    	//var breaks = bind "/controls/flight/speedbrake"
    	ShuntingYard sy;
    	sy = new ShuntingYard();
    	if (arguments.length ==2 ) //var x
    		cf.DoubleSymbolTable.put(arguments[1], null);
    	
    	
    //	else if((arguments[2].equals("="))&& arguments.length==4 )			//var y=x+3
    //	DoubleSymbol.put(arguments[1].trim(), sy.calc(arguments[arguments.length-1],cf).calculate() );
    	else if (arguments[2].equals("=")  && arguments.length==4   ) {    		
    	
    	cf.DoubleSymbolTable.put(arguments[1].trim(),cf.PathTable.get(cf.StringSymbolTable.get(arguments[(arguments.length-1)]))  );
    	}
    	
    	else if (arguments[3].equals("bind") )  	//"var y = bind simX",	
    		cf.StringSymbolTable.put(arguments[1].trim(), arguments[arguments.length-1]);

   
    	
    

	//"connect 127.0.0.1 "+port,
	//"var x",
	//"x = bind simX",
	//"var y = bind simX",	
	//"x = "+rand*2,
	//"disconnect",
	//"return y"
}}




