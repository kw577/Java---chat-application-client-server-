import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class LoginWindow extends JFrame {

	JTextField loginField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	
    private JLabel lbUsername;
    private JLabel lbPassword;
	
	JButton loginButton = new JButton("Login");
	private ChatClient client;
	
	
	
	public LoginWindow() {
		super("Chat Client");
		
		this.client = new ChatClient("localhost", 4444);
		
		client.connectToServer();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel(new GridBagLayout());
		
		
		
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		
		
		
		
		
		
		
		lbUsername = new JLabel("Nick: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        p.add(lbUsername, cs);
 
        loginField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        p.add(loginField, cs);
		
		
		
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        p.add(lbPassword, cs);
		
		
        passwordField = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        p.add(passwordField, cs);
        p.setBorder(new LineBorder(Color.GRAY));
		
		
        JPanel bp = new JPanel();
		
		
		
		bp.add(loginButton);
		
		
		
		getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
       // setLocationRelativeTo(parent);
		
		
		////// Formatowanie okna logowania
		
		
		
		
		
		
		//
		//
        //loginButton.setBackground(new Color(0, 128, 64));
        
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
				//userPanel.userListModel.addElement("user");
				//userPanel.userListModel.addElement("me");
				System.out.println("ilosc kontaktow " + client.listOfContacts.size());
				
				
				for(String person : client.listOfContacts) {
					userPanel.userListModel.addElement(person);
				}
				
				
				
				//userPanel.userListModel.addElement(client.listOfContacts.get(1));
				
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
