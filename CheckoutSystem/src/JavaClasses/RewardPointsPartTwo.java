import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Second GUI seen when using reward points to pay for part/all of a purchase
 */
public class RewardPointsPartTwo {

	private JFrame frame;
	private JTextField txtPoints;
	private Employee curEmployee;
	private Customer curCustomer;
	private Customer customer;
	private DecimalFormat dec;
	private Order currentOrder;
	private boolean isReturn;
	private double points;
	private double orderTotal;



	/**
	 * Create the application.
	 */
	public RewardPointsPartTwo(Order curOrder) {
		this.isReturn = false;
		this.currentOrder = curOrder;
		initialize(curOrder);
	}

	/**
	 * Create the application.
	 */
	public RewardPointsPartTwo(Order curOrder, Employee curEmployee, Customer curCustomer,boolean isReturn) {
		this.curEmployee = curEmployee;
		this.curCustomer = curCustomer;
		this.currentOrder = curOrder;
		this.isReturn = isReturn;
		initialize(curOrder);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Order curOrder) {

		dec = new DecimalFormat("#.00");
		this.currentOrder = curOrder;

		orderTotal = this.currentOrder.getOrderTotal();
			points = (this.curCustomer.getCustPoints())/100;
		
		
		frame = new JFrame();
		frame.setBounds(480, 177, 739, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRewardPoints = new JLabel("Reward Points");
		lblRewardPoints.setBounds(325, 29, 130, 36);
		frame.getContentPane().add(lblRewardPoints);
		
		JLabel lblCustomer = new JLabel("Customer id: " + this.curCustomer.getCustName());
		lblCustomer.setBounds(106, 94, 214, 16);
		frame.getContentPane().add(lblCustomer);


		JLabel lblPoints = new JLabel("Points (in Dollars): $" + dec.format((this.curCustomer.getCustPoints())/100));
		lblPoints.setBounds(67, 126, 214, 16);
		frame.getContentPane().add(lblPoints);
		
		JLabel lblOrderTotal = new JLabel("Order Total: $ " + dec.format(this.currentOrder.getOrderTotal()));
		lblOrderTotal.setBounds(106, 214, 255, 16);
		frame.getContentPane().add(lblOrderTotal);
		
		JLabel lblAmmountToPay = new JLabel("Amount to pay w/ points:");
		lblAmmountToPay.setBounds(17, 258, 199, 16);
		frame.getContentPane().add(lblAmmountToPay);
		
		txtPoints = new JTextField();
		txtPoints.setText("0.00");
		txtPoints.setBounds(202, 253, 130, 26);
		frame.getContentPane().add(txtPoints);
		txtPoints.setColumns(10);
		
		JButton btnCalculate = new JButton("calculate");
		btnCalculate.setBounds(366, 253, 117, 29);
		frame.getContentPane().add(btnCalculate);
		
	
		
		JLabel lblPointsRemaining = new JLabel("Points remaining: $" + dec.format(points));
		lblPointsRemaining.setBounds(81, 310, 232, 16);
		frame.getContentPane().add(lblPointsRemaining);
		
		JLabel lblOrderTotalRemaining = new JLabel("Order Total Remaining: $" + dec.format(orderTotal));
		lblOrderTotalRemaining.setBounds(45, 347, 274, 16);
		frame.getContentPane().add(lblOrderTotalRemaining);
		
		
		
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	


				
			String ammountWantedString = txtPoints.getText();
			double ammountWanted = Double.parseDouble(ammountWantedString);
			
			if(ammountWanted > points){
				JOptionPane.showMessageDialog(frame, "Not enough points. Enter lower amount");
				txtPoints.setText("0.00");
				lblPointsRemaining.setText("Points remaining: $" + dec.format(points));
				lblOrderTotalRemaining.setText("Order Total Remaining: $" + dec.format(orderTotal));
			return;
			}
			//also need to check to see if more points than needed is used.
			if(ammountWanted > orderTotal){
				JOptionPane.showMessageDialog(frame, "Points exceeds Order total");
				txtPoints.setText("0.00");
				lblPointsRemaining.setText("Points remaining: $" + dec.format(points));
				lblOrderTotalRemaining.setText("Order Total Remaining: $" + dec.format(orderTotal));
				return;
			}
			
			else{
				//calculate remaining points and new total after.
				lblPointsRemaining.setText("points remaining: $" + dec.format((points - ammountWanted)));
				lblOrderTotalRemaining.setText("Order Total Remaining: $"+ dec.format((orderTotal - ammountWanted)));
			}
			
				}
			});
		
		
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(594, 464, 117, 29);
		frame.getContentPane().add(btnGoBack);
		
		
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				this.setVisible(false);
            	RewardPoints rewards = new RewardPoints(currentOrder, curEmployee, isReturn);
            	rewards.setVisible(true);
			
			
				}

			private void setVisible(boolean b) {
				// TODO Auto-generated method stub
				frame.setVisible(b);
			}
		
		});
		
		JButton btnContinue = new JButton("continue");
		btnContinue.setBounds(465, 464, 117, 29);
		frame.getContentPane().add(btnContinue);
		
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				String ammountWantedString = txtPoints.getText();
				double ammountWanted = Double.parseDouble(ammountWantedString);

				double leftOver = orderTotal - ammountWanted;
				//subtract points from customer
				
				double pointsLeft = points - ammountWanted;
							
				if(ammountWanted > points){
					JOptionPane.showMessageDialog(frame, "Not enough points. Enter lower amount");
					txtPoints.setText("0.00");
					lblPointsRemaining.setText("Points remaining: $" + dec.format(points));
					lblOrderTotalRemaining.setText("Order Total Remaining: $" + dec.format(orderTotal));
				return;
				}
				//also need to check to see if more points than needed is used.
				if(ammountWanted > orderTotal){
					JOptionPane.showMessageDialog(frame, "Points exceeds Order total");
					txtPoints.setText("0.00");
					lblPointsRemaining.setText("Points remaining: $" + dec.format(points));
					lblOrderTotalRemaining.setText("Order Total Remaining: $" + dec.format(orderTotal));
					return;
				}
				
				
				
				DBConnection.dbUpdateRecord("customers", "rewardPoints =\"" + pointsLeft*10 + "\"", "customerid = " + curCustomer.getCustName() );
				
				
				
				if(leftOver == 0.0){
	           
				//go to receipt	
					this.setVisible(false);
	            	Customer cust = new Customer(curCustomer.getCustName());
					int custId = cust.writeToDatabase();
					currentOrder.setPaymentMethod("Reward points");
					currentOrder.setCustId(custId);
					currentOrder.writeToDatabase(isReturn);
	            	MainScreen main = new MainScreen(curEmployee);
					Receipt receipt = new Receipt(currentOrder, isReturn);
					main.setVisible(true);
					receipt.setVisible(true);
					frame.dispose();
					
				}
				else{
					//go back to payment method to pay the rest
					currentOrder.setOrderTotal(leftOver);
					Payment paymnt = new Payment(currentOrder, curEmployee, false);
	            	paymnt.setVisible(true);
	            	frame.dispose();
				}
			
				}

			private void setVisible(boolean b) {
				// TODO Auto-generated method stub
				frame.setVisible(b);
			}
			});
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		frame.setVisible(b);
	}
}
