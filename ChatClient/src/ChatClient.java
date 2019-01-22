import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatClient {

	private final String serverName;
	private final int portNumber;
	private Socket serverSocket;
	private OutputStream serverOut;
	private InputStream serverIn;
	private BufferedReader bufferedIn;
	
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	
	public ChatClient(String serverName, int portNumber) {
		this.serverName = serverName;
		this.portNumber = portNumber;
	}



	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ChatClient client = new ChatClient("localhost", 4444);
		client.addUserStatusListener(new UserStatusListener() {

			@Override
			public void online(String login) {
				// TODO Auto-generated method stub
				System.out.println("ONLINE: " + login);
			}

			@Override
			public void offline(String login) {
				// TODO Auto-generated method stub
				System.out.println("ONLINE: " + login);
			}
			
						
		});
		
		
		if(!client.connectToServer()) {
			System.err.println("Connection failed");
		} else {
			System.out.println("Connection succesfull");
			
			// logowanie
			if(client.login("user", "pass")) {
				System.out.println("\nLogin successful");
				
				client.msg("me","Messega from ChatClient application");
			} else {
				System.err.println("\nLogin failed");
			}
			
			//test - automatycznie zamknie client'a po zalogowaniu
			client.closeConnection();
		}
	
		//client.closeConnection();
		
	}



	private void msg(String sendTo, String msgBody) throws IOException {
		// TODO Auto-generated method stub
		String cmd = "msg " + sendTo + " " + msgBody + "\n";
		this.serverOut.write(cmd.getBytes());
	}



	private void closeConnection() throws IOException  {
		// TODO Auto-generated method stub
		String cmd = "quit\n";
		this.serverOut.write(cmd.getBytes());
	}



	private boolean login(String login, String password) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println("\n\n\n\nStarted login");
		String cmd = "login " + login + " " + password + "\n";
		this.serverOut.write(cmd.getBytes());
		//this.serverOut.write("\n".getBytes());
		
		String response = bufferedIn.readLine();
		System.out.println("\nServer response: " + response);
		
		if("You have succesfully logged in".equalsIgnoreCase(response)) {
			startServerDataReader();
			return true;
		} else {
			return false;
		}
	}



	

	private void startServerDataReader() {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			@Override
			public void run() {
				getData();
			}

		
		};
		t.start();
		
	}

	private void getData() {
		// TODO Auto-generated method stub
		String line;
		try {
			while((line = bufferedIn.readLine()) != null) {
				
				
				String[] tokens = line.split(" ");
				if(tokens != null && tokens.length > 0) {
					
					String cmd = tokens[0];
					if("online".equalsIgnoreCase(cmd)) {
						handleOnline(tokens);
					} else if ("offline".equalsIgnoreCase(cmd)) {
						handleOffline(tokens);
					}
				}
				
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}





	private boolean connectToServer() {
		try {
			this.serverSocket = new Socket(this.serverName, this.portNumber);
			this.serverOut = serverSocket.getOutputStream();
			this.serverIn = serverSocket.getInputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			this.serverOut.write("\n".getBytes());
			System.out.println("Server response: " + bufferedIn.readLine());  // odbieranie informacji z serwera
			System.out.println("Client port is: " + serverSocket.getLocalPort());
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	
	public void addUserStatusListener(UserStatusListener listener){
		
		userStatusListeners.add(listener);
		
	}
	
	public void removeUserStatusListener(UserStatusListener listener){
		
		userStatusListeners.remove(listener);
		
	}
	
	// informacje o zalogowanych uzytkownikach
	private void handleOnline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.online(login);
		}
		
	}
	
	
	// informacje o zalogowanych uzytkownikach
	private void handleOffline(String[] tokens) {
		String login = tokens[1];
		for(UserStatusListener listener : userStatusListeners) {
			listener.offline(login);
		}
		
	}
	
}






