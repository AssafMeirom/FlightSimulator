package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Commands.CommandFactory;
import Lexer.Lexer;
import Parser.Parser;
import ServerClient.MyClient;
import ServerClient.MyServer;
import ServerClient.Server;

public class MyInterpreter {

	public static Double Value;
	
	public static  int interpret(String[] lines){
		
	   
		ConcurrentHashMap<String,Double> DoublesymbolTable = new ConcurrentHashMap<>(); // x = 5
		ConcurrentHashMap<String, String> SymbolTable = new ConcurrentHashMap<>(); // x -> path x
		ConcurrentHashMap<String, Double> PathTable = new ConcurrentHashMap<>(); //path simx -> value
		//PathTable.put("simX", (double)0);
		//PathTable.put("simY", (double)0);
		//PathTable.put("simZ", (double)0);
		
		String[] DMnames= new String[] {
				"instrumentation/airspeed-indicator/indicated-speed-kt",
				"instrumentation/altimeter/indicated-altitude-ft",
				"instrumentation/altimeter/pressure-alt-ft",
				"instrumentation/attitude-indicator/indicated-pitch-deg",
				"instrumentation/attitude-indicator/indicated-roll-deg",
				"instrumentation/attitude-indicator/internal-pitch-deg",
				"instrumentation/attitude-indicator/internal-roll-deg",
				"instrumentation/encoder/indicated-altitude-ft",
				"instrumentation/encoder/pressure-alt-ft",
				"instrumentation/gps/indicated-altitude-ft",
				"instrumentation/gps/indicated-ground-speed-kt",
				"instrumentation/gps/indicated-vertical-speed",
				"instrumentation/heading-indicator/indicated-heading-deg",
				"instrumentation/magnetic-compass/indicated-heading-deg",
				"instrumentation/slip-skid-ball/indicated-slip-skid",
				"instrumentation/turn-indicator/indicated-turn-rate",
				"instrumentation/vertical-speed-indicator/indicated-speed-fpm",
				"controls/flight/aileron",
				"controls/flight/elevator",
				"controls/flight/rudder",
				"controls/flight/flaps",
				"controls/engines/current-engine/throttle",
				"engines/engine/rpm"
		};
		
		MyServer MyServer = new MyServer(DMnames,PathTable); //original (DMnames)
		MyClient client = new MyClient(DMnames, PathTable);
		CommandFactory ch = new CommandFactory(MyServer,client,SymbolTable,DoublesymbolTable,PathTable );
		Lexer l = new Lexer();
		Parser p = new Parser(ch);
		for (String str : lines) {
			p.parse(l.processLine(str));	
		}
		MyServer.stop();
		return Value.intValue();
		
		
	}
}
