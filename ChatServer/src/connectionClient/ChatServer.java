package connectionClient;

public class ChatServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Server");
		SocketServer s = new SocketServer();
		s.runServer();
	}

}



//testowac polacznie z serwerem mozna przez windows command line:    telnet localhost 4444    (4444 to przyjety w klasie SocketServer numer portu)