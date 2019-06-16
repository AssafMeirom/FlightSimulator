package Commands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ServerClient.MyServer;
import ServerClient.Server;

public class OpenServerCommand implements Command {

    private MyServer server;

    public OpenServerCommand(MyServer server2) {
        this.server = server2;
    }

    public MyServer getServer() {
        return server;
    }

    public void setServer(MyServer server) {
        this.server = server;
    }

    @Override
    public void doCommand(String[] arguments) {
    	//"openDataServer "+ (port+1)+" 10",
    	int Port = Integer.parseInt(arguments[1]);
    	int hz = Integer.parseInt(arguments[2]);
    	server.OpenServer(Port,hz);

    }
}
