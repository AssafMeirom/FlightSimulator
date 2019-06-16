package BfsAlgo;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.naming.directory.InvalidAttributeIdentifierException;

public class MyClientHandler implements ClientHandler{

	private Solver solve;
	CacheManager cm;
	
	public MyClientHandler(Solver solve,CacheManager cm) {
		this.solve=solve;
		this.cm=cm;
	}
	

	@Override
	public void handleClient(InputStream messageClient, OutputStream answerServer) {
		BufferedReader userInput=new BufferedReader(new InputStreamReader(messageClient));
		PrintWriter answer= new PrintWriter(answerServer); 
    	  String line;
    	  //Double[][] matrix = null;
    	  State[][] matrixS2= null;
    	  Point initialP=null;
    	  Point goalP=null;
    	  int j=0;
    	  ArrayList<String> clientP=new ArrayList<>();
    	  int row=0;
    	  int col=0;
    	  int k=0;
	    	try {
				while(!(line=userInput.readLine()).equals("end")){
			         row++;
			         String[] split=line.split(",");
			         for (String s: split)
		         	 {
				        clientP.add(s);
		             }
				}
				   col=clientP.size()/row;
				   matrixS2= new State[row][col];
					
				   for(int i=0; i<row; i++ ){
							for(j=0; j<col; j++)	{
							   matrixS2[i][j]=new State(new Point(i, j));	
							   matrixS2[i][j].setCost(Double.parseDouble(clientP.get(k)));
							   k++;
					
					}
				  }
					
			 		String[] startP;
			 		String[] endP;
					
						startP = userInput.readLine().split(",");
					    initialP=new Point(new Integer(startP[0]),new Integer(startP[1]));
					 
						endP = userInput.readLine().split(",");
						goalP =new Point(new Integer(endP[0]),new Integer(endP[1]));
					
					
			
					 
				    
					MatGame clientMat= new MatGame(matrixS2, initialP, goalP);
					
					boolean isSave= cm.isSave(clientMat);
					
					
					
					if(isSave)
					{
						String sol= (String) cm.getSolution(clientMat);
						 
			            answer.println(sol);
			           
			        
					}
					 
					
					LinkedList<State> backTrace=(LinkedList<State>) solve.solve(clientMat);
					StringBuilder stringSol= new StringBuilder();
					int x=initialP.x;
					int y=initialP.y;
					for (int i=1;i< backTrace.size() ;i++)
					{
						if (backTrace.get(i).getState().getX()==x+1 && backTrace.get(i).getState().getY()==y)
						{
							stringSol.append("Down,");
							x=x+1;
						}
						if (backTrace.get(i).getState().getX()==x-1 && backTrace.get(i).getState().getY()==y)
						{
							stringSol.append("up,");
							x=x-1;
						}
						if (backTrace.get(i).getState().getX()==x && backTrace.get(i).getState().getY()==y+1)
						{
							stringSol.append("Right,");
							y=y+1;
						}
						if (backTrace.get(i).getState().getX()==x && backTrace.get(i).getState().getY()==y-1)
						{
							stringSol.append("Left,");
							y=y-1;
						}
					}
					 stringSol.deleteCharAt(stringSol.length()-1).toString();	
					 cm.saveSolution(clientMat,stringSol);
					 
		             answer.println(stringSol);
		             
			        
	    	}
				
				 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			                       }
		     
	    	 answer.close();
	    	 try {
				userInput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         

             
	}
}

