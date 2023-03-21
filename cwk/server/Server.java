import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.*;

public class Server {
	// initialize PORT AND POOL_SIZE
	private static final int PORT = 6001;
    private static final int POOL_SIZE = 30;
	// initialize logfile
	private File file = new File("log.txt");
	private ServerSocket serverSock;
	private Socket sock;
	private ExecutorService executor;
	// array of items available to bid
	private ArrayList<BidItem> bidItems = new ArrayList<BidItem>();
	public void runServer() {
        try
        {
            // initialize server socket using port 6001
            serverSock = new ServerSocket(PORT);
			// initialize executor to manage fixed thread-pool with 30 connections
			executor = Executors.newFixedThreadPool(POOL_SIZE);
			// create new log file if it does not exist
			file.createNewFile();

            while( true )
            {
                // accepts client connection
                sock = serverSock.accept();
				// execute a new instance of ClientHandler object
				executor.execute(new ClientHandler(sock, bidItems));
            }
        }
        catch (IOException ex) {
			// print exception
            System.out.println( ex );
        }
    }
	public static void main( String[] args )
	{
		// initialize server and run
		Server server = new Server();
        server.runServer();
	}
}