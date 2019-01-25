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
	
	
	public ArrayList<String> listOfContacts = new ArrayList<>();
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	private ArrayList<MessageListener> messageListeners = new ArrayList<>();
	
	
	
	public ChatClient(String serverName, int portNumber) {
		this.serverName = serverName;
		this.portNumber = portNumber;
	}


	public void answer(String sendTo) throws IOException {
		// TODO Auto-generated method stub
		String cmd = "answer " + sendTo + " " + "about status" +"\n";
		System.out.println("\nWyslano odpowiedz zwrotna: " + cmd);
		this.serverOut.write(cmd.getBytes());
	}
	
	

	public void msg(String sendTo, String msgBody) throws IOException {
		// TODO Auto-generated method stub
		String cmd = "msg " + sendTo + " " + msgBody + "\n";
		this.serverOut.write(cmd.getBytes());
	}



	public void closeConnection() throws IOException  {
		// TODO Auto-generated method stub
		String cmd = "quit\n";
		this.serverOut.write(cmd.getBytes());
	}



	public boolean login(String login, String password) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println("\n\n\n\nStarted login");
		String cmd = "login " + login + " " + password + "\n";
		this.serverOut.write(cmd.getBytes());
		//this.serverOut.write("\n".getBytes());
		
		String response = bufferedIn.readLine();
		System.out.println("\nServer response: " + response);
		
		if("contacts".equalsIgnoreCase(response.substring(0,8))) {
			startServerDataReader();
			this.fetchListOfContacts(response);
			return true;
		} else {
			return false;
		}
	}



	

	private void fetchListOfContacts(String response) {
		// TODO Auto-generated method stub
		String data = response.replaceAll("contacts ","");
		
		String[] contacts = data.split(" ");
		if(contacts != null && contacts.length > 0) {
			
			for(int i=0; i<contacts.length; i++) {
				if(contacts[i]!=null && contacts[i] != "  ") {
				System.out.println("\nDodano " + contacts[i] + " do listy kontaktow");
				this.listOfContacts.add(contacts[i]);
				}
			}
						
		}
		//listOfContacts.toString();
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
						
						//odpowiedz zwrotna - na informacje o tym ze inny uzytkownik jest online - cleint przedstawia mu swoj status
						answer(tokens[2]);
						
						
					} else if ("offline".equalsIgnoreCase(cmd)) {
						// nie wymaga odpowiedzi zwrotnej
						handleOffline(tokens);
					} else if ("answer".equalsIgnoreCase(cmd)) {  
						
						System.out.println("\n\notrzymano otpowiedz zwrotna");
						
						// odpowiedz zwrotna na info o statusie
						handleOnline(tokens);
					} else if ("msg".equalsIgnoreCase(cmd)) {
						String[] tokensMsg = line.split(" ", 3); // oddziaala tylko 3 pierwsza slowa - reszta - wiadomosc do innego uzytkownika bedzie przechowywana w  tokensMsg[2]
						
						handleMessage(tokensMsg);
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





	private void handleMessage(String[] tokensMsg) {
		// TODO Auto-generated method stub
		String login = tokensMsg[1];
		String msgBody = tokensMsg[2];
		
		for(MessageListener listener : messageListeners) {
			listener.onMessage(login, msgBody);
		}
	}



	public boolean connectToServer() {
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
	
	
	public void addMessageListener(MessageListener listener){
		
		messageListeners.add(listener);
		
	}
	
	public void removeMessageListener(MessageListener listener){
		
		messageListeners.remove(listener);
		
	}
	
	
	// informacje o zalogowanych uzytkownikach
	private void handleOnline(String[] tokens) {
		String login = tokens[2];
		for(UserStatusListener listener : userStatusListeners) {
			listener.online(login);
			
		}
		
	}
	
	
	// informacje o zalogowanych uzytkownikach
	private void handleOffline(String[] tokens) {
		String login = tokens[2];
		for(UserStatusListener listener : userStatusListeners) {
			listener.offline(login);
		}
		
	}
	
}







