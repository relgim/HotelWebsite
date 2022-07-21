import java.io.*;
import java.net.*;

public class Server 
{
	public static void main(String []args) throws IOException
	{
		ServerSocket ss = new ServerSocket(9999);
		Socket s = ss.accept();
		System.out.println("");
	}
}
