import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MessagePane extends JPanel {
	

	private ChatClient client;
	private String login;

	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> messageList = new JList<>(listModel);
	private JTextField inputField = new JTextField();
	
	
	public MessagePane(ChatClient client, String login) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.login = login;
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
	}
}
