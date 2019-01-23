import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {

	JTextField loginField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	private ChatClient client;
	
	public LoginWindow() {
		super("Chat Client");
		
		this.client = new ChatClient("localhost", 4444);
		
		client.connectToServer();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		p.setLayout((new BoxLayout(p, BoxLayout.Y_AXIS)));
		
		p.add(loginField);
		p.add(passwordField);
		p.add(loginButton);
		
		//
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doLogin();
			}

			
		});
		
		
		//
		
		
		getContentPane().add(p, BorderLayout.CENTER);
		pack(); // rozmiar sam sie dostosowuje do 
		
		setVisible(true);
	}
	
	
	private void doLogin() {
		// TODO Auto-generated method stub
		String login = loginField.getText();
		String password = passwordField.getText();
		
		
		try {
			if(client.login(login, password)) {
				
				//ukryj okno logowania 
				setVisible(false);
				
				
				// otworz okno startowe czatu -  z lista uzytkownikow				
				UserPanel userPanel = new UserPanel(client);
				
				
				JFrame frame = new JFrame("Chat Application");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(400, 600);
				frame.getContentPane();
				frame.add(userPanel, BorderLayout.CENTER);
				frame.setVisible(true);
				
				// test			- zmienic dostep do userListModel w UserPanel na private
				userPanel.userListModel.addElement("user");
				userPanel.userListModel.addElement("me");
				
				
			} else {
				JOptionPane.showMessageDialog(this, "Invalid login/password");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}
}
