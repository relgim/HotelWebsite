import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/**
 *This HotelServer connects to Port 1181 and relays the information through the socket with 6 basic commands:
 *
 *		   @author Yun Ki (Ted) Jang 100258884
 *         @version 1.0
 *         @since 2019-11-29
 *
 */
public class HotelServer 
{
	/**
	 * This main method is what runs our server and will continuously run using a Loop and Try command.
	 * After starting our server with port 1181 we have 6 options for the client:
	 * -User change : By Changing our user String variable
	 * -Reserve date : By Using hotel class
	 * -Cancel reservation : By Using Hotel Class
	 * -Avaliable List time for Hotel : By Using Hotel Class
	 * -Quit the connection to Server : By closing Socket
	 * @param args
	 */
    public static void main(String[] args)   
    {
        final int PORT = 1181;
    	try 
    	{
    		ServerSocket server = new ServerSocket(PORT);
    		System.out.println("Waiting for client to connect . . . ");
    		Hotel hotel = new Hotel();
    		while (true) 
    		{
    			try 
    			{
    				Socket s = server.accept();
    				String user = "",
    						command = "";
    				boolean shutDown = false,
    						program = true;
    				
	    			System.out.println("Client connected.");
	    			
	                DataInputStream in = new DataInputStream(s.getInputStream());
	                DataOutputStream out = new DataOutputStream(s.getOutputStream());
	                
	                out.writeUTF("Client Connected"); //0
	                
	                command = in.readUTF(); //1
	                if(checkingNum(command) != 1)
	                {
	                	System.out.println("Command: " + command);
	                	command = in.readUTF();
	                	System.out.println("User: "+command);
	                	out.writeUTF("Invalid command: Closing Connection"); //1
	                	out.close();
	                	in.close();
	                	s.close();
	                }
	                user = in.readUTF();  //2 
	                System.out.println("User: "+user);

	                out.writeUTF("Hello, "+user); //3
	                
	                program = true;
	                

	                while(program)
	                {
	                	command = in.readUTF(); //4
	                	
	                	if(!checking(command))
	                	{
	                		switch(checkingNum(command))
	                		{
	                			case 1:
	                			{
	                				user = in.readUTF(); //U1
	    		                	out.writeUTF("Hello, "+user); //U2
	    		                	System.out.println("Client Connected");
	                				break;
	                			}
	                			case 2:
	                			{
	                				int num1 = in.readInt(), //R1
	                					num2 = in.readInt(); //R2
	                				System.out.println("Reserving: "+num1+" to "+num2);	                				
	                				if(hotel.requestReservation(user,num1,num2))
	            					{
	            						out.writeUTF("Reservation Made: "+user+" from "+num1+" to "+num2); //R3
	            					}
	            					else
	            					{
	            						out.writeUTF("Reservation unsuccessful: "+user+" from "+num1+" to "+num2); //R3
	            					}
	                				break;
	                			}
	                			case 3:
	                			{
	                				System.out.println("Making Reservation.");
	                				if(hotel.cancelReservation(user))
	            					{
	                					out.writeUTF("Reservation Canceled: "+user); //C1
	                					System.out.println("Made Reservation");
	            					}
	            					else
	            					{
	            						out.writeUTF("Reservation not canceled for "+user+", no current reservation"); //C1
	            						System.out.println("Did not make Reservation");
	            					}
	                				break;
	                			}
	                			case 4:
	                			{
	                				out.writeUTF(hotel.toString()); //A1
	                				System.out.println("Print Hotel List");
	                				break;
	                			}
	                			case 5:
	                			{
	                				out.writeUTF("Closing Connection"); //Q1
	                				System.out.println("Closing Client Connection");
	                				out.flush();
	                				out.close();
	        	                	in.close();
	        	                	s.close();
	        	                	program = false;
	                				break;
	                			}
	                			case 6:
	                			{
	                				out.writeUTF("Invalid command: Closing Connection");
	                				System.out.println("Closing Client Connection");
	                				out.flush();
	                				program = false;
	                				break;
	                			}
	                		}
	                	}
	                	else
	                	{
	                		System.out.println("Invalid command: Closing Connection2");
	                		out.flush();
            				out.close();
    	                	in.close();
    	                	s.close();
		                	program = false;
	                	}
	                	out.flush();
	                	
	                }
	               
    			} 
    			catch (IOException e) 
    			{
    				e.printStackTrace();
    			}
    		}   
    	} 
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
	}
    /**
     * This checking method, checks if the option is valid and returns a false valid if it is valid
     * using our String variable check, but true if it isn't valid.
     * 
     * @param check
     * @return false - If valid commands
     * 			true - If non-valid commands
     */
    public static boolean checking(String check)
    {
    	//System.out.println(check.equalsIgnoreCase("quit"));
    	
    	if(check.equalsIgnoreCase("quit"))
    	{
    		return false;
    	}
    	if(check.equalsIgnoreCase("cancel"))
    	{
    		return false;
    	}
    	if(check.equalsIgnoreCase("avail"))
    	{
    		return false;
    	}
    	if(check.equalsIgnoreCase("reserve"))
    	{
    		return false;
    	}
    	if(check.equalsIgnoreCase("user")) // checking length should cause error
        {
    		return false;
        }
    	return true;
    }
    /**
     * This checkingNum method returns the int Variable with the corrisponding command using our String variable check.
     * @param check
     * @return 1 - User
     * 			2 - Reserve
     * 			3 - Cancel
     * 			4 - Avail
     * 			5 - Quit
     * 			6- Anything else
     */
    public static int checkingNum(String check)
    {
    	if(check.equalsIgnoreCase("quit"))
    	{
    		return 5;
    	}
    	if(check.equalsIgnoreCase("cancel"))
    	{
    		return 3;
    	}
    	if(check.equalsIgnoreCase("avail"))
    	{
    		return 4;
    	}
    	if(check.equalsIgnoreCase("reserve"))
    	{
    		return 2;
    	}
    	if(check.equalsIgnoreCase("user")) // checking length should cause error
        {
    		return 1;
        }
    	return 6;
    }
}
