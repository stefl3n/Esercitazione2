import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
	Socket socket;
	String nomeFile;
	int lengthFile;
	File file;
	DataInputStream sockIn;
	DataOutputStream sockOut;
	
	public ServerThread(Socket socket, String nomeFile, int lengthFile) {
		this.socket = socket;
		this.nomeFile = nomeFile;
		this.lengthFile = lengthFile;
		this.file = new File(nomeFile);
		
		try {
			sockIn = new DataInputStream(this.socket.getInputStream());
			sockOut = new DataOutputStream(this.socket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void run() {
		Byte temp;
		FileOutputStream fileOut = null;
		
		try {
			fileOut = new FileOutputStream(this.file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<this.lengthFile; i++) {
			try {
				temp =sockIn.readByte();
				fileOut.write(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}	
}
