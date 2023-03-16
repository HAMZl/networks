import java.io.*;
import java.net.*;

public class Client 
{
	// function to connect to surver with client request as an argument
	public void connect(String request) {

        try {
			// initialize socket with host name using port 6001
            Socket s = new Socket( "localhost", 6001 );
			// initialize writer to write to server
			PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
			// write request to server
			writer.println(request);
			// initialize reader to read from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // read line from server
			String serverOutput = reader.readLine();
			// keep reading from server until reader reaches null
			while(serverOutput != null){
				// print server output to client-side terminal
				System.out.println(serverOutput);
				// read next line
				serverOutput = reader.readLine();
			}
			// close reader and server
            reader.close();
            s.close();
        }
		// catch exception
        catch( IOException e )
        {
			// print exception
            System.out.println( e );
        }
    }
	public static void main( String[] args ){
		// initialize client
		Client client = new Client();
		// check if number of argument is valid
		if(args.length == 0){
			System.out.println("Usage: java Client show");
			System.out.println("Usage: java Client item <string>");
			System.out.println("Usage: java Client bid <item> <value>");
		} else if(args.length > 3){
			System.out.println("Invalid number of arguments.");
		// check if length of arguments is 1 and first argument is not 'show'
		} else if(args.length == 1 && !args[0].equals("show")){
			System.out.println("Invalid argument: "+ args[0]);
		// check if length of arguments is 2 and first argument is not 'item'
		} else if(args.length == 2 && !args[0].equals("item")){
			System.out.println("Invalid argument: "+ args[0]);
		// check if length of arguments is 3 and first argument is not 'bid'
		} else if(args.length == 3 && !args[0].equals("bid")){
			System.out.println("Invalid argument: "+ args[0]);
		} else {
			// check if length of arguments is 3 and first argument is 'bid'
			if(args.length == 3 && args[0].equals("bid")){
				try {
					// ceck if third argument is double
					Double.parseDouble(args[2]);
				} catch (NumberFormatException ex){
					// print exception and exit
					System.out.println(ex);
					System.exit(0);
				}	
			}
			// join array with a space inbetween each argument to form a request
			String request = String.join(" ", args);
			// invoke connect function
        	client.connect(request);
		}
	}
}