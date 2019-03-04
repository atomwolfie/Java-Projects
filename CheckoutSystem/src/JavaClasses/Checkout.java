import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * GUI to checkout
 */
public class Checkout {

	private JFrame frame;
	private JTable table;
	private JLabel lblTotal;
	private JButton btnPay;
	private JTextField txtId;
	private JTextField txtname;
	private JTextField textField;
	private Order currentOrder;
	private DefaultTableModel model;
	private	DecimalFormat dec;
	private Employee curEmployee;


	/**
	 * Create the application.
	 */
	public Checkout() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public Checkout(Employee curEmployee) {
		this.curEmployee = curEmployee;
		initialize();
		populateTable();
	}

	/**
	 * Create the application.
	 */
	public Checkout(Order curOrder) {
		this.currentOrder = curOrder;
		initialize();
		populateTable();
	}

	public Checkout(Order curOrder, Employee curEmployee) {
		this.curEmployee = curEmployee;
		this.currentOrder = curOrder;
		initialize();
		populateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dec = new DecimalFormat("#.00");
		if (this.currentOrder == null) {
			this.currentOrder = new Order();
		}
		frame = new JFrame();
		 frame.setBounds(400, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Checkout");
		lblNewLabel.setBounds(425, 6, 141, 16);
		frame.getContentPane().add(lblNewLabel);


		JLabel lblAddItemLabel = new JLabel("Add item");
		lblAddItemLabel.setBounds(110, 80, 111, 16);
		frame.getContentPane().add(lblAddItemLabel);

		txtId = new JTextField();
		txtId.setText("");
		txtId.setBounds(106, 112, 130, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);

		txtname = new JTextField();
		txtname.setText("");
		txtname.setBounds(106, 167, 130, 26);
		frame.getContentPane().add(txtname);
		txtname.setColumns(10);

		textField = new JTextField();
		textField.setText("");
		textField.setColumns(10);
		textField.setBounds(106, 240, 130, 26);
		frame.getContentPane().add(textField);

		JLabel lblEnterId = new JLabel("Enter id:");
		lblEnterId.setBounds(44, 117, 74, 16);
		frame.getContentPane().add(lblEnterId);

		JLabel lblOr = new JLabel("or");
		lblOr.setBounds(64, 144, 74, 16);
		frame.getContentPane().add(lblOr);

		JLabel lblEnterName = new JLabel("Enter name:");
		lblEnterName.setBounds(19, 172, 94, 16);
		frame.getContentPane().add(lblEnterName);

		JLabel lblEnterAmount = new JLabel("Quantity:");
		lblEnterAmount.setBounds(37, 245, 78, 16);
		frame.getContentPane().add(lblEnterAmount);

		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(135, 288, 100, 40);
		frame.getContentPane().add(btnAddItem);
		
		btnPay = new JButton("Finish and Pay");
		btnPay.setBounds(57, 470, 150, 56);
		btnPay.setBackground(new Color(95,186,125));
		if (this.currentOrder == null || this.currentOrder.getOrderTotal() == 0) {
			btnPay.setEnabled(false);
		}
		else {
			btnPay.setEnabled(true);
		}
		frame.getContentPane().add(btnPay);
		
		JButton btnStartOver = new JButton("Start Over");
		btnStartOver.setBounds(57, 538, 150, 26);
		frame.getContentPane().add(btnStartOver);
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(700, 610, 117, 29);
		frame.getContentPane().add(btnGoBack);

		lblTotal = new JLabel("Total: $0.00");
		lblTotal.setBounds(284, 558, 150, 16);
		frame.getContentPane().add(lblTotal);

		String[] columnNames = {"Product","-","Quantity","+","Price","X"};
		String[][] Data = {};

		TableModel modelo = new DefaultTableModel(Data, columnNames)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table = new JTable(modelo);
		table.getColumnModel().getColumn(1).setMaxWidth(20);
		table.getColumnModel().getColumn(3).setMaxWidth(20);
		table.getColumnModel().getColumn(5).setMaxWidth(20);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		model = (DefaultTableModel) table.getModel();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(284,85,570,450);
		frame.getContentPane().add(scrollPane);


	ActionListener buttonListener = new ActionListener() {

        //we have to define this method in order for an Action Listener to work
        public void actionPerformed(ActionEvent e) { //'e' is the Action Event which is a button being clicked in our case

            if (e.getSource() == btnGoBack) { //return to main screen

            	this.setVisible(false);
            	MainScreen main = new MainScreen(curEmployee);
            	main.setVisible(true);
            	frame.dispose();
            }

            if (e.getSource() == btnAddItem) {
				scanForItem();
            } 

            if (e.getSource() == btnStartOver) {  //start over button, starts everything over
				currentOrder.reset();
            	currentOrder.clearPurchases();
            	model.setRowCount(0);
				btnPay.setEnabled(false);
				lblTotal.setText("Total: $0.00");
            }

            if (e.getSource() == btnPay) { 

            	this.setVisible(false);
            	Payment paymnt = new Payment(currentOrder, curEmployee, false);
            	paymnt.setVisible(true);
            }
        }

		private void setVisible(boolean b) {
			frame.setVisible(b);
		}

    };
    
	btnGoBack.addActionListener(buttonListener);
	btnAddItem.addActionListener(buttonListener);
	btnStartOver.addActionListener(buttonListener);
	btnPay.addActionListener(buttonListener);

	table.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			JTable temp = (JTable)e.getSource();
			if (temp.getSelectedColumn() == 1) {
				decrRow(temp.getSelectedRow());
			}
			if (temp.getSelectedColumn() == 3) {
				incrRow(temp.getSelectedRow());
			}
			if (temp.getSelectedColumn() == 5) {
				removeRow(temp.getSelectedRow());
			}
		}
	});
}

	private void incrRow(int row) {
		currentOrder.incrementPurchaseQuantity(row, 1);

		this.table.setValueAt(this.currentOrder.getPurchases().get(row).getQuantity(), row, 2);
		this.table.setValueAt("$" + dec.format(this.currentOrder.getPurchases().get(row).getPurchaseTotal()), row, 4);

		this.lblTotal.setText("Total: $" + dec.format(this.currentOrder.getOrderTotal()));
	}

	private void decrRow(int row) {
		currentOrder.decrementPurchaseQuantity(row, 1);

		this.table.setValueAt(this.currentOrder.getPurchases().get(row).getQuantity(), row, 2);
		this.table.setValueAt("$" + dec.format(this.currentOrder.getPurchases().get(row).getPurchaseTotal()), row, 4);

		if (currentOrder.getPurchases().get(row).getQuantity() == 0) {
			currentOrder.removePurchase(row);
			model.removeRow(row);
		}

		if (currentOrder.getOrderTotal() == 0) {
			btnPay.setEnabled(false);
			lblTotal.setText("Total: $0.00");
		}
		else {
			this.lblTotal.setText("Total: $" + dec.format(this.currentOrder.getOrderTotal()));
		}
	}

	private void removeRow(int row) {
		currentOrder.removePurchase(row);
		model.removeRow(row);

		if (currentOrder.getOrderTotal() == 0) {
			btnPay.setEnabled(false);
			lblTotal.setText("Total: $0.00");
		}
		else {
			this.lblTotal.setText("Total: $" + dec.format(this.currentOrder.getOrderTotal()));
		}
	}

	private void invalidInfo(JTextField lbl) {
		lbl.setForeground(Color.RED);
	}
	private void scanForItem () {
		Purchases purchase;
		if (!this.txtId.getText().isEmpty()) {
			purchase = new Purchases(Integer.parseInt(this.txtId.getText()),Integer.parseInt(this.textField.getText()));
		}
		else {
			purchase = new Purchases(this.txtname.getText(),Integer.parseInt(this.textField.getText()));
		}
		if(purchase.isValidProduct()) {
			if (purchase.getQuantity() > 0) {
				Color defColor = new JTextField().getForeground();
				this.addProdToOrder(purchase);
				this.txtId.setText("");
				this.txtname.setText("");
				this.textField.setText("");
				this.txtId.setForeground(defColor);
				this.txtname.setForeground(defColor);
				this.textField.setForeground(defColor);
			}
			else {
				invalidInfo(this.textField);
			}
		}
		else {
			if (this.txtId.getText().isEmpty()) {
				invalidInfo(this.txtname);
			}
			else {
				invalidInfo(this.txtId);
			}
		}
	}
	public void addProdToOrder(Purchases purchase) {
		int temp = this.currentOrder.addNewPurchase(purchase);

		this.lblTotal.setText("Total: $" + dec.format(this.currentOrder.getOrderTotal()));

		if (temp < 0) {
			this.model.addRow(new Object[]{purchase.getProductName(), "-", purchase.getQuantity(), "+", '$' + dec.format(purchase.getPurchaseTotal()), "X"});
		}
		else {
			this.table.setValueAt(this.currentOrder.getPurchases().get(temp).getProductName(), temp, 0);
			this.table.setValueAt(this.currentOrder.getPurchases().get(temp).getQuantity(), temp, 2);
			this.table.setValueAt("$" + dec.format(this.currentOrder.getPurchases().get(temp).getPurchaseTotal()), temp, 4);

			}
			
		if (!this.btnPay.isEnabled()) {
			this.btnPay.setEnabled(true);
		}
	}

	public void populateTable() {
		ArrayList<Purchases> purch = this.currentOrder.getPurchases();

		this.lblTotal.setText("Total: $" + dec.format(this.currentOrder.getOrderTotal()));

		for (int i = 0; i < purch.size(); i++) {
			this.model.addRow(new Object[]{purch.get(i).getProductName(), "-", purch.get(i).getQuantity(), "+", "$" + dec.format(purch.get(i).getPurchaseTotal()), "X"});
		}
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
}