package BfsAlgo;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {  //The call protocol - The purpose of the interface is to
	                              // determine the type of call and handling the customer
  public void handleClient(InputStream messageClient, OutputStream answerServer); //Receives input from which you can read the client's messages and voip-pot to which we will write the server reply	 
}
