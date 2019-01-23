import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MessagePane extends JPanel implements MessageListener {
	

	private ChatClient client;
	private String login;

	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList<String> messageList = new JList<>(listModel);
	private JTextField inputField = new JTextField();
	
	
	public MessagePane(ChatClient client, String login) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.login = login;
		
		client.addMessageListener(this);
		
		
		
		setLayout(new BorderLayout());
		add(new JScrollPane(messageList), BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		
		
		
		
		inputField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String text = inputField.getText();
					client.msg(login, text);
					
					//dodawanie wiadomosci do wyswieltanej na ekranie
					listModel.addElement("You: " + text);
					
					// wyczyszczenie pola
					inputField.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		});
		
	}


	@Override
	public void onMessage(String fromLogin, String msgBody) {
		// TODO Auto-generated method stub
		String line = fromLogin + ": " + msgBody;
		listModel.addElement(line);
		
	}
}
