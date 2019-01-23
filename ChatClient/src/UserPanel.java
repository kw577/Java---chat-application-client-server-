import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UserPanel extends JPanel implements UserStatusListener {
	
	private final ChatClient client;
	private JList<String> userListUI;
	public DefaultListModel<String> userListModel;
	
	
	
	public UserPanel(ChatClient client) {
		
		this.client = client;
		this.client.addUserStatusListener(this);
		
		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		
		
		userListUI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() > 1) {
					String login = userListUI.getSelectedValue();
					MessagePane messagePane = new MessagePane(client, login);
				
					JFrame f = new JFrame("Message to: " + login);
					
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(500, 500);
					f.getContentPane().add(messagePane);
					f.setVisible(true);
				}
			}
		});
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
		
		if(client.connectToServer()) {
			try {
				client.login("user", "pass");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		userPanel.userListModel.addElement("user");
		userPanel.userListModel.addElement("me");

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
