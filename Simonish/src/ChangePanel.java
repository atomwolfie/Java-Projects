import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChangePanel{
	JFrame frame;
	Container pane;
	JPanel panel;
	ColorChangeListener colorListener;
	
	public ChangePanel() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		pane = frame.getContentPane();
		pane.setPreferredSize(new Dimension(200, 200));
		
		colorListener = new ColorChangeListener();
		
		panel = new JPanel();
		panel.setBackground(java.awt.Color.BLUE.darker());
		panel.addMouseListener(colorListener);
		pane.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		
		blink();
		blink();
		blink();
		
	}
	
	private void blink() {
		panel.setBackground(panel.getBackground().brighter());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.setBackground(panel.getBackground().darker());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	class ColorChangeListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == panel) {
				panel.setBackground(panel.getBackground().brighter());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == panel) {
				panel.setBackground(panel.getBackground().darker());
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChangePanel();

	}

}