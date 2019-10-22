import java.io.*;
import java.net.*;

public class Server {
	
	
	public static void main(String args[])
	{
		int port=-1;
		try {
		port=Integer.parseInt(args[0]);
			if(port<1023||port>49152)
			{
				//messaggio errore e chiuditi
			}
		
			
		}catch(NumberFormatException e)
		{
			//stacktrace e chiudi
		}
		
	
	
	//creare connessione
		try {
		ServerSocket Ssocket=new ServerSocket(port);
		Socket socket;
		DataInputStream dataIn;
		DataOutputStream dataOut;
		String fileName;
		File file;
		int fileLength=0;
		

		//attendi stream
		while(true)
		{
			socket=Ssocket.accept();
			
			
			
			//verifica che il file non sia presente
			
			dataIn=new DataInputStream(socket.getInputStream());
			dataOut=new DataOutputStream(socket.getOutputStream());
			fileName=dataIn.readUTF();
			file=new File(fileName);
			if(file.exists())
			{
				
				//manda il messaggio "salta file"
				dataOut.writeUTF("salta file");
				
				
			}
			else
			{
	
				//manda "accetta"
				dataOut.writeUTF("accetta");
				
				
				
				//attendi lunghezza del file
				fileLength=Integer.parseInt(dataIn.readUTF());
				
				
				//genera thread con socket nomefile e length
				
				
			}
		}
		
		
		
		
		
		
		
		
		}
		catch(Exception e)
		{
			
		}
	
	
	
		
		
		
	}

}
