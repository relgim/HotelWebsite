import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;

public class HotelClient 
{
	public static void main(String[] args) 
	{
		final int PORT = 1111;
		Scanner scanner = new Scanner(System.in);
		
		try
		{
			Socket s = new Socket("localhost", PORT);
			String user = "quit100.23.42",
					command = "";
			boolean running = true;
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println(in.readUTF()); //1

			System.out.print("Type Command: ");
			command = scanner.nextLine();
			
			if(!command.equalsIgnoreCase("user"))
			{
				System.out.println("Invalid Command: Proccess Terminated");
				running = false;
			}
			while(user == "quit100.23.42" && running)
			{
				System.out.print("Type a valid name: ");
				user = scanner.nextLine();
				checkUser(user);
			}
			out.writeUTF(user); //2
			running = in.readBoolean(); //3
			
			System.out.println(in.readUTF()); //4
			
			
			
			while(running) //switch case to match server
			{
				System.out.println("Input Command: (Options) ");
				user = scanner.nextLine();			
				out.writeUTF(user); // 5
				running = in.readBoolean(); //6
				System.out.println(in.readUTF()); //7
				out.flush();
			}
			
			System.out.println("nope ");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
    public static String checkUser(String user)
    {
    	if(user == null)
    	{
    		return "quit100.23.42";
    	}
    	if(user.length() <= 0 )
    	{
    		return "quit100.23.42";
    	}
    	return user;
    }
}
/**
{
	public static void main (String[]args) throws IOException
	{
		String command;
		Socket s = new Socket("127.0.0.1",1111);
		Scanner scan = new Scanner(s.getInputStream());
		System.out.println("Enter your username: ");
		command = scan.next();
		System.out.println("Sent.");
		
		PrintStream p = new PrintStream(s.getOutputStream());
		p.println(command);

		System.out.println(command);
		public static void main(String[] args) 
		{
			final int PORT = 1181;
			Scanner userIn = new Scanner(System.in);
			try (Socket s = new Socket("localhost", PORT)){
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				String text="";
				while(!text.equalsIgnoreCase("quit")) {
					text = in.readUTF();
					System.out.println(text);
					System.out.print("Say something: ");
					text = userIn.nextLine();
					out.writeUTF(text);
					out.flush();
				}
			} catch (IOException e) {e.printStackTrace();}
	}
		
}
**/
