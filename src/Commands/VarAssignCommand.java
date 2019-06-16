package Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Interpreter.ShuntingYard;
import ServerClient.Client;
import ServerClient.MyClient;

public class VarAssignCommand  implements Command {
	public CommandFactory cf;
    private MyClient client;
    ShuntingYard sy;
    
    public VarAssignCommand(MyClient client, CommandFactory cf) {
    	this.cf =cf;
        this.client = client;
        sy = new ShuntingYard();
    }
    
	//set /controls/engines/engine/throttle 1

//	 breaks = 8	

    @Override
    public void doCommand(String[] arguments) {
//				"x = bind simX",
    	//"var h0 = heading",

    	//if (arguments[2].equals("bind") ) {
    	//	cf.StringSymbolTable.put(arguments[0].trim(), arguments[arguments.length-1]);
    		
    	//}
    	//else {
    		if (!cf.StringSymbolTable.containsKey(arguments[0])) {
    			cf.DoubleSymbolTable.put(arguments[0], sy.calc(arguments[arguments.length], cf).calculate());
    		}
    		else {
        	cf.PathTable.put(cf.StringSymbolTable.get(arguments[0]),sy.calc(arguments[arguments.length-1], cf).calculate() ); //x = 10
        	if(client.GetSocket()!= null) {
        		client.setPathWithValue(cf.StringSymbolTable.get(arguments[0]), sy.calc(arguments[arguments.length-1], cf).calculate());
        	}
    		}		
    	//}
    	
    	
    }
    
    
    
}
