import java.io.*;
import java.net.*;

public class Client 
{
	public void connect(String request) {

        try {
            Socket s = new Socket( "localhost", 6001 );
			PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
			writer.println(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream() ) );
            String serverOutput = reader.readLine();
			while(serverOutput != null){
				System.out.println(serverOutput);
				serverOutput = reader.readLine();
			}
            reader.close();
            s.close();
        }
        catch( IOException e )
        {
            System.out.println( e );
        }
    }
	public static void main( String[] args ){
		Client client = new Client();
		if(args.length == 0 || args.length > 3){
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		} else if(args.length == 1 && !args[0].equals("show")){
			System.out.println("Invalid argument: "+ args[0]);
			System.exit(1);
		} else if(args.length == 2 && !args[0].equals("item")){
			System.out.println("Invalid argument: "+ args[0]);
			System.exit(1);
		} else if(args.length == 3 && !args[0].equals("bid")){
			System.out.println("Invalid argument: "+ args[0]);
			System.exit(1);
		}
		String request = String.join(" ", args);
        client.connect(request);
	}
}