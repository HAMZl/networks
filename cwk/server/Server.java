import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class Server 
{
	private File file = new File("log.txt");
	private String[] clientArguments = null;
	private ArrayList<BidItem> bidItems = new ArrayList<BidItem>();
	public boolean checkItemInList(String bidItem){
		for(BidItem item: bidItems){
			if(item.getItemName().equals(bidItem)){
				return true;
			}
		}
		return false;
	}
	public void runServer() {
        try
        {
            // we use port 6001.
            ServerSocket serverSock = new ServerSocket(6001);
			file.createNewFile();

            while( true )
            {
                // Accept; blocking; will not return until a client has made contact.
                Socket sock = serverSock.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            	String clientRequest = reader.readLine();
				this.clientArguments = clientRequest.split(" ");
                
				// writing to log file
                InetAddress inet = sock.getInetAddress();
                LocalDate localDate = LocalDate.now(); // current date
				LocalTime localTime = LocalTime.now(); // current time
				FileWriter fileWriter = new FileWriter("log.txt", true); // open file to append
				String content = localDate + "|" + localTime + "|" + inet.getHostAddress() + "|" + clientRequest + "\n";
				fileWriter.write(content); // write to file
				fileWriter.close(); // close file
                
				PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
				if(clientArguments[0].equals("show")){
					if(bidItems.size() == 0){
						writer.println("There are currently no items in this auction.");
					}
					for(BidItem item: bidItems){
						writer.println(item.toString());
					}
				} else if(clientArguments[0].equals("item")){
					if (checkItemInList(clientArguments[1])){
						writer.println("Failure.");
					} else {
						bidItems.add(new BidItem(clientArguments[1]));
						writer.println("Success.");
					}
				} else if(clientArguments[0].equals("bid")){
					double bid = Double.parseDouble(clientArguments[2]);
					if (!checkItemInList(clientArguments[1]) || bid <= 0.0){
						writer.println("Failure.");
					} else {
						for(BidItem item: bidItems){
							if (item.getItemName().equals(clientArguments[1])){
								if(item.getBid() < bid){
									item.setBidder(bid, inet);
									writer.println("Accepted.");
								} else {
									writer.println("Rejected.");
								}
							}
						}
					}
				}
				else {
					continue;
				}
                
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