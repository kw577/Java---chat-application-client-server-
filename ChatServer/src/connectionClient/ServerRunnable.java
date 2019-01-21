package connectionClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

			this.handleClient();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	private void handleClient() throws IOException, InterruptedException{
		
		OutputStream outputStream = clientSocket.getOutputStream();
		InputStream inputStream = clientSocket.getInputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		String arg1;
		arg1 = reader.readLine();
		//System.out.println("Client says: " + arg1);
		out.println("Established connection with client");
		
		System.out.println("Established connection from: " + clientSocket);
		
		/*
		for(int i = 0; i < 10000; i++) {
			Thread.sleep(1000);
			out.println("a");
		}
		*/
		///
		
		String line;
		while ((line = reader.readLine()) != null) {
			// jesli client wpisze komende quit - nastapi zamkniecie polaczenia z serwerem
			if("quit".equalsIgnoreCase(line)) {
				break;
			}
			// w przeciwnym razie serwer przesyla z powrotem ta sama linie do klienta ktora on wyslal na serwer
			String msg = "You typed: " + line + "\n";
			outputStream.write(msg.getBytes());
			
		}
		
								
		// Zamykanie polaczenia
		clientSocket.close();
		
	}
	
	
}

