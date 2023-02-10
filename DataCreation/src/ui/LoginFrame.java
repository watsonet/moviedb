package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
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
		DatabaseConnectionService dbcs = null;
		try (InputStream input = new FileInputStream("./DataCreation/src/mediadb.properties")) {
			Properties properties = new Properties();
			properties.load(input);
			dbcs = new DatabaseConnectionService(properties.getProperty("serverName"),
					properties.getProperty("databaseName"));
			dbcs.connect(properties.getProperty("serverUsername"), properties.getProperty("serverPassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		JFrame loginFrame = new JFrame();

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

		JLabel loginLabel = new JLabel("Login to MediaDB");

		JPanel userPanel = new JPanel();
		createUsername(userPanel);

		JPanel passPanel = new JPanel();
		createPassword(passPanel);

		JButton validation = new JButton("Login");
		JButton creation = new JButton("Create Account");
		validation.putClientProperty("dbcs", dbcs);
		creation.putClientProperty("dbcs", dbcs);
		validation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String user = getUsername();
				String pass = getPassword();
				DatabaseConnectionService dbcs = ((DatabaseConnectionService) ((JButton) e.getSource())
						.getClientProperty("dbcs"));
				int returnValue = 1;
				if (user == null || pass == null) {
					loginLabel.setText("Username or password cannot be empty");
				} else {
					loginLabel.setText("Connecting...");
					try {
						CallableStatement cs = dbcs.getConnection().prepareCall("{? = call checkUser(?, ?)}");
						cs.setString(2, user);
						cs.setString(3, pass);
						cs.registerOutParameter(1, Types.INTEGER);
						cs.execute();
						returnValue = cs.getInt(1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					if (returnValue > 0) {
						loginLabel.setText("Failed to connect, try again.");
						return;
					}

					loginFrame.dispose();
					
					Main.currentUser = getUsername();

					MainFrame mainFrame = new MainFrame(dbcs);
					mainFrame.createFrame();
				}
			}
		});
		creation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = getUsername();
				String pass = getPassword();
				DatabaseConnectionService dbcs = ((DatabaseConnectionService) ((JButton) e.getSource())
						.getClientProperty("dbcs"));
				int returnValue = -1;
				if (pass != null && pass.length() > 20) {
					loginLabel.setText("Password must be less than 20 characters!");
				} else {
					try {
						CallableStatement cs = dbcs.getConnection().prepareCall("{? = call addUser(?, ?)}");
						cs.setString(2, user);
						cs.setString(3, pass);
						cs.registerOutParameter(1, Types.INTEGER);
						cs.execute();
						returnValue = cs.getInt(1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (returnValue == 0) {
						loginFrame.dispose();

						MainFrame mainFrame = new MainFrame(dbcs);
						mainFrame.createFrame();
					}
					if (returnValue == 1) {
						loginLabel.setText("Username cannot be null");
					}
					if (returnValue == 2) {
						loginLabel.setText("Password cannot be null");
					}
					if (returnValue == 3) {
						loginLabel.setText("Username is already in use");
					}
					if (returnValue > 3 || returnValue < 0) {
						loginLabel.setText("Failed to connect, try again.");
					}
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
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(validation);
		panel.add(creation);
		loginPanel.add(panel);
		loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentX(JLabel.CENTER);
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