package BfsAlgo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyTestClientHandler implements ClientHandler { //Testing the infrastructure

	Solver solver;
	CacheManager cm;
	
	public MyTestClientHandler(Solver solver,CacheManager cm) {
		this.solver=solver;
	    this.cm=cm;
	}
	
	@Override
	public void handleClient(InputStream messageClient, OutputStream answerServer)  {
		BufferedReader userInput=new BufferedReader(new InputStreamReader(messageClient));
		PrintWriter answer= new PrintWriter(answerServer); 
		try {                                                  //We would like to read the input with ash
		  String line;
	    	while(!(line=userInput.readLine()).equals("end")){ //Reads one line from the input each time - up to the stop string
	    	   boolean isSave= cm.isSave(line);    //Check to see if we have a solution to this problem
	     	   if(isSave)                   //If there is, we will pull out the solution and send it to the customer in writing to Baffer
	    	   {
	      
                 answer.println(cm.getSolution(line));	
                 answer.flush();  //We will use this method in case the buffer is not complete and we want the solution to this line to reach the client now
	    	   }
	    	   else
	    		  cm.saveSolution(line,solver.solve(line)); 
	    	 //     PrintWriter answer= new PrintWriter(answerServer); 
                  answer.println(solver.solve(line));	
                  answer.flush();    //Otherwise, we'll send it for a solution, save it, and return an answer to the user
		    }
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		try {
			userInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		answer.close();
}
}


