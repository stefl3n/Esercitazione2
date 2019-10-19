import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class Client {
	public void main(String[] args) {
		int min_size=0;
		int serverPort=0;
		InetAddress addr=null;
		boolean another_dir=true;
		
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
		catch(UnknownHostException uhe) {
			uhe.printStackTrace();
			System.out.println("remote host sconosciuto");
			System.exit(1);
		}
		catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("usage: Client min_size(intero) serverAddr serverPort(intero)");
			System.exit(1);
		}
		
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		String read_dir;
		
		//richiesta dir e invio contenuto dir
		while(another_dir) {
			System.out.println("Inserire il percorso assoluto o relativo della directory di cui si vuole inviare il contenuto...");
			
			try {
				read_dir=in.readLine();
				File dir=new File(read_dir);
				
				//invio file(s)
				if(dir.isDirectory()) {
					//creazione socket per dir corrente
					Socket socket=new Socket(addr,serverPort);
					DataInputStream socketIn=new DataInputStream(socket.getInputStream());
					DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
					
					//ciclo nei file della directory
					for(File f : dir.listFiles()) {
						long file_size = f.length();
						
						if(file_size > min_size) {
							//trasferimento file f
							socketOut.writeUTF(f.getName());
							if(socketIn.readUTF().equals("attiva")) {
								
								System.out.println("sto inviando "+f.getName());
								socketOut.writeLong(file_size);
								FileUtility.trasferisci_a_byte_file_binario(new DataInputStream(new FileInputStream(f)), socketOut);
								System.out.println("inviato");
							}
						}
					}
					
					socket.close();
				}
				else {
					System.out.println(read_dir + " non e' una directory");
				}
				
				System.out.println("Vuole inserire un'altra directory?(s/n)");
				if(in.readLine().equals("n")) another_dir=false;
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
