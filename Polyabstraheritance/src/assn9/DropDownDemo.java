package assn9;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DropDownDemo {
	private JFrame frame = new JFrame("Drop Down Demo");
	private Container pane;
	private String[] strArr = {"One", "Two", "Three", "Four"};
	private String[] animArr = {"Tiger", "Lion", "Bear"};
	private String[] plantArr = {"Tree", "Shrub", "Rose"};
	private JComboBox<String> combo1;
	private JComboBox<Animal> combo2;
	private JComboBox<Plant> combo3;
	private ActionListener comboListener;
	
	public DropDownDemo() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		
		comboListener = new ComboListener();
		
		combo1 = new JComboBox<String>(strArr);
		combo1.addActionListener(comboListener);
		combo1.setSelectedIndex(-1); //what does this do?
		
		combo2 = new JComboBox<Animal>();
		for (String i : animArr) {
			combo2.addItem(new Animal(i));
		}
		combo2.addActionListener(comboListener);
		
		combo3 = new JComboBox<Plant>();
		for (String i : plantArr) {
			combo3.addItem(new Plant(i));
		}
		combo3.addActionListener(comboListener);
		
		pane.add(combo1);
		pane.add(combo2);
		pane.add(combo3);
				
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DropDownDemo();
	}
	
	private class ComboListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox tmp = (JComboBox)e.getSource();
			if (tmp.getSelectedIndex() != -1)
				JOptionPane.showMessageDialog(frame, tmp.getSelectedItem());
		}
		
	}

}
