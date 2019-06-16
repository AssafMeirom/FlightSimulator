	package ServerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import Commands.CommandFactory;

public class MyServer  {
	public Map<String, Double> PathTable;
    private boolean stop = false;
    private Thread MyThread;
    private CommandFactory cf;
	private ServerSocket serverSocket = null;
	private Thread myThread = null;
	private Socket clientSock = null;
	private Socket connectSock = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	Object Lock = new Object();
	String[] DMnames;

	
	public MyServer(String[] dm,Map<String, Double> PathTable) {
		this.DMnames = dm;
		this.PathTable = PathTable;	
	}

	
	
	public void OpenServer(int port, int hz) {
		this.MyThread = new Thread(() -> {
		try {
			this.serverSocket = new ServerSocket(port);
		
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		while (!stop) {
		try {	
			System.out.println("here");
			clientSock = serverSocket.accept(); // blocking call
			
            synchronized (Lock) {
            	Lock.notifyAll(); 
            	}
            System.out.println("Simulator connected!");
            
			this.br = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
		         try {
						String line ;	
						 while ((line = br.readLine()) != null) {
					        	line = br.readLine();
					        	  // System.out.println("Received from simulator: " + line);
					               String[] ReadTheLines = line.split(",");
					               int size = ReadTheLines.length;
					               for(int i=0; i<size; i++) {
					            	
					            PathTable.put(DMnames[i].trim(), Double.parseDouble(ReadTheLines[i]));
					               }  	
						 }
		                System.out.println("Closing server");
		                serverSocket.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		}
		  }); 
	        MyThread.start();
		synchronized (Lock) {
				System.out.println("waiting for the connection");
				try {
					Lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("passed sync");
				}
		}

	}

	    public void stop() {
	        stop = true;
	    }

	 
	    public Thread SThread() {
	        return MyThread;
	    }


	    public Map<String, Double> getData() {
	    	
	        return cf.DoubleSymbolTable;
	    }

		
	
}
