import java.io.IOException;
import java.net.*;

public class Server {
	public static final int PORT = 1026;
	
	
	public static void main(String[] args) {
		int port = -1;
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		if (args.length == 0) {
			port = PORT;	
		}
		else{
			
			try {
				port = Integer.parseInt(args[0]);
				
			} catch (NumberFormatException e) {
				System.err.println("usage: Server [porta(intero)] (DefaultPort: 1026)");
				System.exit(1);
			}
		}
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Errore creazione serverSocket");
			e.printStackTrace();
			System.exit(2);
		}
			
		while(true) {
			
			try {
				System.out.println("Server PRONTO in attesa di una richiesta da un Client.");
				socket = serverSocket.accept();
			}catch (IOException e) {
				e.printStackTrace();
				
				try {
					serverSocket.close();
				}catch(IOException e1) {
					System.err.println("Errore chiusara serverSocket:");
					e1.printStackTrace();
					System.exit(2);
				}
				System.exit(2);
			}
				
			DirectoryServerThread thread = new DirectoryServerThread(socket);
			thread.run();
		}
	}
}
