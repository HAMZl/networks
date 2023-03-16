import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class Server 
{
	// initialize logfile
	private File file = new File("log.txt");
	// initialize array for arguments from client request
	private String[] clientArguments = null;
	// initialize array for to store all bid items
	private ArrayList<BidItem> bidItems = new ArrayList<BidItem>();

	// function to check if item exists in arrayList
	public boolean checkItemInList(String bidItem){
		for(BidItem item: bidItems){
			if(item.getItemName().equals(bidItem)){
				return true; // item is already in list
			}
		}
		return false; // item does not exist in list
	}

	public void runServer() {
        try
        {
            // initialize server socket using port 6001
            ServerSocket serverSock = new ServerSocket(6001);
			// create logfile if it doesnt exist
			file.createNewFile();

            while( true )
            {
                // accepts contact from client
                Socket sock = serverSock.accept();
				// initialize reader to read fro client
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            	// read client request
				String clientRequest = reader.readLine();
				// split client's request to an array
				this.clientArguments = clientRequest.split(" ");
                
				// writing to log file
                InetAddress inet = sock.getInetAddress();
				// get current date and time
                LocalDate localDate = LocalDate.now();
				LocalTime localTime = LocalTime.now();
				// initialize writer to append to log.txt
				FileWriter fileWriter = new FileWriter("log.txt", true);
				// initialize content of the line to be written to log file
				String content = localDate + "|" + localTime + "|" + inet.getHostAddress() + "|" + clientRequest + "\n";
				fileWriter.write(content); // write to file
				fileWriter.close(); // close file
                
				PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
				if(clientArguments[0].equals("show")){
					if(bidItems.size() == 0){ // check if arrayList is of length 0
						writer.println("There are currently no items in this auction.");
					}
					for(BidItem item: bidItems){ // display all items available to bid
						writer.println(item.toString());
					}
				} else if(clientArguments[0].equals("item")){ 
					if (checkItemInList(clientArguments[1])){ // check if item already exists
						writer.println("Failure.");
					} else {
						bidItems.add(new BidItem(clientArguments[1])); // add new item to arrayList
						writer.println("Success."); // item added successfully
					}
				} else if(clientArguments[0].equals("bid")){
					// convert third argument of client request to double
					double bid = Double.parseDouble(clientArguments[2]);
					// check if the item to bid does not exists in arrayList or if clients bid is valid
					if (!checkItemInList(clientArguments[1]) || bid <= 0.0){
						writer.println("Failure."); // bid failed
					} else {
						for(BidItem item: bidItems){
							if (item.getItemName().equals(clientArguments[1])){
								// check if clients bid is greater than the current bid of item
								if(item.getBid() < bid){
									// set the bid to new bid with top bidder which is the client
									item.setBidder(bid, inet);
									writer.println("Accepted."); // bid accepted
								} else {
									writer.println("Rejected."); // bid rejected
								}
							}
						}
					}
				}
				else {
					continue;
				}
                // close writer and socket
				writer.close();
                sock.close();
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