import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * This HotelUserClient connects to the HotelServer to relay information back and forth for the client to use and interact with
 * to make their interaction with the hotel class. Overall, this is the computer the client uses to connect to the server to
 * interact with the internet.
 *		   @author Yun Ki (Ted) Jang 100258884
 *         @version 1.0
 *         @since 2019-11-29
 *
 */
public class HotelUserClient 
{
	/**
	 * This main method is what runs our client and will continuously run using a Loop and Try command.
	 * After starting our client with port 1181 we have 6 options for the server:
	 * -User change : By Changing our user String variable   +String Variable
	 * -Reserve date : By Using hotel class		+Int Variable + Int Variable
	 * -Cancel reservation : By Using Hotel Class
	 * -Avaliable List time for Hotel : By Using Hotel Class
	 * -Quit the connection to Server : By closing Socket
	 * 
	 * Each parameter will be asked by the class to the user to fill in.
	 * @param args
	 */
	public static void main(String[]args)
	{
		 final int PORT = 1181;
	      try (Socket s = new Socket("localhost", PORT)) 
	      {
	    	   
	    	  InputStream instream = s.getInputStream();
		      OutputStream outstream = s.getOutputStream();
		
		      DataInputStream in = new DataInputStream(instream);
		      DataOutputStream out = new DataOutputStream(outstream);
		      
		      String response = in.readUTF();
		      System.out.println("Receiving: " + response);

		      out.writeUTF("USER"); //1
		      out.writeUTF("Jill"); //2
		      response = in.readUTF(); //3
		      System.out.println("Receiving: " + response);
		      
   		  out.writeUTF("RESERVE"); //4
   		  out.writeInt(3); //R1
   		  out.writeInt(5); //R2
   		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF()); //R3
		      
   		  out.writeUTF("RESERVE"); //4
   		  out.writeInt(10); //R1
   		  out.writeInt(12); //R2
   		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF()); //R3 	  

   		  out.writeUTF("USER"); //4
   		  out.writeUTF("Jack"); //U1
   		  out.flush();  
	    	  System.out.println("\nReceiving: " + in.readUTF());	//U2
	    	  
   		  out.writeUTF("CANCEL"); //4
   		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	//C1
	    	  
   		  out.writeUTF("RESERVE"); //4
   		  out.writeInt(10); //R1
   		  out.writeInt(12); //R2
   		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF());	//R3
	    	  
   		  out.writeUTF("CANCEL"); //4
   		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	//C1
	    	  	    	  
	    	  
   		  out.writeUTF("AVAIL");	//4
   		  out.flush();		    	  
	    	  System.out.println("\nReceiving: " + in.readUTF());	//A1
	    	  
   		  out.writeUTF("QUIT");	//4
   		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	//Q1

		      
	      } catch(IOException e) {
	    	  System.out.println(e.getStackTrace());
	      }
	}
}
