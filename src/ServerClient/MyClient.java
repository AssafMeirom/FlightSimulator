package ServerClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Commands.CommandFactory;

public class MyClient {
	int port;
	String ip;
	MyServer server;
	CommandFactory cf;
	public Socket MySocket;
	PrintWriter SetClient;
	
	public Map<String, Double> PathTable;
	String[] DMnames;

	public MyClient() {
		// TODO Auto-generated constructor stub
	}
	
	public MyClient(String[] DMnames,ConcurrentHashMap<String, Double> pathTable2 ) {

		this.PathTable = pathTable2;
		this.DMnames = DMnames;
		for (String string : DMnames) {
			this.PathTable.put(string, 0.0);
		}
	}
	
    public void connect(String ip, int port) throws Exception {
            MySocket = new Socket(ip, port);
            
            SetClient = new PrintWriter(MySocket.getOutputStream());
            System.out.println("connect to client");

           
        
		}
    

	//set /controls/engines/engine/throttle 1
    public void setPathWithValue(String path, Double value) {

    	PathTable.put(path,value);
    	SetClient.println("set " + path + " " + value);
        System.out.println("sent to simulator:" + "set " + path + " " + value);
        SetClient.flush();
    }

    
	public void close() {
		try { 
			MySocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void disconnect() {
	    try {
            PrintWriter SetClient = new PrintWriter(MySocket.getOutputStream());
            SetClient.println("bye");
            System.out.println("bye bye bitch");
            SetClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
	}
	public Socket GetSocket() {
		return	this.MySocket;
	}
    
    //get position of plane from the simulator
	public Map<String, Double> getPath() {
		
		return this.PathTable;
	}
}
