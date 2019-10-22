import java.io.*;
import java.io.IOException;
import java.net.*;

public class Server {
	public static final int PORT = 1026;
	public static void main(String[] args) {
		
		int port = -1;
		FileUtility utility = new FileUtility();
		if (args.length == 0) {
			
			port = PORT;
			
		}else {try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Errore formato porta (intero)");
			System.exit(1);
			e.printStackTrace();
		}}
		
		ServerSocket serverSocket = null;
		
		
			
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.err.println("Errore Socket");
				System.exit(2);
				
			}
			
			while(true) {
				
				Socket clientSocket = null;
				try {
					System.out.println("Inizio");
					clientSocket = serverSocket.accept();
				} catch (IOException e) {
					System.err.println("Errore accept");
					System.exit(3);
				}
				
				new DirectoryServer(clientSocket).run();
				
				
				
				
				
			}
			
		
	}

}
