import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * 
 * @author relgi
 *
 */
public class HotelTestClient {
	
	public static void main(String[] args) {
	      final int PORT = 1181;
	      try (Socket s = new Socket("localhost", PORT)) {
	    	  Scanner scanner = new Scanner(System.in);
		      InputStream instream = s.getInputStream();
		      OutputStream outstream = s.getOutputStream();
		
		      DataInputStream in = new DataInputStream(instream);
		      DataOutputStream out = new DataOutputStream(outstream);
		      
		      String response = in.readUTF(); //0
		      System.out.println("Receiving: " + response);
		      String list = "Commands:\n[1]User + name\n[2]Reserve + date(number) + date(number) \n[3]Cancel\n[4]Avail\n[5]Quit\nAnything else Disconnects\nCommand:",
		    		  command = "",
		    		  user = "";
		      boolean program = true;
		     
		      
		      System.out.println(list);
		      command = scanner.nextLine();
		      out.writeUTF(command); //1
		      user = scanner.nextLine();
		      out.writeUTF(user); //2
		      System.out.println(in.readUTF()); //3 
		      if(command.equalsIgnoreCase("user")!= true)
		      {
		    	  return;
		      }
		      
		      while(program)
		      {
		    	  System.out.println(list);
		    	  command = scanner.nextLine();
			      out.writeUTF("Receiving: " +command); //4
			      switch(checkingNum(command))
          		  {
          			case 1:
          			{
          				user = scanner.nextLine();
          				out.writeUTF(user); //U1
		                System.out.println("Receiving: " +in.readUTF()); //U2
          				break;
          			}
          			case 2:
          			{
          				int num1 = 0, //R1
          					num2 = 0; //R2
          				if(num2 > num1)
          				{
          					int temp = num2;
          					num2 = num1;
          					num1 = num2;
          				}
          				num1 = scanner.nextInt();//1
          				num2 = scanner.nextInt();//2
          				out.writeInt(num1);
          				out.writeInt(num2);
          				System.out.println("Reserving: "+num1+" to "+num2);	                				
          				System.out.println("Receiving: " +in.readUTF()); //R3
          				break;
          			}
          			case 3:
          			{
          				System.out.println("Receiving: " +in.readUTF()); //C1
          				break;
          			}
          			case 4:
          			{
          				System.out.println("Receiving: " +in.readUTF()); //A1
          				break;
          			}
          			case 5:
          			{
          				System.out.println("Receiving: " +in.readUTF()); //Q1
          				program = false;
          				break;
          			}
          			case 6:
          			{
          				System.out.println("Receiving: " +in.readUTF()); //Q1
          				program = false;
          				break;
          			}
          		}
		      }
	      } catch(IOException e) {
	    	  System.out.println(e.getStackTrace());
	      }
	}
	/**
	 * This stringCheck method just uses our String command to see if it is void or null
	 * @param command
	 * @return null - if null
	 * 			zero - if length is under zero
	 * 			command - if no problem.
	 */
	public static String stringCheck(String command)
	{
		if(command == null)
		{
			return "null";
		}
		if(command.length() <= 0)
		{
			return "zero";
		}
		return command;
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
