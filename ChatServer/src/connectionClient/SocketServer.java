package connectionClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

	int portNumber = 4444;
	ServerSocket serverSocket = null;
	//private Server server = new Server(portNumber);
	
	// lista client'ow z ktorymi nawiazano polaczenie
	private ArrayList<ServerRunnable> clientList = new ArrayList<>();
	
	
	public List<ServerRunnable> getClientList() {
		return clientList;
	}




	public void runServer() {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();
				ServerRunnable m = new ServerRunnable(clientSocket, this);
				
				ServerRunnable newClient = new ServerRunnable(clientSocket, this);
				
				new Thread(newClient).start();
				
				clientList.add(newClient);
				
			}catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}




	public void removeConnection(ServerRunnable serverRunnable) {
		// TODO Auto-generated method stub
		this.clientList.remove(serverRunnable);
	}
}
