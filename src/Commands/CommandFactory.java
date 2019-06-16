package Commands;

import ServerClient.Client;
import ServerClient.MyClient;
import ServerClient.MyServer;
import ServerClient.Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;


public class CommandFactory {


    public static final String OPEN_SERVER_COMMAND = "openDataServer";
    public static final String CONNECT_COMMAND = "connect";
    public static final String VARIABLE_ALLOCATION_COMMAND = "var";
    public static final String ASSIGNMENT_COMMAND = "=";
    public static final String WHILE_COMMAND = "while";
    public static final String PRINT_COMMAND = "print";
    public static final String RETURN_COMMAND = "return";
    public static final String DISCONNECT_COMMAND = "disconnect";
    public static final String SLEEP_COMMAND = "sleep";
    private MyServer server;
    private MyClient client;
    public ConcurrentHashMap<String, String> StringSymbolTable;
    public ConcurrentHashMap<String, Double> DoubleSymbolTable;
    private ConcurrentHashMap<String, Supplier<Command>> commandsSupplierMap;
    public ConcurrentHashMap<String, Double> PathTable;

    public CommandFactory(MyServer myServer, MyClient client, ConcurrentHashMap<String, String> symbolTable
    		, ConcurrentHashMap<String, Double> DoublesymbolTable , ConcurrentHashMap<String, Double> PathTable)
    		 {
        this.server = myServer;
        this.client = client;
        this.StringSymbolTable = symbolTable;
        this.DoubleSymbolTable = DoublesymbolTable;
        this.PathTable = PathTable;
        this.commandsSupplierMap = new ConcurrentHashMap<>();

        commandsSupplierMap.put(OPEN_SERVER_COMMAND, () -> new OpenServerCommand(this.server));
        commandsSupplierMap.put(CONNECT_COMMAND, () -> new ConnectToClientCommand(this.client));
        commandsSupplierMap.put(VARIABLE_ALLOCATION_COMMAND, () -> new VarDeclarationCommand(this.client, this));
        commandsSupplierMap.put(ASSIGNMENT_COMMAND, () -> new VarAssignCommand(this.client,this));
        commandsSupplierMap.put(WHILE_COMMAND, () -> new LoopCommand(this.server, this));
        commandsSupplierMap.put(PRINT_COMMAND, () -> new PrintCommand(this.server, this));
        commandsSupplierMap.put(RETURN_COMMAND, () -> new ReturnCommand(this));
        commandsSupplierMap.put(DISCONNECT_COMMAND, () -> new DisCommand(this.client));
        commandsSupplierMap.put(SLEEP_COMMAND, () -> new SleepCommand(this)    );
    }

    public Command getCommand(String commandKey) {

        Supplier<Command> commandSupplier = commandsSupplierMap.get(commandKey.trim());
        
        
        if (commandSupplier != null) {
            return commandSupplier.get();
        }
        else if (commandKey.length() > 4	) {	
        String whileChecker = commandKey.substring(0, 5);
        if (whileChecker.equals("while")) {
            return commandsSupplierMap.get(WHILE_COMMAND).get();
        }
        }
        else {
            // we expect that if it's not any of the commands in the map then it's an assignment command
            return commandsSupplierMap.get(ASSIGNMENT_COMMAND).get();
        }
        return commandsSupplierMap.get(ASSIGNMENT_COMMAND).get();


    }


}
