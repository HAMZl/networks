import java.io.*;
import java.net.*;

public class Client 
{
	public void connect() {

        try {

            // Try and create the socket. The server is assumed to be running on the same host ('localhost'),
            // so first run 'KnockKnockServer' in another shell.
            Socket s = new Socket( "localhost", 6001 );

            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream() ) );
            String advice = reader.readLine();
            System.out.println(advice);
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
        client.connect();
	}
}