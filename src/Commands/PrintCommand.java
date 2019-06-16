package Commands;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ServerClient.MyServer;
import ServerClient.Server;

public class PrintCommand implements Command {

    private MyServer server;
    public CommandFactory cf;

    public PrintCommand(MyServer server2, CommandFactory cf) {
        this.server = server2;
        this.cf =cf;
    }


    @Override
    public void doCommand(String[] arguments) {
        // validate arguments
    	if (cf.StringSymbolTable.get(arguments[1]) ==null && cf.DoubleSymbolTable.containsKey(arguments[1])== false) {
    		System.out.println(arguments[1]);
    		return;
    	}
    	else if ( (cf.StringSymbolTable.get(arguments[1])!= null) && cf.PathTable.containsKey(cf.StringSymbolTable.get(arguments[1]))  ) {
    	System.out.println(cf.PathTable.get(cf.StringSymbolTable.get(arguments[1]))			);
    	return;
    	}
    	else if (cf.DoubleSymbolTable.containsKey(arguments[1]) ) {
    		System.out.println(cf.DoubleSymbolTable.get(arguments[1]));
    		return;
    	}
    	
    		System.out.println(arguments[1]);
    	
    }
}
