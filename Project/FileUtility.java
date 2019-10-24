import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class FileUtility {

	static protected void trasferisci_a_byte_file_binario (DataInputStream src, DataOutputStream dest) throws IOException { 
		// ciclo di lettura da sorgente e scrittura su destinazione
		int buffer = 0;
	
		try
		{ 
			// esco dal ciclo alla lettura di un valore negativo ossia EOF
			while ( (buffer = src.read()) >= 0) 
				dest.write(buffer);	
			dest.flush();
			
		}catch (EOFException e){ 
			System.out.println("Problemi:");
			e.printStackTrace();
		}
	}
	
	static protected void trasferisci_a_byte_file_binario (DataInputStream src, DataOutputStream dest, long size) throws IOException { 
		// ciclo di lettura da sorgente e scrittura su destinazione
		int buffer = 0;
	
		try
		{ 
			// esco dal ciclo alla lettura di un valore negativo ossia EOF o quando raggiungo la dimensione attesa del file (size) 
			for(int i=0; i<size && ((buffer = src.read()) >= 0); i++)
				dest.write(buffer);	
			
			dest.flush();
			
		}catch (EOFException e){ 
			System.out.println("Problemi:");
			e.printStackTrace();
		}
	}
}
