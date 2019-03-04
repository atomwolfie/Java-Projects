import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.List;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

/**
 * GUI to display receipt after purchase
 */
public class Receipt {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private boolean isReturn;


	/**
	 * Create the application.
	 */
	public Receipt(Order curOrder) {
		initialize(curOrder);
	}


	/**
	 * Create the application.
	 */
	public Receipt(Order curOrder, boolean isReturn) {
		this.isReturn = isReturn;
		initialize(curOrder);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Order curOrder) {
		frame = new JFrame();
		frame.setBounds(400, 100, 450, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblReceipt = new JLabel("Receipt");
		lblReceipt.setBounds(174, 6, 61, 29);
		if (this.isReturn) {
			lblReceipt.setText("Receipt - REFUND");
			lblReceipt.setBounds(154, 6, 121, 29);
		}
		frame.getContentPane().add(lblReceipt);

		JLabel lblOrderId = new JLabel("Order ID: " + curOrder.getOrderId());
		lblOrderId.setBounds(115,30,150,29);
		frame.getContentPane().add(lblOrderId);
		
		JLabel lblNewLabel = new JLabel("Mr. Smith's Store");
		lblNewLabel.setBounds(153, 47, 143, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("1125 Main St. Logan Utah 84173");
		lblNewLabel_1.setBounds(104, 57, 258, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel label = new JLabel("----------------------------------------");
		label.setBounds(45, 508, 373, 16);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("----------------------------------------");
		label_1.setBounds(45, 555, 373, 16);
		frame.getContentPane().add(label_1);

		Double tax = curOrder.getOrderTotal() * .03;

		DecimalFormat dec = new DecimalFormat("#.00");

		JLabel lblTax = new JLabel("tax: $" + dec.format(tax));
		if (this.isReturn) {
			tax = curOrder.getReturnTotal() * .03;
			lblTax.setText("tax: -$" + dec.format(-1 * tax));
		}
		lblTax.setBounds(104, 535, 150, 16);
		frame.getContentPane().add(lblTax);
		
		JLabel lblTotal = new JLabel("total: $" + dec.format((curOrder.getOrderTotal() + tax)));
		if (this.isReturn) {
			lblTotal.setText("total: -$" + dec.format(-1 * (curOrder.getReturnTotal() + tax)));
		}
		lblTotal.setBounds(104, 577, 150, 16);
		frame.getContentPane().add(lblTotal);

		String[] columnNames = {"Product","Quantity","Price"};
		String[][] Data = {};
		table = new JTable(new DefaultTableModel(Data, columnNames));
		model = (DefaultTableModel) table.getModel();
		table.setBounds(55, 98, 317, 148);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		fillTable(curOrder);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(55,95,317,398);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel_2 = new JLabel("tel: (435) 757 - 2211");
		lblNewLabel_2.setBounds(141, 618, 143, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblMrsmithgroceryemailcom = new JLabel("email: mrsmithgrocery@email.com");
		lblMrsmithgroceryemailcom.setBounds(141, 646, 248, 16);
		frame.getContentPane().add(lblMrsmithgroceryemailcom);
	}

	public void fillTable(Order curOrder) {
		ArrayList<Purchases> purch = curOrder.getPurchases();

		DecimalFormat dec = new DecimalFormat("#.00");

		if (this.isReturn) {
			for (int i = 0; i < purch.size(); i++) {
				model.addRow(new Object[]{purch.get(i).getProductName(), -1 * curOrder.getReturnQuantity(i), "-$" + dec.format(curOrder.getPurchases().get(i).getProdPrice() * curOrder.getReturnQuantity(i))});
			}
		}
		else {
			for (int i = 0; i < purch.size(); i++) {
				model.addRow(new Object[]{purch.get(i).getProductName(), purch.get(i).getQuantity(), "$" + dec.format(purch.get(i).getPurchaseTotal())});
			}
		}
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
