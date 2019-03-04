import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * GUI that reports on orders, only available to managers
 */
public class Report {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private DecimalFormat dec;
    private Employee curEmployee;
    /**
     * Constructor
     */
    Report() {
        initialize();
    }

    /**
     * Constructor with employee
     */
    Report(Employee employee) {
        this.curEmployee = employee;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        dec = new DecimalFormat("#.00");
        frame = new JFrame();
        frame.setBounds(400, 100, 900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Report");
        lblNewLabel.setBounds(425, 6, 141, 16);
        frame.getContentPane().add(lblNewLabel);

        JButton btnGoBack = new JButton("go back");
        btnGoBack.setBounds(700, 610, 117, 29);
        frame.getContentPane().add(btnGoBack);

        JLabel lblSelectFrom = new JLabel("Select orders from:");
        lblSelectFrom.setBounds(70, 50, 160, 30);
        frame.getContentPane().add(lblSelectFrom);

        JTextField txtStartDate = new JTextField("2000-01-01");
        txtStartDate.setBounds(230, 50, 160, 30);
        frame.getContentPane().add(txtStartDate);

        JLabel lblSelectTo = new JLabel("to:");
        lblSelectTo.setBounds(430, 50, 160, 30);
        frame.getContentPane().add(lblSelectTo);

        JTextField txtEndDate = new JTextField("5000-12-25");
        txtEndDate.setBounds(480, 50, 160, 30);
        frame.getContentPane().add(txtEndDate);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(670, 50, 160, 30);
        frame.getContentPane().add(btnFilter);

        String[] columnNames = {"Customer Name","Payment Method","Order Total","Date Time"};
        String[][] Data = {};

        TableModel modelo = new DefaultTableModel(Data, columnNames)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        table = new JTable(modelo);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        model = (DefaultTableModel) table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,95,800,450);
        frame.getContentPane().add(scrollPane);

        populateTable();

        ActionListener buttonListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == btnGoBack) { //return to main screen
                    frame.setVisible(false);
                    MainScreen main = new MainScreen(curEmployee);
                    main.setVisible(true);
                    frame.dispose();
                }
                if (e.getSource() == btnFilter) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    model.setRowCount(0);
                    try {
                        populateTable(new java.sql.Date(format.parse(txtStartDate.getText()).getTime()),new java.sql.Date(format.parse(txtEndDate.getText()).getTime()));
                    } catch (ParseException e1) {
                        //e1.printStackTrace();
                    }
                }
            }
        };

        btnGoBack.addActionListener(buttonListener);
        btnFilter.addActionListener(buttonListener);
    }

    public void populateTable () {

        ResultSet customerRS = DBConnection.dbSelectAllFromTable("customers");
        int customerCount = DBConnection.dbGetRecordCountForTable("customers");

        String[] customers = new String[customerCount + 1];
        try {
            while (customerRS.next()) {
                customers[customerRS.getInt("customerid")] = customerRS.getString("customername");
            }
        } catch (SQLException e) {
            
        	e.printStackTrace();
        }

        ResultSet rs = DBConnection.dbSelectAllFromTable("orders");
        try {
            while (rs.next()) {
                this.model.addRow(new Object[]{customers[rs.getInt("customerid")],rs.getString("paymentmethod"),"$" + dec.format(rs.getFloat("ordertotal")),rs.getString("date_time")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateTable (Date start, Date end) {

        ResultSet customerRS = DBConnection.dbSelectAllFromTable("customers");
        int customerCount = DBConnection.dbGetRecordCountForTable("customers");

        String[] customers = new String[customerCount + 1];
        try {
            while (customerRS.next()) {
                customers[customerRS.getInt("customerid")] = customerRS.getString("customername");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        ResultSet rs = DBConnection.dbSelectAllFromTable("orders");
        try {
            while (rs.next()) {
                System.out.println("Start time: " + start.getTime() + "       End time: " + end.getTime());
                System.out.println(rs.getDate("date_time").getTime());
                if (start.getTime() <= rs.getDate("date_time").getTime() && rs.getDate("date_time").getTime() <= end.getTime()) {
                    this.model.addRow(new Object[]{customers[rs.getInt("customerid")], rs.getString("paymentmethod"), "$" + dec.format(rs.getFloat("ordertotal")), rs.getString("date_time")});
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
