package connectionClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

//za kazdym razem gdy tworzone jest gniazdo klienta - jest ono przekazywane do tej klasy
public class ServerRunnable implements Runnable {

	protected Socket clientSocket = null;
	
	// konstruktor
	public ServerRunnable(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run() {
			
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String arg1;
			arg1 = in.readLine();
			//System.out.println("Client says: " + arg1);
			out.println("Established connection with client");
			
			
			// wersja 2 
			//OutputStream outputStream = clientSocket.getOutputStream();
			//outputStream.write("Hello World\n".getBytes());
			
			for(int i = 0; i < 10000; i++) {
				
				out.println("a");
			}
			
			///
			
			
			// Zamykanie polaczenia
			clientSocket.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

