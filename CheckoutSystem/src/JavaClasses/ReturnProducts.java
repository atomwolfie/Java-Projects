import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * GUI to return products given an order.
 */
public class ReturnProducts {

	private JFrame frame;
	private JTable table;
	private JLabel lblTotal;
	private JButton btnRefund;
	private JTextField txtId;
	private Order currentOrder;
	private DefaultTableModel model;
	private	DecimalFormat dec;
	private Employee curEmployee;


	/**
	 * Create the application.
	 */
	public ReturnProducts() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public ReturnProducts(Employee curEmployee) {
		this.curEmployee = curEmployee;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public ReturnProducts(Order curOrder, Employee curEmployee) {
		this.currentOrder = curOrder;
		this.curEmployee = curEmployee;
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
		
		JLabel lblNewLabel = new JLabel("Return Products");
		lblNewLabel.setBounds(375, 6, 141, 16);
		frame.getContentPane().add(lblNewLabel);


		JLabel lblAddItemLabel = new JLabel("Enter order ID here");
		lblAddItemLabel.setBounds(80, 100, 141, 16);
		frame.getContentPane().add(lblAddItemLabel);

		txtId = new JTextField();
		txtId.setText("");
		txtId.setBounds(106, 132, 130, 26);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);

		JLabel lblEnterId = new JLabel("Order ID:");
		lblEnterId.setBounds(41, 137, 74, 16);
		frame.getContentPane().add(lblEnterId);

		JButton btnOpenOrder = new JButton("Open Order");
		btnOpenOrder.setBounds(52, 238, 155, 40);
		frame.getContentPane().add(btnOpenOrder);
		
		btnRefund = new JButton("Finish and Refund");
		btnRefund.setBounds(57, 470, 165, 56);
		btnRefund.setBackground(new Color(95,186,125));
		if (this.currentOrder == null || this.currentOrder.getOrderTotal() == 0) {
			btnRefund.setEnabled(false);
		}
		else {
			btnRefund.setEnabled(true);
		}
		frame.getContentPane().add(btnRefund);
		
		JButton btnStartOver = new JButton("Start Over");
		btnStartOver.setBounds(57, 538, 165, 26);
		frame.getContentPane().add(btnStartOver);
		
		JButton btnGoBack = new JButton("go back");
		btnGoBack.setBounds(700, 610, 117, 29);
		frame.getContentPane().add(btnGoBack);

		lblTotal = new JLabel("Total: $0.00");
		lblTotal.setBounds(284, 558, 150, 16);
		frame.getContentPane().add(lblTotal);

		String[] columnNames = {"Product","-","Return","+","Price","Quantity"};
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
		//table.getColumnModel().getColumn(5).setMaxWidth(20);
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

            if (e.getSource() == btnOpenOrder) {
				txtId.setForeground(new JTextField().getForeground());
				currentOrder = new Order(Integer.parseInt(txtId.getText()), true);
				model.setRowCount(0);
				btnRefund.setEnabled(false);
				if (!currentOrder.isValid()) {
					invalidInfo(txtId);
				}
				populateTable();
            } 

            if (e.getSource() == btnStartOver) {  //start over button, starts everything over
				currentOrder.reset();
            	currentOrder.clearPurchases();
            	model.setRowCount(0);
				btnRefund.setEnabled(false);
				lblTotal.setText("Total: $0.00");
            }

            if (e.getSource() == btnRefund) {

            	this.setVisible(false);
            	Payment paymnt = new Payment(currentOrder, curEmployee, true);
            	paymnt.setVisible(true);
            }
        }

		private void setVisible(boolean b) {
			frame.setVisible(b);
		}

    };
    
	btnGoBack.addActionListener(buttonListener);
	btnOpenOrder.addActionListener(buttonListener);
	btnStartOver.addActionListener(buttonListener);
	btnRefund.addActionListener(buttonListener);

	table.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			JTable temp = (JTable)e.getSource();
			if (temp.getSelectedColumn() == 1) {
				decrRow(temp.getSelectedRow());
			}
			if (temp.getSelectedColumn() == 3) {
				incrRow(temp.getSelectedRow());
			}
		}
	});
}

	private void incrRow(int row) {
		if (!btnRefund.isEnabled()) {
			btnRefund.setEnabled(true);
		}
		if (this.currentOrder.getReturnQuantity(row) == this.currentOrder.getPurchases().get(row).getQuantity()) {
			return;
		}
		currentOrder.incrementReturnQuantity(row, 1);

		this.table.setValueAt(this.currentOrder.getReturnQuantity(row), row, 2);
		this.table.setValueAt("-$" + dec.format(this.currentOrder.getPurchases().get(row).getProdPrice() * this.currentOrder.getReturnQuantity(row)), row, 4);

		this.lblTotal.setText("Total: -$" + dec.format(-1 * this.currentOrder.getReturnTotal()));
	}

	private void decrRow(int row) {
		if (this.currentOrder.getReturnQuantity(row) == 0) {
			return;
		}

		currentOrder.decrementReturnQuantity(row, 1);

		this.table.setValueAt(this.currentOrder.getReturnQuantity(row), row, 2);
		this.table.setValueAt("-$" + dec.format(-1 * this.currentOrder.getPurchases().get(row).getProdPrice() * this.currentOrder.getReturnQuantity(row)), row, 4);

		if (currentOrder.getReturnTotal() == 0) {
			btnRefund.setEnabled(false);
			lblTotal.setText("Total: $0.00");
		}
		else {
			this.lblTotal.setText("Total: -$" + dec.format(-1 * this.currentOrder.getReturnTotal()));
		}
	}

	private void invalidInfo(JTextField lbl) {
		lbl.setForeground(Color.RED);
	}

	public void populateTable() {
		ArrayList<Purchases> purch = this.currentOrder.getPurchases();

		this.lblTotal.setText("Total: -$0.00");

		for (int i = 0; i < purch.size(); i++) {
			this.model.addRow(new Object[]{purch.get(i).getProductName(), "-", "0", "+", "-$0.00", purch.get(i).getQuantity()});
		}
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
}