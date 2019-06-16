package Interpreter;

import Commands.CommandFactory;
import Lexer.Lexer;
import Parser.Parser;
import ServerClient.MyServer;

public class MyInterpreter {
	CommandFactory cf;
	Thread interpreterThread;
	MyServer server;
	
	public MyInterpreter(CommandFactory CF, MyServer s) {
		this.cf = CF;
		this.server =s;
	}
	
	public void runScript(String text) {
		String lines[] = text.split("\\r?\\n");

		Lexer l = new Lexer();
		Parser p = new Parser(this.cf);
		for (String str : lines) {
			p.parse(l.processLine(str));	
		}
		
	}
	
	
	public void start(String text) {
		interpreterThread = new Thread(() -> {
			this.runScript(text);
		});
		
		interpreterThread.start();
	}
	
	public void stop() {
		server.stop();
	}
	
	
}
