package Commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ServerClient.Client;
import ServerClient.MyClient;

public class DisCommand implements Command{
	CommandFactory cf;
	MyClient client;
	
	public DisCommand(MyClient client2) {
	this.client =client2;
	}
	
	
	@Override
	public void doCommand(String[] arguments) {
		client.disconnect();
	}


}
