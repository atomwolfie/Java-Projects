import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Main screen GUI once logged in
 */
public class MainScreen {

	private JFrame frame;
	private JLabel imageLabel;
	private Employee curEmployee;


	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}


	public MainScreen(Employee empl) {
		this.curEmployee = empl;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		JLabel lblNewLabel = new JLabel("Store Management System");
		lblNewLabel.setBounds(392, 160, 268, 16);
		frame.getContentPane().add(lblNewLabel);

		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setBounds(500, 251, 250, 190);
		frame.getContentPane().add(btnCheckout);

		JButton btnInventory = new JButton("Inventory");
		btnInventory.setBounds(190, 251, 250, 190);

		JButton btnReturnItem = new JButton("Return Products");
		btnReturnItem.setBounds(190, 251, 250, 190);

		JButton btnMngEmployees = new JButton("Manage Employees");
		btnMngEmployees.setBounds(340, 521, 250, 50);

		JButton btnReport = new JButton("Report");
		btnReport.setBounds(340, 581, 250, 50);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(10,160,100,25);

		if (this.curEmployee == null) {
			frame.getContentPane().add(btnInventory);
		}
		else {
			if (this.curEmployee.getIsManager()) {
				frame.getContentPane().add(btnInventory);
				frame.getContentPane().add(btnMngEmployees);
				frame.getContentPane().add(btnReport);
				btnReturnItem.setBounds(340, 455, 250, 50);
				frame.getContentPane().add(btnReturnItem);
			}
			else {
				frame.getContentPane().add(btnReturnItem);
			}
			frame.getContentPane().add(btnLogout);

			// Load and display employee picture
			int labelWidth = 100;
			int labelHeight = 150;
			ImageIcon image = new ImageIcon(curEmployee.loadEmployeePic().getScaledInstance(labelWidth, labelHeight,
					Image.SCALE_SMOOTH));
			imageLabel = new JLabel(image);
			imageLabel.setBounds(10,10,labelWidth,labelHeight);
			frame.getContentPane().add(imageLabel);
			imageLabel.setVisible(true);
		}

		ActionListener buttonListener = new ActionListener() {

	        //we have to define this method in order for an Action Listener to work
	        public void actionPerformed(ActionEvent e) { //'e' is the Action Event which is a button being clicked in our case

	            if (e.getSource() == btnCheckout) { //check to see if the source is the checkout

	            	this.setVisible(false);
	                Checkout check = new Checkout(curEmployee);
	                check.setVisible(true);
					frame.dispose();

	            } else if (e.getSource() == btnInventory) { //check to see if the source is the inventory
	            	
	            	this.setVisible(false);
	                Inventory inv = new Inventory(curEmployee);
	                inv.setVisible(true);
	                frame.dispose();
	            }
				else if (e.getSource() == btnLogout) {
	            	this.setVisible(false);
	            	LoginScreen login = new LoginScreen();
	            	login.setVisible(true);
	            	frame.dispose();
				}
				else if (e.getSource() == btnMngEmployees) {
					this.setVisible(false);
					EditEmployee mngEmpl = new EditEmployee(curEmployee);
					mngEmpl.setVisible(true);
					frame.dispose();
				}
				else if (e.getSource() == btnReturnItem) {
					this.setVisible(false);
					ReturnProducts retItem = new ReturnProducts(curEmployee);
					retItem.setVisible(true);
					frame.dispose();
				}
				else if (e.getSource() == btnReport) {
					this.setVisible(false);
					Report report = new Report(curEmployee);
					report.setVisible(true);
					frame.dispose();
				}
	        }

			private void setVisible(boolean b) {
				frame.setVisible(b);
			}
	    };
	    
		btnInventory.addActionListener(buttonListener);
		btnCheckout.addActionListener(buttonListener);
		btnLogout.addActionListener(buttonListener);
		btnMngEmployees.addActionListener(buttonListener);
		btnReport.addActionListener(buttonListener);
		btnReturnItem.addActionListener(buttonListener);
		if (imageLabel != null) {
			imageLabel.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					this.setVisible(false);
					ChangeEmplInfo changePic = new ChangeEmplInfo(curEmployee);
					changePic.setVisible(true);
					frame.dispose();
				}

				private void setVisible(boolean b) { frame.setVisible(false); }

				@Override
				public void mousePressed(MouseEvent e) { }
				@Override
				public void mouseReleased(MouseEvent e) { }
				@Override
				public void mouseEntered(MouseEvent e) { }
				@Override
				public void mouseExited(MouseEvent e) { }
			});
		}
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}