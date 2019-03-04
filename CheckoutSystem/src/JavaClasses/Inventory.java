import java.awt.EventQueue;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;


/**
 * GUI to display the store inventory, only available to Managers
 */
public class Inventory {

	private JFrame frame;
	private JTable table;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtProvider;
	private JTextField txtType;
	private JTextField txtInStock;
	private JTextField txtDiscount;
	private Employee curEmployee;
	private String curId;


	/**
	 * Create the application.
	 */
	public Inventory() {
		initialize();
	}

	public Inventory(Employee curEmployee) {
		this.curEmployee = curEmployee;
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(292, 139, 576, 382);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		txtId = new JTextField();
		txtId.setText("id");
		txtId.setBounds(100, 187, 130, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtName = new JTextField();
		txtName.setText("name");
		txtName.setBounds(100, 225, 130, 26);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setText("price");
		txtPrice.setBounds(100, 265, 130, 26);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		
		txtProvider = new JTextField();
		txtProvider.setText("Provider");
		txtProvider.setBounds(100, 303, 130, 26);
		frame.getContentPane().add(txtProvider);
		txtProvider.setColumns(10);
		
		txtType = new JTextField();
		txtType.setText("Type");
		txtType.setBounds(100, 340, 130, 26);
		frame.getContentPane().add(txtType);
		txtType.setColumns(10);
		
		txtInStock = new JTextField();
		txtInStock.setText("# In Stock");
		txtInStock.setBounds(100, 378, 130, 26);
		frame.getContentPane().add(txtInStock);
		txtInStock.setColumns(10);
		
		txtDiscount = new JTextField();
		txtDiscount.setText("0 or none for no discount");
		txtDiscount.setBounds(100, 419, 130, 26);
		frame.getContentPane().add(txtDiscount);
		txtDiscount.setColumns(10);
		
		JLabel lblProductName = new JLabel("Name:");
		lblProductName.setBounds(47, 230, 82, 16);
		frame.getContentPane().add(lblProductName);
		
		JLabel lblEnterId = new JLabel("Id:");
		lblEnterId.setBounds(68, 192, 61, 16);
		frame.getContentPane().add(lblEnterId);
		
		JLabel lblEnterPrice = new JLabel("Price:");
		lblEnterPrice.setBounds(52, 270, 82, 16);
		frame.getContentPane().add(lblEnterPrice);
		
		JLabel lblEnterProvider = new JLabel("Provider:");
		lblEnterProvider.setBounds(37, 308, 92, 16);
		frame.getContentPane().add(lblEnterProvider);
		
		JLabel lblEnterType = new JLabel("Type:");
		lblEnterType.setBounds(57, 345, 72, 16);
		frame.getContentPane().add(lblEnterType);
		
		JLabel lblEnter = new JLabel("# in Stock:");
		lblEnter.setBounds(24, 383, 92, 16);
		frame.getContentPane().add(lblEnter);
		
		JLabel lblDiscount = new JLabel("Discount:");
		lblDiscount.setToolTipText("Enter 0 or none if there is no discount");
		lblDiscount.setBounds(24, 423, 92, 16);
		frame.getContentPane().add(lblDiscount);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(25, 466, 111, 29);
		frame.getContentPane().add(btnAddItem);

		
		JLabel lblInventory = new JLabel("Inventory");
		lblInventory.setBounds(406, 72, 138, 16);
		frame.getContentPane().add(lblInventory);
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(700, 610, 117, 29);
		frame.getContentPane().add(btnGoBack);
		
		btnGoBack.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				System.out.println("employee getting passed to MainSreen" + curEmployee);
				this.setVisible(false);
				MainScreen main = new MainScreen(curEmployee);
				main.setVisible(true);
				frame.dispose();
			}

			public void setVisible(boolean b) {
				frame.setVisible(b);
			}
		});

		JButton btnEditSelectedItem = new JButton("edit selected item");
		btnEditSelectedItem.setBounds(302, 526, 181, 29);
		frame.getContentPane().add(btnEditSelectedItem);
			
		btnEditSelectedItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//get row
				int row = table.getSelectedRow();
				TableModel model = table.getModel();
				
				txtId.setText(model.getValueAt(row,0).toString());
				
				txtName.setText(model.getValueAt(row,1).toString());
				txtPrice.setText(model.getValueAt(row,2).toString());
				txtProvider.setText(model.getValueAt(row,3).toString());
				txtType.setText(model.getValueAt(row,4).toString());
				txtInStock.setText(model.getValueAt(row,5).toString());
				
				if(model.getValueAt(row,6).toString().equals(0.0)){
					txtDiscount.setText("none");
				}
				else{
					txtDiscount.setText(model.getValueAt(row,6).toString());
				}
				
				
				try{
					ResultSet myRs = DBConnection.dbSelectAllFromTable("products");
					myRs.next();

					curId = model.getValueAt(row,0).toString();
				}
				catch(Exception e2){
					 e2.printStackTrace();
				}
			}
		});
		
		JButton updateNewBtn = new JButton("Update Item");
		updateNewBtn.setBounds(148, 466, 122, 29);
		frame.getContentPane().add(updateNewBtn);

		updateNewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			String productName = txtName.getText();
				
			String price = txtPrice.getText();
    		double  productPrice = Double.parseDouble(price);
			
    		String discountString = txtDiscount.getText();
			double discount = Double.parseDouble(discountString);
			
			if(discountString.equals("none") || discountString.equals("None")){
				discount = 0.0;
			}
			
			if(discount > productPrice){
				JOptionPane.showMessageDialog(frame, "item not updated, discount price greater than product price");
				return;
			}
			
				//EDIT ITEM IN DATABASE
        		try {
					Connection myCon = (Connection) DriverManager.getConnection(DBConnection.dbUrl, DBConnection.dbUser, DBConnection.dbPassword);
					Statement myStmt = (Statement) myCon.createStatement();

        			if(txtPrice.getText().isEmpty()){
        				System.out.println("price not updated");
        			}
        			else{
    	        		DBConnection.dbUpdateRecord("products", "productprice =\"" + txtPrice.getText() + "\"", "productid = " + curId );
        			}
        		      			
					if(txtId.getText().isEmpty()){
						System.out.println("id not updated");
					}
					else{
						DBConnection.dbUpdateRecord("products", "productid =\"" + txtId.getText() + "\"", "productid = " + curId );
					}
        		
					if(txtName.getText().isEmpty()){
						System.out.println("name not updated");
					}
					else{
						DBConnection.dbUpdateRecord("products", "productname =\"" + productName + "\"", "productid = " + curId );
					}

					if(txtType.getText().isEmpty()){
						System.out.println("type not updated");
					}
					else{
						DBConnection.dbUpdateRecord("products", "type =\"" + txtType.getText() + "\"", "productid = " + curId );
					}
        		
					if(txtProvider.getText().isEmpty()){
						System.out.println("provider not updated");
					}
					else{
						DBConnection.dbUpdateRecord("products", "provider =\"" + txtProvider.getText() + "\"", "productid = " + curId );
					}

        			if(txtInStock.getText().isEmpty()){
        				System.out.println("inStock not updated");
        			}
					else{
	        			DBConnection.dbUpdateRecord("products", "inStock =\"" + txtInStock.getText() + "\"", "productid = " + curId );
       		 		}
        			if(txtDiscount.getText().isEmpty()){
        				System.out.println("Discount not updated");
        			}
        			if(txtDiscount.getText().equals("none")){
        				DBConnection.dbUpdateRecord("products", "discountPrice =\"" + 0.0 + "\"", "productid = " + curId );
        			}
					else{
	        			
						DBConnection.dbUpdateRecord("products", "discountPrice =\"" + txtDiscount.getText() + "\"", "productid = " + curId );
       		 		}
        		}
        		catch (Exception e1){
        		    e1.printStackTrace();
        		}
			
				Inventory myInv = new Inventory(curEmployee);
	         	myInv.setVisible(true);
	         	frame.dispose();

			}
		});

		try {
			Connection con = (Connection) DriverManager.getConnection(DBConnection.dbUrl, DBConnection.dbUser, DBConnection.dbPassword);
			String query  = "select * from products";

			java.sql.PreparedStatement  pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			table.setModel(DbUtils.resultSetToTableModel(rs));		
			table.getColumnModel().getColumn(0).setHeaderValue("ID");
			table.getColumnModel().getColumn(1).setHeaderValue("Name");
			table.getColumnModel().getColumn(2).setHeaderValue("Price");
			table.getColumnModel().getColumn(3).setHeaderValue("Provider");
			table.getColumnModel().getColumn(4).setHeaderValue("Type");
			table.getColumnModel().getColumn(5).setHeaderValue("Stock");
		}
	
		catch (Exception e1){
		    System.out.println("NOT WORKING");
			e1.printStackTrace();
		}
		
		JButton btnDelete = new JButton("delete selected item");
		btnDelete.setBounds(492, 526, 181, 29);
		frame.getContentPane().add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				TableModel model = table.getModel();
				 txtName.setText(model.getValueAt(row,1).toString());
				 String Name = txtName.getText();
				txtName.setText("name");

				 try {
				 	Connection myConn2 = (Connection) DriverManager.getConnection(DBConnection.dbUrl, DBConnection.dbUser, DBConnection.dbPassword);
					// 2. Create a statement
					java.sql.Statement myStmt = myConn2.createStatement();
					// 3. Execute SQL query
					String sql = "delete from products where productname = ?";

					PreparedStatement preparedStmt = (PreparedStatement) myConn2.prepareStatement(sql);

					preparedStmt.setString(1, Name);
					preparedStmt.execute();
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
				Inventory myInv = new Inventory(curEmployee);
				myInv.setVisible(true);
				frame.dispose();
			}
		});


		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				String stringId = txtId.getText();
        		double id = Double.parseDouble(stringId);
        		System.out.println("id:" + stringId);
            	 
        		String productName = txtName.getText();

        		String price = txtPrice.getText();
        		double  productPrice = Double.parseDouble(price);

        		String productType = txtType.getText();
        		
        		String productProvider = txtProvider.getText();
				
				String stringStock = txtInStock.getText();
				double stock = Double.parseDouble(stringStock);
				
				String discountString = txtDiscount.getText();
				double discount = Double.parseDouble(discountString);
				
				
				if(discountString.equals("none") || discountString.equals("None")){
					discount = 0.0;
				}
				
				
				if(discount > productPrice){
					JOptionPane.showMessageDialog(frame, "item not updated, discount price greater than product price");
					return;
				}
				
        		try {
					Connection myCon = (Connection) DriverManager.getConnection(DBConnection.dbUrl, DBConnection.dbUser, DBConnection.dbPassword);

        		 	String sql = "INSERT into products"
        		            + "(productid,productname,productprice,provider,type,inStock, discountPrice) VALUES"
        		            + "(?,?,?,?,?,?,?)";
        		    java.sql.PreparedStatement ps = myCon.prepareStatement(sql);
        		    ps.setDouble(1, id);
        		    ps.setString(2, productName);
        		    ps.setDouble(3, productPrice);
        		    ps.setString(4, productProvider);
        		    ps.setString(5, productType);
        		    ps.setDouble(6, stock);
        		    ps.setDouble(7, discount);
        		     		    
        		    ps.executeUpdate();
        		}
        		catch (Exception e1){
        		    e1.printStackTrace();
        		}
        		Inventory myInv = new Inventory(curEmployee);
            	myInv.setVisible(true);
            	frame.dispose();
			}
		});
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);

	}
}
