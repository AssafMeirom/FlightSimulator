package Parser;


import Commands.Command;
import Commands.CommandFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parser {

    private CommandFactory commandFactory;
    private String reservedSymbols = "{}()<>=+-*/";

    public Parser(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void parse(String[] line) {
    	String[] str2 = Arrays.stream(line).
				filter(value -> !value.isEmpty())
				.filter((string)->!string.equals(" "))
				.toArray(String[]::new);
    	Command command = commandFactory.getCommand(str2[0]);
        command.doCommand(str2);
    }


}
