import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UserPanel extends JPanel implements UserStatusListener {
	
	private final ChatClient client;
	private JList<String> userListUI;
	private DefaultListModel<String> userListModel;
	
	
	
	public UserPanel(ChatClient client) {
		
		this.client = client;
		this.client.addUserStatusListener(this);
		
		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
	}




	public static void main(String[] args) {
		ChatClient client = new ChatClient("localhost", 4444);
		
		UserPanel userPanel = new UserPanel(client);
		
		
		JFrame frame = new JFrame("Chat Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.getContentPane();
		frame.add(userPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}




	@Override
	public void online(String login) {
		// TODO Auto-generated method stub
		userListModel.addElement(login);
	}




	@Override
	public void offline(String login) {
		// TODO Auto-generated method stub
		userListModel.removeElement(login);
	}
	
}
