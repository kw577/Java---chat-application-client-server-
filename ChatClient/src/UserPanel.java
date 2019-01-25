import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UserPanel extends JPanel implements UserStatusListener {
	
	private final ChatClient client;
	private JList<String> userListUI;
	public DefaultListModel<String> userListModel;
	
	JButton logoutButton = new JButton("Logout");
	
	public UserPanel(ChatClient client) {
		
		this.client = client;
		this.client.addUserStatusListener(this);
		
		userListModel = new DefaultListModel<>();
		userListUI = new JList<>(userListModel);
		setLayout(new BorderLayout());
		add(logoutButton,BorderLayout.SOUTH);
		add(new JScrollPane(userListUI), BorderLayout.CENTER);
		
		//userListUI.setFont(new Font("Verdana", Font.PLAIN, 20));
		
		
		
		userListUI.setCellRenderer(new ContactListRenderer());
		
		userListUI.setFixedCellHeight(35);
		
		
		userListUI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() > 1) {
					String login = userListUI.getSelectedValue();
					if(login.contains("_")) {
						login = login.replaceAll("_","");
					}
					MessagePane messagePane = new MessagePane(client, login);
					
					
					
					JFrame f = new JFrame("Message to: " + login);
					
					
					
					
					
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.setSize(400, 450);
					f.getContentPane().add(messagePane);
					f.setVisible(true);
				}
			}
		});
		
		
		userListUI.addMouseMotionListener(new MouseAdapter() {
			
		});
		
		
		
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					client.closeConnection();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			
		});
		
		
	}

	
	
	private void logout() {
			
		try {
			this.client.closeConnection();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	





	@Override
	public void online(String login) {
		// TODO Auto-generated method stub
		login = login.replaceAll("\\s+","");
		
		if(userListModel.contains(login)) {
			System.out.println("\nW kontaktach jest osoba o takim nicku");
			
			for(int i=0; i<this.userListModel.size(); i++) {
				if(this.userListModel.get(i).equals(login)) {
					this.userListModel.set(i, this.userListModel.get(i)+"_");
				}
				
				
			}
			
			
		
			
			
			
		}
		else System.out.println("\nW kontaktach nie ma osoby o takim nicku");
		
		//userListModel.addElement(login);
		
		
	}




	@Override
	public void offline(String login) {
		// TODO Auto-generated method stub
		
		
		
		
		
		login = login.replaceAll("\\s+","");
		String login_temp = login;
		login +="_";
		
		if(userListModel.contains(login)) {
			System.out.println("\nW kontaktach jest osoba o takim nicku (sprawdzanie wylogowania)");
			
			for(int i=0; i<this.userListModel.size(); i++) {
				if(this.userListModel.get(i).equals(login)) {
					this.userListModel.set(i, login_temp);
				}
				
				
			}
			
			
			
			
		}
		else System.out.println("\nW kontaktach nie ma osoby o takim nicku");
		
		//userListModel.addElement(login);
		
		
		
		
	}
	
}
