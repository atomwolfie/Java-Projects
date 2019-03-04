import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * GUI for paying with cash
 */

public class Cash {

	private JFrame frame;
	private JTextField textField;
	private JTextField rewardField;
	private Order currentOrder;
	private Employee curEmployee;
	private boolean isReturn;
	private double curPoints;
	private int custId;


	/**
	 * Create the application.
	 */
	public Cash(Order curOrder) {
		this.isReturn = false;
		initialize(curOrder);
	}

	/**
	 * Create the application.
	 */
	public Cash(Order curOrder, Employee curEmployee) {
		this.isReturn = false;
		this.curEmployee = curEmployee;
		initialize(curOrder);
	}

	/**
	 * Create the application.
	 */
	public Cash(Order curOrder, Employee curEmployee, boolean isReturn) {
		this.curEmployee = curEmployee;
		this.isReturn = isReturn;
		initialize(curOrder);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Order curOrder) {
		this.currentOrder = curOrder;
		frame = new JFrame();
		 frame.setBounds(400, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCash = new JLabel("Cash");
		if (this.isReturn) {
			lblCash.setBounds(425, 210, 268, 16);
		}
		else {
			lblCash.setBounds(425, 160, 268, 16);
		}
		frame.getContentPane().add(lblCash);

		if (!isReturn) {
			JLabel rewardsMember = new JLabel("Enter phone # or swipe reward card (optional): ");
			rewardsMember.setBounds(50, 330, 300, 16);
			frame.getContentPane().add(rewardsMember);

			rewardField = new JTextField();
			rewardField.setText("");
			rewardField.setBounds(380, 325, 130, 26);

			frame.getContentPane().add(rewardField);
		}
		
		
		JButton btnPrintReceipt = new JButton("Print Receipt");
		btnPrintReceipt.setBounds(370, 350, 150, 67);
		btnPrintReceipt.setBackground(new Color(95,186,125));
		if (!this.isReturn) {
			btnPrintReceipt.setEnabled(false);
			btnPrintReceipt.setBounds(370, 400, 150, 67);
		}
		frame.getContentPane().add(btnPrintReceipt);
		
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(380, 240, 130, 26);
		if (!this.isReturn) {
			frame.getContentPane().add(textField);
		}
		textField.setColumns(10);

		JLabel lblEnterAmmount = new JLabel("Enter amount:");
		lblEnterAmmount.setBounds(280, 245, 150, 16);
		if (!this.isReturn) {
			frame.getContentPane().add(lblEnterAmmount);
		}

		Double tax = this.currentOrder.getOrderTotal() * .03;

		DecimalFormat dec = new DecimalFormat("#.00");
		JLabel lblTotal = new JLabel("Total: $" + dec.format(this.currentOrder.getOrderTotal() + tax));
		if (this.isReturn) {
			tax = this.currentOrder.getReturnTotal() * 0.03;
			lblTotal.setText("Total: -$" + dec.format(-1 * (this.currentOrder.getReturnTotal() + tax)));
			lblTotal.setBounds(380, 265, 150, 16);
		}
		else {
			lblTotal.setBounds(380, 185, 150, 16);
		}
		frame.getContentPane().add(lblTotal);
		
		JLabel lblChange = new JLabel("Change:");
		lblChange.setBounds(280, 285, 150, 16);
		if (!this.isReturn) {
			frame.getContentPane().add(lblChange);
		}

		JButton btnEnter = new JButton("calculate");
		btnEnter.setBounds(520, 240, 91, 29);
		if (!this.isReturn) {
			frame.getContentPane().add(btnEnter);
		}
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.setBounds(700, 610, 117, 29);
		frame.getContentPane().add(btnGoBack);
		
		ActionListener buttonListener = new ActionListener() {

	        //we have to define this method in order for an Action Listener to work
	        public void actionPerformed(ActionEvent e) { //'e' is the Action Event which is a button being clicked in our case

	            if (e.getSource() == btnGoBack) { //return to checkout screen

	            	this.setVisible(false);
	            	Payment payment = new Payment(currentOrder, curEmployee, isReturn);
	            	payment.setVisible(true);
	            } 	

	            if (e.getSource() == btnEnter) {
	            	double changeDue = Double.parseDouble(textField.getText()) - (currentOrder.getOrderTotal() + currentOrder.getOrderTotal() * .03);
					lblChange.setText("Change: $" + dec.format(changeDue ));
					if (changeDue >= 0) {
						btnPrintReceipt.setEnabled(true);
					}
					else {
						btnPrintReceipt.setEnabled(false);
					}
				}

	            if (e.getSource() == btnPrintReceipt) { //go to receipt screen, also brings up main screen to start again

					String phoneNum = "";
					int custId = 0;
					if (!isReturn) {
						phoneNum = rewardField.getText();
					}
					else {
						custId = currentOrder.getCustId();
					}

	            	try{
						ResultSet myRs;
						if (!isReturn) {
							myRs = DBConnection.dbSelectAllFromTableWhere("customers", "phonenumber=\"" + phoneNum + "\"");
						}
						else {
							myRs = DBConnection.dbSelectAllFromTableWhere("customers", "customerid=" + custId);
						} 
						//gets the current points
						myRs.next();
						custId = myRs.getInt(1);
						 curPoints = myRs.getDouble(5);
						 
						System.out.println("cur points: " + curPoints);
					}
					catch(Exception e1){
						e1.printStackTrace();
						System.out.println("finding customer not working");
						 curPoints = 0.0;
					}
					//updates to new points
					double newPoints = currentOrder.getOrderTotal() + curPoints;
					if (isReturn) {
						newPoints = currentOrder.getReturnTotal() + curPoints;
					}
					
					System.out.println("goint to add " + newPoints + " to customer w/ phone num: " + phoneNum);
					DBConnection.dbUpdateRecord("customers", "rewardPoints =\"" + newPoints  + "\"", "phonenumber = " + phoneNum);

	            	
	            	this.setVisible(false);
	            	currentOrder.setCustId(custId);
	            	currentOrder.setPaymentMethod("Cash");

	            	if(!isReturn) {
						currentOrder.writeToDatabase(isReturn);
					}
					else {
	            		curOrder.updateOrder();
					}

					frame.dispose();
					//Write new data to mysql db

	            	MainScreen main = new MainScreen(curEmployee);
					Receipt receipt = new Receipt(currentOrder, isReturn);
					
					main.setVisible(true);
					receipt.setVisible(true);
	            }
	        }

			private void setVisible(boolean b) {
				frame.setVisible(b);
			}
	    };

		btnGoBack.addActionListener(buttonListener);
		btnEnter.addActionListener(buttonListener);
		btnPrintReceipt.addActionListener(buttonListener);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
