package connectionClient;

public class ChatServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Server");
		SocketServer s = new SocketServer();
		s.runServer();
	}

}