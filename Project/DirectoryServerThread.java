import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DirectoryServerThread extends Thread {
	Socket socket;
	DataInputStream inSock;
	DataOutputStream outSock;
	String nomeFile;
	long size;
	
	public DirectoryServerThread(Socket socket) {
		this.socket = socket;
		
		try {
			inSock = new DataInputStream(this.socket.getInputStream());
			outSock = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Thread:"+Thread.currentThread().getId()+" Errore Input/Output Stream");
			return;
		}	
	}
	
	public void run() {
		boolean finito =false;
		
		try {
			while(!finito) {
				
				nomeFile = inSock.readUTF();
				
				if(nomeFile.equals("finito")) {
					finito=true;
					socket.shutdownOutput();
					return;
				}
				else {
					System.out.println("Thread:"+Thread.currentThread().getId()+" Richiesta invio FILE: "+nomeFile);
				}
				
				if(new File(nomeFile).exists()) {
					
					outSock.writeUTF("salta file");
					System.out.println("Thread:"+Thread.currentThread().getId()+" File GIA' presente");
				}else {
					
					outSock.writeUTF("attiva");
					
					size = inSock.readLong();
					
					FileUtility.trasferisci_a_byte_file_binario(inSock, new DataOutputStream(new FileOutputStream(nomeFile)),size);
					System.out.println("Thread:"+Thread.currentThread().getId()+" File ricevuto");		
				}
			}
		}catch(IOException e) {
			
			try {
				socket.close();
			} catch (IOException e1) {
				System.err.println("Thread:"+Thread.currentThread().getId()+" Errore chiusura socket:");
				e1.printStackTrace();
				return;
			}
			System.err.println("Thread:"+Thread.currentThread().getId()+" Stream Chiuso. Problema:");
			e.printStackTrace();
			return;
		}
		
	}
}