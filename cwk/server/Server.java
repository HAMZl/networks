import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.*;

public class Server {
	private static final int PORT = 6001;
    private static final int POOL_SIZE = 30;
	// initialize logfile
	private File file = new File("log.txt");
	private ServerSocket serverSock;
	private Socket sock;
	private ExecutorService executor;
	private ArrayList<BidItem> bidItems = new ArrayList<BidItem>();
	public void runServer() {
        try
        {
            // initialize server socket using port 6001
            serverSock = new ServerSocket(PORT);
			executor = Executors.newFixedThreadPool(POOL_SIZE);
			file.createNewFile();

            while( true )
            {
                // accepts contact from client
                sock = serverSock.accept();
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
		// initialize server and run it
		Server server = new Server();
        server.runServer();
	}
}