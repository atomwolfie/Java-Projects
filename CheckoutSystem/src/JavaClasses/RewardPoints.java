import java.awt.EventQueue;

import com.mysql.jdbc.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;


/**
 * First GUI seen when using reward points to pay for part/all of a purchase
 */
public class RewardPoints{

	private JFrame frame;
	private JTextField txtPhone;
	private JTextField txtName;
	private Employee curEmployee;
	private Customer curCustomerGuy;
	private Order currentOrder;
	private boolean isReturn;


	/**
	 * Create the application.
	 */
	public RewardPoints(Order curOrder) {
		this.isReturn = false;
		initialize(curOrder);
	}

	/**
	 * Create the application.
	 */
	public RewardPoints(Order curOrder, Employee curEmployee, boolean isReturn) {
		this.curEmployee = curEmployee;
		this.isReturn = isReturn;
		this.currentOrder = curOrder;
		initialize(curOrder);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Order curOrder) {
		this.currentOrder = curOrder;

		System.out.println("Order total: " + this.currentOrder.getOrderTotal());
		
		frame = new JFrame();
		frame.setBounds(480, 177, 739, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(601, 477, 117, 29);
		frame.getContentPane().add(btnGoBack);

		txtPhone = new JTextField();
		txtPhone.setText("");
		txtPhone.setBounds(172, 219, 130, 26);
		frame.getContentPane().add(txtPhone);
		txtPhone.setColumns(10);
		
		
		
		
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 149, 253, 252);
		frame.getContentPane().add(scrollPane);
					
		//get employees into vector
		Vector<String> custNames = new Vector<String>(10,2);
		
		try{
			 Connection myConn2 = (Connection) DriverManager.getConnection(DBConnection.dbUrl, DBConnection.dbUser, DBConnection.dbPassword);
      		java.sql.Statement myStmt = myConn2.createStatement();
     		
     		ResultSet myRs = myStmt.executeQuery("select * from customers");
     		
     		while(myRs.next()){
     			custNames.add(myRs.getString("customername"));
     		}
     		
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//sort customers
		Collections.sort(custNames);
		
		//put them into the jlist
		JList list = new JList(custNames);
		scrollPane.setViewportView(list);
		
		JButton btnSelectEmployee = new JButton("Or search by phone number:");
		btnSelectEmployee.setBounds(343, 411, 151, 29);
		//frame.getContentPane().add(btnSelectEmployee);
		
		btnSelectEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText((String) list.getSelectedValue());
				}
			});
		
		JLabel lblSelectCustomer = new JLabel("Select Customer");
		lblSelectCustomer.setBounds(312, 76, 130, 16);
		frame.getContentPane().add(lblSelectCustomer);
		
		
		JButton btnContinue = new JButton("continue");
		btnContinue.setBounds(481, 477, 117, 29);
		frame.getContentPane().add(btnContinue);
		
		JLabel lblOrSearchBy = new JLabel("Or search by phone # :");
		lblOrSearchBy.setBounds(25, 224, 164, 16);
		frame.getContentPane().add(lblOrSearchBy);
		
		JButton btnSearch = new JButton("search");
		btnSearch.setBounds(202, 257, 117, 29);
		frame.getContentPane().add(btnSearch);
		
		JLabel lblSwipeCard = new JLabel("Swipe card");
		lblSwipeCard.setBounds(25, 205, 104, 16);
		frame.getContentPane().add(lblSwipeCard);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String phone = txtPhone.getText();
				
				try{

					ResultSet myRs = DBConnection.dbSelectAllFromTableWhere("customers", "phonenumber=\"" + phone + "\"");

					myRs.next();

					Customer curCustomer = new Customer(myRs.getString(1));
					curCustomerGuy = curCustomer;
				}
				catch(Exception e1){
					e1.printStackTrace();
					//no customer found
					JOptionPane.showMessageDialog(frame, "no customer found");
					txtPhone.setText("");
				}
				
				
				this.setVisible(false);
				RewardPointsPartTwo reward = new RewardPointsPartTwo(currentOrder, curEmployee, curCustomerGuy,isReturn);
				reward.setVisible(true);
	            	frame.dispose();		
				}

				public void setVisible(boolean b) {
					frame.setVisible(b);
				}
			});
				
				
			
		
		
		
		
				btnContinue.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						if(txtPhone.equals("")){
							System.out.println("do the normal thing");
						}
						
						
						txtPhone.setText((String) list.getSelectedValue());
						String name = txtPhone.getText();
						System.out.println(name);
												
						try{

							ResultSet myRs = DBConnection.dbSelectAllFromTableWhere("customers", "customername=\"" + name + "\"");

							myRs.next();

							Customer curCustomer = new Customer(myRs.getString(1));
							curCustomerGuy = curCustomer;
						}
						catch(Exception e1){
							System.out.println("no customer found");
							e1.printStackTrace();
						}
						
						
						this.setVisible(false);
						RewardPointsPartTwo reward = new RewardPointsPartTwo(currentOrder, curEmployee, curCustomerGuy,isReturn);
						reward.setVisible(true);
			            	frame.dispose();		
						}

						public void setVisible(boolean b) {
							frame.setVisible(b);
						}
					});
		
		

		btnGoBack.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
			this.setVisible(false);
			Payment payment = new Payment(currentOrder, curEmployee, isReturn);
			payment.setVisible(true);
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
