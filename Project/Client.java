import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client {
	
	public static void main(String[] args) {
		int min_size=0;
		int serverPort=0;
		InetAddress addr=null;
		
		//controllo argomenti
		if(args.length!=3) {
			System.out.println("usage: Client min_size(intero) serverAddr serverPort(intero)");
			System.exit(1);
		}
		
		try {
			min_size=Integer.parseInt(args[0]);
			
			if(min_size<=0) {
				System.err.println("min_size deve essere >0");
				System.exit(1);
			}
			
			serverPort=Integer.parseInt(args[2]);
			addr=InetAddress.getByName(args[1]);
			
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Remote-host sconosciuto");
			System.exit(2);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("usage: Client min_size(intero) serverAddr serverPort(intero)");
			System.exit(3);
		}
		
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		String read_dir = null;
		Socket socket = null;
		DataInputStream socketIn = null;
		DataOutputStream socketOut = null;
		
		
		do{
			//richiesta dir e invio contenuto dir
			System.out.println("Inserire il percorso assoluto o relativo della directory di cui si vuole inviare il contenuto (EOF per terminare)");
				
			try {
				read_dir=in.readLine();
				
				if(read_dir!=null) {
					File dir=new File(read_dir);
					
					//invio file(s)
					if(dir.isDirectory()) {
						
						//creazione Socket per dir corrente
						socket = new Socket(addr,serverPort);
						socketIn = new DataInputStream(socket.getInputStream());
						socketOut = new DataOutputStream(socket.getOutputStream());
						
						//ciclo nei file della directory
						for(File f : dir.listFiles())
							InvioFile(f, min_size, socketIn, socketOut);
						socketOut.writeUTF("finito");
						socket.shutdownOutput();
					}
					else {
						System.out.println(read_dir + " non è una directory");
					}
				}
					
			}catch(IOException e) {
				e.printStackTrace();
				System.exit(4);
			}
			
			
		}while(read_dir!=null);
		
	}
	
	private static void InvioFile(File f, int min_size,DataInputStream socketIn, DataOutputStream socketOut) throws IOException  {
		long file_size = f.length();
		String ans = null;
		
		if(file_size > min_size && !f.isDirectory()) {
			
			//trasferimento file f
			
			socketOut.writeUTF(f.getName());
			ans = socketIn.readUTF();
			
			if(ans.equals("attiva")) {
				
				System.out.println("Sto inviando "+f.getName());
				socketOut.writeLong(file_size);
				FileUtility.trasferisci_a_byte_file_binario(new DataInputStream(new FileInputStream(f)), socketOut);
				System.out.println("Inviato");
				
			}
			else if (ans.equals("salta file")){
				System.out.println("File "+f.getName()+" già esistente o non richiesto dal server.");
			}
			else {
				System.err.println("Errore nel trasferimento del file "+f.getName()+". Risposta dal server non prevista: "+ans);
				System.exit(1);
			}
				
		}
		
		if(f.isDirectory()) {
			
			for(File file: f.listFiles())
				InvioFile(file,min_size,socketIn,socketOut);	
		}
	}
}
