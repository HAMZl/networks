import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class ClientHandler implements Runnable {
    private Socket sock;
    private BufferedReader reader;
    private InetAddress inet;
    private PrintWriter writer;
	// initialize array for arguments from client request
	private String[] clientArguments = null;
	// initialize array for to store all bid items
	private ArrayList<BidItem> bidItems;
	
    public ClientHandler(Socket sock, ArrayList<BidItem> bidItems){
        this.sock = sock;
        this.bidItems = bidItems;
    }

    // function to check if item exists in arrayList
	public boolean checkItemInList(String bidItem){
		for(BidItem item: bidItems){
			if(item.getItemName().equals(bidItem)){
				return true; // item is already in list
			}
		}
		return false; // item does not exist in list
	}

    public void writeToFile(String clientRequest){
        // get current date and time
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        // initialize writer to append to log.txt
        try {
            FileWriter fileWriter = new FileWriter("log.txt", true);
            // initialize content of the line to be written to log file
            String content = localDate + "|" + localTime + "|" + inet.getHostAddress() + "|" + clientRequest + "\n";
            fileWriter.write(content); // write to file
            fileWriter.close(); // close file
        } catch (IOException ex) {
			// print exception
            System.out.println( ex );
        }
    }

    public void clientRequestShow(){
        if(bidItems.size() == 0){ // check if arrayList is of length 0
            writer.println("There are currently no items in this auction.");
        }
        for(BidItem item: bidItems){ // display all items available to bid
            writer.println(item.toString());
        }
    }

    public void clientRequestItem(){
        if (checkItemInList(clientArguments[1])){ // check if item already exists
            writer.println("Failure.");
        } else {
            bidItems.add(new BidItem(clientArguments[1])); // add new item to arrayList
            writer.println("Success."); // item added successfully
        }
    }

    public void clientRequestBid(){
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

    @Override
	public void run() {
        try
        {
            while(!sock.isClosed())
            {
				// initialize reader to read from client
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            	// read client request
				String clientRequest = reader.readLine();
				// split client's request to an array
				this.clientArguments = clientRequest.split(" ");
                inet = sock.getInetAddress();
				// writing to log file
                writeToFile(clientRequest);
				writer = new PrintWriter(sock.getOutputStream(), true);
				if(clientArguments[0].equals("show")){
					clientRequestShow();
				} else if(clientArguments[0].equals("item")){ 
					clientRequestItem();
				} else if(clientArguments[0].equals("bid")){
                    clientRequestBid();
				}
				else {
					continue;
				}
                // close writer and socket
                reader.close();
				writer.close();
                sock.close();
            }
        } catch (IOException ex) {
			// print exception
            System.out.println( ex );
        }
    }
}
