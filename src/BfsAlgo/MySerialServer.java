package BfsAlgo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server{ //Server mechanism
 
   private int port;             //
   private volatile boolean stop;
   
	public MySerialServer(int port) {
	 this.port = port;
	 this.stop = false;
}

	@Override
	public void open(ClientHandler ch) throws Exception  {  //
		ServerSocket server=new ServerSocket(port); //open socket
		server.setSoTimeout(1000); //Set Time Out
		int i=0;
		
//		while (i!=5)                              //To handle multiple clients - as long as the server is not stopped
		while(!stop){
			try
			{
				Socket aClient=server.accept();     //Blocked reading - waiting for a customer to connect
			
			    try {
			    	i++;
				    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
			       //
				    aClient.close();
			        
			    }
			    catch (IOException e) {
					System.out.println("io exception");
				}
	
			}
			 catch(SocketTimeoutException e)
		    {
		    	System.out.println("time out");
		    }
		}
		server.close();
	}
	 
	
	public void start(ClientHandler ch)  {  //A method that will enable us to use the method that the client listens and handles according to the parameter received
	 	new Thread(()->{                             // She would actually use a separate thurd so that it would be possible to get to the stop method sometime
			try {
				open(ch);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	

	@Override
	public void stop() { 
		stop=true;
	}

}
