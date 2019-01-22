package connectionClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;

//za kazdym razem gdy tworzone jest gniazdo klienta - jest ono przekazywane do tej klasy
public class ServerRunnable implements Runnable {

	protected Socket clientSocket = null;
	//public static State userState = State.logged;
	private State userState = State.logged;
	private SocketServer serverSocket;
	private OutputStream outputStream;
	private String loggedUser;
	
	// Test - docelowo zmienic na baze danych
	private HashSet<String> topicSet = new HashSet<>();
	
	
	// konstruktor
	public ServerRunnable(Socket clientSocket, SocketServer serverSocket) {
		this.clientSocket = clientSocket;
		this.serverSocket = serverSocket;
	}
	
	public void run() {
			
		try {

			this.handleClient();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	private void handleClient() throws IOException, InterruptedException{
		
		this.outputStream = clientSocket.getOutputStream();
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
			
			// podzial linii otrzymanej od clienta na pojedyncze slowa 
			String[] tokens = line.split(" ");
			if(tokens != null && tokens.length > 0) {
				
				String cmd = tokens[0];
				String msg;
				
				// jesli client wpisze komende quit - nastapi zamkniecie polaczenia z serwerem
				if("quit".equalsIgnoreCase(cmd)) {
					logging_out();
					break;
				} else if ("login".equalsIgnoreCase(cmd)) {
					check_login(outputStream, tokens);
				} else if ("msg".equalsIgnoreCase(cmd)) {
					String[] tokensMsg = line.split(" ", 3); // oddziaala tylko 3 pierwsza slowa - reszta - wiadomosc do innego uzytkownika bedzie przechowywana w  tokensMsg[2]
					sendMessage(tokensMsg);
				} else if ("join".equalsIgnoreCase(cmd)) { // dolaczenie do zapisanej rozmowy
					joinConverstion(tokens);
				} else if ("leave".equalsIgnoreCase(cmd)) { // dolaczenie do zapisanej rozmowy
					leaveConversation(tokens);
				}		
				else {
					msg = "unknown command: " + cmd + "\n";
					outputStream.write(msg.getBytes());
				}
				// w przeciwnym razie serwer przesyla z powrotem ta sama linie do klienta ktora on wyslal na serwer
				
				// TEST
				//msg = "You typed: " + line + "\n";
				
				//outputStream.write(msg.getBytes());
				
				
			}
			

			
		}
		
								
		// Zamykanie polaczenia
		clientSocket.close();
		
	}

	




	private void logging_out() throws IOException {
		this.serverSocket.removeConnection(this);
		
		// po wylogowaniu  wysylamy do wszystkich pozostalych klientow informacje 
		String byeMsg = "user " + this.loggedUser + " is offline\n";
		List<ServerRunnable> clientList = this.serverSocket.getClientList();
		for(ServerRunnable srvRun : clientList) {
			if(!this.loggedUser.equals(srvRun.getLoggedUser()))
				if(srvRun.getLoggedUser() != null)
					srvRun.send(byeMsg);
		}
		
		this.clientSocket.close();
		
	}

	private void check_login(OutputStream outputStream, String[] tokens) throws IOException {
		// komenda typu:  login <user> <password>
		if(tokens.length == 3) {
			String login = tokens[1];
			String password = tokens[2];
			String msg;
			
			
			// Test login - za pomoca ustawionych na stale wartosci 
			if((login.equals("user") && password.equals("pass")) || (login.equals("me") && password.equals("pass1"))) {
				msg = "You have succesfully logged in\n";
				this.userState = State.logged;
				this.loggedUser = login;
				System.out.println("\nUser: " + login + " logged in\n");
				
				// po zalogowaniu sie wysylamy do wszystkich pozostalych klientow informacje o nowym zalogowanym uczestniku  
				String greetingMsg = "user " + login + " is online\n";
				List<ServerRunnable> clientList = this.serverSocket.getClientList();
				for(ServerRunnable srvRun : clientList) {
					if(!login.equals(srvRun.getLoggedUser()))
						if(srvRun.getLoggedUser() != null)
							srvRun.send(greetingMsg);
				}
				
				
			} else {
				msg = "Error in login\n";
			}
			
			try {
				outputStream.write(msg.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// dodac sprawdzanie konta uzytkownika za pomoca polaczenia z baza danych
			//
			//
			//
			//
			//
			//
			//
			
		}
		
	}

	private void send(String greetingMsg) throws IOException {
		// TODO Auto-generated method stub
		if(this.userState != State.unlogged) this.outputStream.write(greetingMsg.getBytes());
	}
	
	
	
	
	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	// komenda typu:  msg <user> <text>
	// komenda typu:  msg #topic <text>
	private void sendMessage(String[] tokens) throws IOException {
		
		String sendTo = tokens[1];
		String body = tokens[2];
		
		// sprawdzenie czy zostala podana zmienna okreslajaca wybor konwersacji
		boolean isTopic = false;
		if(sendTo.charAt(0) == '#') isTopic = true;  // sprawdza czy drugi przeslany parametr to #  - wtedy wiadomosc ma byc wyslana do wszystkich ktorzy sa uczestnikami tej konwersacji
		
		
		List<ServerRunnable> clients = this.serverSocket.getClientList();
		for(ServerRunnable srvRun : clients) {
			if(isTopic) {
				if(srvRun.isMemberOfDiscussion(sendTo)) {
					String outMsg = "msg " + sendTo + ":" + this.loggedUser + " " + body + "\n";
					srvRun.send(outMsg);
				}
			}
			else {
			
			
				if(sendTo.equalsIgnoreCase(srvRun.getLoggedUser())) {
					String outMsg = "msg " + this.loggedUser + " " + body + "\n";
					srvRun.send(outMsg);
				}
			}
		}
		
		
	}
	
	
	public boolean isMemberOfDiscussion(String topic) {
		
		 return this.topicSet.contains(topic);
	}
		
	// komenda typu   join <topic>
	private void joinConverstion(String[] tokens) {
		if(tokens.length > 1) {
			String topic = tokens[1];
			topicSet.add(topic);
		}
		
	}
	
	
	private void leaveConversation(String[] tokens) {
		if(tokens.length > 1) {
			String topic = tokens[1];
			topicSet.remove(topic);
		}
		
	}
	
}

