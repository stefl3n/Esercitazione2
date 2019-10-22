import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtility {

	static protected void trasferisci_a_byte_file_binario (DataInputStream src, DataOutputStream dest) throws IOException
	{ // ciclo di lettura da sorgente e scrittura su destinazione
	int buffer = 0;
	try
	{ // esco dal ciclo alla lettura di un valore negativo ossia EOF
	while ( (buffer = src.read()) >= 0) dest.write(buffer);
	
	
	dest.flush();
	}
	catch (EOFException e)
	{ System.out.println("Problemi:");
	e.printStackTrace();
	}
	} 
	
	static protected void trasferisci_a_byte_file_binario2 (DataInputStream src, DataOutputStream dest, long size) throws IOException
	{ // ciclo di lettura da sorgente e scrittura su destinazione
	byte[] buff = new byte[(int)size];
	try
	{ // esco dal ciclo alla lettura di un valore negativo ossia EOF
	src.read(buff);
	dest.write(buff);
	dest.flush();
	}
	catch (EOFException e)
	{ System.out.println("Problemi:");
	e.printStackTrace();
	}
	} 
	static protected void InvioFile(File f, int min_size,DataInputStream socketIn, DataOutputStream socketOut) throws IOException  {
		long file_size = f.length();
		
		if(file_size > min_size && !f.isDirectory()) {
			
			//trasferimento file f
			
			socketOut.writeUTF(f.getName());
			
			if(socketIn.readUTF().equals("attiva")) {
				
				System.out.println("sto inviando "+f.getName());
				socketOut.writeLong(file_size);
				FileUtility.trasferisci_a_byte_file_binario(new DataInputStream(new FileInputStream(f)), socketOut);
				System.out.println("inviato");
				
			}
		}if(f.isDirectory()) {
			
			for(File file: f.listFiles()) {
				
				InvioFile(file,min_size,socketIn,socketOut);
				
			}
			
		}
		
		
	}
}
