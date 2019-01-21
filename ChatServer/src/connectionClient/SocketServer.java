package connectionClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	int portNumber = 4444;
	ServerSocket serverSocket = null;
	
	public void runServer() {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();
				ServerRunnable m = new ServerRunnable(clientSocket);
				new Thread(new ServerRunnable(clientSocket)).start();
			}catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
