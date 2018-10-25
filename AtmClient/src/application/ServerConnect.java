package application;
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ServerConnect{
	PrintWriter writer;
	BufferedReader reader;
	



  public void connect(){

    try{
      Socket socket = new Socket("127.0.0.1",6000);
	    System.out.println("...");





	InputStreamReader input = new InputStreamReader(socket.getInputStream());
	 reader = new BufferedReader(input);
   writer = new PrintWriter(socket.getOutputStream(),true);

  String response;






    }
    catch(IOException ioe){
	System.out.println(ioe.toString());

    }
  }
  
  public void write(String message) {
	  writer.println(message);
  }
  
  public String read() throws IOException{
	  String response= reader.readLine();
	  return response;
	  
  }
  
}


