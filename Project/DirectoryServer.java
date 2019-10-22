import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DirectoryServer extends Thread {
	
	Socket clientSocket = null;
	DataInputStream inSock = null;
	DataOutputStream outSock = null;
	public DirectoryServer(Socket clientSocket) {
		
		this.clientSocket = clientSocket;
		try {
			inSock = new DataInputStream(this.clientSocket.getInputStream());
			outSock = new DataOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Errore Input/Output Stream");
			System.exit(3);
		}
		
	}
	
	public void run() {
		
		String nomeFile = null;
		long size;
		
		
		while(true) {
			
			try {
				
				nomeFile = inSock.readUTF();
				System.out.println(nomeFile+ " is file");
				
			} catch (IOException e) {
				System.err.println("Stream Chiuso");
				return;
			}	
			if(new File(nomeFile).exists() || nomeFile == null) {
				
				try {
					outSock.writeUTF("Salta file");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				
				try {
					outSock.writeUTF("attiva");
					
					size = inSock.readLong();
					
					FileUtility.trasferisci_a_byte_file_binario2(inSock, new DataOutputStream(new FileOutputStream(nomeFile)),size);
					System.out.println("File Scritto");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
		}
		
		
		
	}
	
	
}