package BfsAlgo;

public interface Server {
 public void start(ClientHandler ch);
 public void open(ClientHandler ch) throws Exception; //Open the server and wait for customers
 public void  stop();        //close the server
}



