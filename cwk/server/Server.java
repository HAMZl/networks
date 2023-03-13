import java.net.*;
import java.io.*;
import java.util.*;

public class Server 
{
	public void runServer() {
		File file = new File("log.txt");
		String[] clientArguments = null;
        try
        {
            // we use port 5555.
            ServerSocket serverSock = new ServerSocket(6001);

            while( true )
            {
                // Accept; blocking; will not return until a client has made contact.
                Socket sock = serverSock.accept();

                // Get information about the connection and the date/time, and print to screen. Normally you would not print
                // this information - it would e.g. be sent to a log file - this is just for demonstration purposes.
                InetAddress inet = sock.getInetAddress();
                Date date = new Date();
                System.out.println("\nDate " + date.toString() );
                System.out.println("Connection made from " + inet.getHostName());

                // Send a single line of text to the client.
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                writer.println("Connection has been made.");    		 // Write to client
                writer.close();
                sock.close();
            }
        }
        catch (IOException ex) {
            System.out.println( ex );
        }
    }
	public static void main( String[] args )
	{
		Server server = new Server();
        server.runServer();
	}
}