package ui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame {
	private String username;
	private String password;
	private int width = 400;
	private int height = 200;

	public void createFrame() {
		JFrame loginFrame = new JFrame();

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

		JLabel loginLabel = new JLabel("Login to MediaDB");

		JPanel userPanel = new JPanel();
		createUsername(userPanel);

		JPanel passPanel = new JPanel();
		createPassword(passPanel);

		JButton validation = new JButton("Login");
		validation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String user = getUsername();
				String pass = getPassword();

				if (user == null || pass == null) {
					loginLabel.setText("Username or password cannot be empty");
				} else {
					loginLabel.setText("Connecting...");
					DatabaseConnection connection = new DatabaseConnection(user, pass);

					
					if (!connection.connect()) {
						loginLabel.setText("Failed to connect, try again.");
						return;
					}
					
					loginFrame.dispose();

					MainFrame mainFrame = new MainFrame();
					mainFrame.createFrame(connection.getConnection());
				}
			}
		});

		loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		loginPanel.add(loginLabel);
		loginPanel.add(Box.createRigidArea(new Dimension(0, height / 10)));
		loginPanel.add(userPanel);
		loginPanel.add(Box.createRigidArea(new Dimension(0, height / 10)));
		loginPanel.add(passPanel);
		loginPanel.add(Box.createRigidArea(new Dimension(0, height / 10)));
		loginPanel.add(validation);
		loginPanel.add(Box.createRigidArea(new Dimension(0, height / 10)));
		
		loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		loginFrame.add(loginPanel);

		loginFrame.setVisible(true);
		loginFrame.setSize(width, height);
		loginFrame.setResizable(true);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void createUsername(JPanel panel) {
		JLabel userLabel = new JLabel("Enter Username:");

		JTextField userField = new JTextField();

		userField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setUsername(userField.getText());
			}
		});

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(userLabel);
		panel.add(userField);
	}

	private void setUsername(String username) {
		this.username = username;
	}

	private void createPassword(JPanel panel) {
		JLabel passLabel = new JLabel("Enter Password:");

		JPasswordField passField = new JPasswordField();
		passField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPassword(passField.getPassword());
			}
		});

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(passLabel);
		panel.add(passField);
	}

	private void setPassword(char[] password) {
		String pass = new String(password);
		this.password = pass;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
