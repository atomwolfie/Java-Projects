import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import com.mysql.jdbc.Connection;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;


/**
 * GUI where the user inputs their username and password to log in
 */
public class employeeLogin {

	private JFrame frame;
	private JTextField txtName;
	private JTextField textField;


	/**
	 * Create the application.
	 */
	public employeeLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(480, 177, 739, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
//		
		
		txtName = new JTextField();
		txtName.setText("");
		txtName.setBounds(360, 174, 130, 26);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		
		JLabel lblProductName = new JLabel("username:");
		lblProductName.setBounds(278, 179, 82, 16);
		frame.getContentPane().add(lblProductName);

		
		JLabel lblInventory = new JLabel("Employee Login");
		lblInventory.setBounds(310, 76, 138, 16);
		frame.getContentPane().add(lblInventory);
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(601, 477, 117, 29);
		frame.getContentPane().add(btnGoBack);
		
		textField = new JPasswordField();
		textField.setText("");
		textField.setBounds(360, 220, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(281, 225, 92, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setBounds(382, 270, 117, 29);
		btnLogin.setBackground(new Color(95, 186, 125));
		frame.getContentPane().add(btnLogin);

		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String passEntered = textField.getText();
				System.out.println("pass entered: " + passEntered);
				//compare passwords
				String realPass;
				String realUserName;
				String userNameEntered = txtName.getText();
				System.out.println("employee username: " + userNameEntered);
				Employee employee = null;
				//get real password

				try{

					ResultSet myRs = DBConnection.dbSelectAllFromTableWhere("employees", "employeeusername=\"" + userNameEntered + "\"");
					myRs.next();
						
					realPass = myRs.getString("employeepassword");
					realUserName = myRs.getString("employeeusername");
					 employee = new Employee(myRs.getInt("employeeid"));

				}
				catch(Exception e1){
				System.out.println("Hitting exception here");	
				e1.printStackTrace();
					realPass = "nothing";
				}
				//if correct go to main screen
				if(passEntered.equals(realPass)){

					this.setVisible(false);
					MainScreen main = new MainScreen(employee);
					main.setVisible(true);
					frame.dispose();

				}
				else{
					JOptionPane.showMessageDialog(frame, "password incorrect.");
					textField.setText("");
					//if not little window saying incorrect password
				}
			}

			private void setVisible(boolean b) {
				frame.setVisible(b);
			}
		});

		
		

		btnGoBack.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
			this.setVisible(false);
            	LoginScreen myLogin = new LoginScreen();
            	myLogin.setVisible(true);
            	frame.dispose();		
			}

			public void setVisible(boolean b) {
				frame.setVisible(b);
			}
		});
	
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);

	}
}
