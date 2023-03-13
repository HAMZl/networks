import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;
public class Server 
{
	public void runServer() {
		File file = new File("log.txt");
		String[] clientArguments = null;
        try
        {
            // we use port 5555.
            ServerSocket serverSock = new ServerSocket(6001);
			file.createNewFile();

            while( true )
            {
                // Accept; blocking; will not return until a client has made contact.
                Socket sock = serverSock.accept();

                // writing to log file
                InetAddress inet = sock.getInetAddress();
                LocalDate localDate = LocalDate.now(); // current date
				LocalTime localTime = LocalTime.now(); // current time
				FileWriter fileWriter = new FileWriter("log.txt", true); // open file to append
				String content = localDate + "|" + localTime + "|" + inet.getHostAddress() + "\n";
				fileWriter.write(content); // write to file
				fileWriter.close(); // close file

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