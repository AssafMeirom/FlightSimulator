package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

import Commands.CommandFactory;
import Interpreter.MyInterpreter;
import Lexer.Lexer;
import Parser.Parser;
import ServerClient.Client;
import ServerClient.MyClient;
import ServerClient.MyServer;
import BfsAlgo.BFS;
import BfsAlgo.FileCacheManager;
import BfsAlgo.MyClientHandler;
import BfsAlgo.MySerialServer;
import BfsAlgo.SearcherAdapter;


public class Model extends Observable {
	MySerialServer serverSearch;
	MyClient  client;
    private Thread MyThread;
	private String solutionPath;
	CommandFactory cf;
	MyServer myServer;
	PrintWriter pw;
	BufferedReader br;
	public double sPlaneX, sPlaneY;
	MyInterpreter interpreter;
	Object Lock = new Object();
	Thread mileStoneThread;
    
	 public Model(MyClient mclient, CommandFactory CommandFactory) throws IOException {
		this.client =mclient;
		this.cf = CommandFactory;
	
//		interpreter = null;
		pw = null;
		br = null;
		
		MyThread = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {}
		
				if(client != null)
				{
					try {				
						if (client.MySocket==null) {
							System.out.println("here");
							try {
								Thread.sleep(20000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						client.MySocket.getInputStream().available();
					
						sPlaneX = client.getPath().get("sim/current-view/viewer-x-m");
						sPlaneY = client.getPath().get("sim/current-view/viewer-y-m");
						System.out.println(sPlaneX);
						System.out.println(sPlaneY);
						setChanged();
						notifyObservers("connectToServer_success");
					} catch (IOException e) {
						
					
					}
					
				}
		
			}
		});
		MyThread.start();

		
		
		
	}

	public String getSolution() {
		return this.solutionPath;
	}
	
	
	public void setAileron(double aileron) {//setting the aileron (sending to client set method)
		
		if (client!= null) {
			client.setPathWithValue("/controls/flight/elevator", aileron);
		}
		
	}

	public void setElevator(double elevator) {//setting the elevator (sending to client set method)
		if (client!= null) {
			
			client.setPathWithValue("/controls/flight/aileron",elevator );
		}
		
	}
	public void setRudder(double rudder) { //setting the rudder (sending to client set method)
			client.setPathWithValue("/controls/flight/rudder", rudder);
		
		
	}
	public void setThrottle(double throttle) { //setting the throttle (sending to client set method)
		if (client!= null) {
			client.setPathWithValue("/controls/engines/current-engine/throttle", throttle);

		}
	
	}
	
	
	//connect to client via ip and port
	public void ConnectToServer(String ip, int port) { 
		try {
			client.connect(ip, port);
			setChanged();
			notifyObservers("Success Connect To Server");
		}catch (Exception e) {
			setChanged();
			notifyObservers("Failed Connect To Server");
		}
		
	}


	public void runScript(String text) {
		interpreter = new MyInterpreter(cf, myServer);
		interpreter.start(text);
		
	}
	
	public void connectToMap(String ip, int port, Integer[][] theMat, int startX, int startY, double desX, double desY) {
		
		mileStoneThread = new Thread(() -> {
			serverSearch= new MySerialServer(5200); // initialize
			serverSearch.start(new MyClientHandler(new SearcherAdapter(new BFS()),new FileCacheManager<>()));

			Socket sock=null;
			PrintWriter out=null;
			BufferedReader in=null;
			try{
				sock=new Socket("127.0.0.1",5200);
				out=new PrintWriter(sock.getOutputStream());
				in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
				int i,j;

				Integer[][] matrix=theMat;
			
				Integer x= (int) desX;
				Integer y= (int) desY;
				System.out.println(x+" "+y);
				
				
				for(i=0;i<matrix.length;i++){
					System.out.print("\t");
					for(j=0;j<matrix[i].length-1;j++){
						out.print(matrix[i][j]+",");
						System.out.print(matrix[i][j]+",");
					}
					out.println(matrix[i][j]);
					System.out.println(matrix[i][j]);
				}
				out.println("end");
				out.println("0,0");
				out.println(x+","+y);
				out.flush();
			
				System.out.println("\tproblem sent, waiting for solution...");
				System.out.println("here1");
				System.out.println(in.readLine());
				System.out.println("here2");
				solutionPath=in.readLine();
				
				System.out.println("\tsolution received");
			
				System.out.println("\t\tyour solution: "+solutionPath);
				
			}catch(SocketTimeoutException e){
				System.out.println("\tYour Server takes over 3 seconds to answer ");
			}catch(IOException e){
				System.out.println("\tYour Server ran into some IOException ");
			}finally{
				try {
					in.close();
					out.close();
					sock.close();
					serverSearch.stop();
				} catch (IOException e) {
					System.out.println("\tYour Server ran into some IOException ");
				}	
		}
			setChanged(); 
			notifyObservers("Calculation Done");
		});
		
		mileStoneThread.start();   
		

		
	}
	
}
