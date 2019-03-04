
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Random;
import javax.swing.JFrame;






public class Simonish extends JFrame implements ActionListener, MouseListener {
	

	JPanel  redBtn, blueBtn, greenBtn, yellowBtn;
	Container pane;
	JFrame frame;
	//JFrame boardPanel;
	

	
	
	public JButton startBtn;
	int playerChoice;
	int computerChoice;
	private Vector<Integer> player = new Vector();
	private Vector<Integer> computer;
	
	
	Random gen = new Random();
	
	
	//ScoreBoard scorePanel;
	
	public Simonish() {
		
		this.setTitle("Simonish");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600, 622);
		pane = this.getContentPane();
		pane.setLayout(null);
		this.setLocationRelativeTo(null);
				
		startBtn = new JButton("Start");
		redBtn = new JPanel();
		blueBtn = new JPanel();
		greenBtn = new JPanel();
		yellowBtn = new JPanel();
		
		
		
		startBtn.setBounds(190, 500, 200, 70);
		redBtn.setBounds(60,50,200, 200);
		blueBtn.setBounds(330, 50, 200, 200);
		greenBtn.setBounds(60,280,200,200);
		yellowBtn.setBounds(330,280,200,200);
		
		
		blueBtn.setBackground(Color.BLUE.darker());
		redBtn.setBackground(Color.RED.darker());
		greenBtn.setBackground(Color.GREEN.darker());
		yellowBtn.setBackground(Color.YELLOW.darker());
		
		
				
		pane.add(startBtn);
		pane.add(redBtn);
		pane.add(blueBtn);
		pane.add(greenBtn);
		pane.add(yellowBtn);
		
		
		
		this.setVisible(true);
		
		
		
		
		redBtn.addMouseListener(this);
		
		blueBtn.addMouseListener(this);
		
		greenBtn.addMouseListener(this);
		
		yellowBtn.addMouseListener(this);
		
		startBtn.addActionListener(this);
			
		
		return;
	} //end of class
	

	/** 
	 * This is a function that creates random numbers 0-3 (which are assigned for each button) and loads the number into a vector
	 * The vector will be used to compare to the players choices
	 * It also emulates a blink for whatever button (number) was selected
	 * 
	 * 
	 * */
	
	public void runGame(){
		computer = new Vector<>();
	
		int randumNum = gen.nextInt((3 - 0) + 1) + 0; 
		
		computer.add(randumNum);
		System.out.println(randumNum);
		for (int i = 0; i < computer.size(); ++i){
			
			if(computer.get(i) == 0){
				
				blink(redBtn);
				
			}
			 if(computer.get(i) == 1){
				blink(blueBtn);
			}
			 if(computer.get(i) == 2){
				blink(greenBtn);
			}
			if(computer.get(i) == 3){
				blink(yellowBtn);
			}
		}	
		
	}

	/**
	 * This function will be invoked when the computer chooses buttons and when the player chooses buttons,
	 * the button should flash brighter then darker the return to it's original state after
	 * 
	 * @param redBtn2
	 */
	
	private void blink(JPanel redBtn2) {
		redBtn2.setBackground(redBtn2.getBackground().darker());
		try {
			Thread.sleep(500);
			System.out.println("Blinking");
						
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		redBtn2.setBackground(redBtn2.getBackground().brighter());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

/**
 * Creates a new class beginning the program	
 * @param args
 */
	
	public static void main(String[] args) {

        new Simonish(); //create an instance of the Counter object (this class file) to get the program going.

    }
	
	/**
	 * This function calls upon runGame() which invokes the computer to make it's moves and set's everything in motion
	 * @param actionPerformed
	 */
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == startBtn){
				runGame();
			}
			
		}
		
	/**
	 * In the mouse clicked function whatever the player chooses will add a number 0-3 to the player's vector which
	 * will be compared to the computer's vector, if the players vector is the same size and contains equivalent values
	 * the players vector will be deleted and the computer will iterate through adding one new element to its vector
	 * 
	 * 
	 *@param mouseClicked
	 */
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
			if(e.getSource() == redBtn){
				player.add(0);    
				
			}
			 if(e.getSource() == blueBtn){
				player.add(1);
			}
			 if(e.getSource() == greenBtn){
				player.add(2);
			}
			 if(e.getSource() == yellowBtn){
				player.add(3);
			 }
			
		if (player.size() == computer.size()){
				
				if (player.equals(computer)){
				
					player.clear();
					runGame();
			}
		}
			
		if (player.get(player.size()-1) != computer.get(player.size()-1)){
				
			
			//JOptionPane.showMessageDialog( "You lost the game, Simon wins!");
		System.out.println("You lost the game, Simon wins!");
		player.clear();
		computer.clear();
			runGame();
		}
		
		JPanel clickedPanel = (JPanel) e.getSource();
		blink(clickedPanel);
		}	
		
	
	



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		JPanel clickedPanel = (JPanel) e.getSource();
		
		clickedPanel.setBackground(clickedPanel.getBackground().brighter());
		}
	



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		JPanel clickedPanel = (JPanel) e.getSource();
		
		clickedPanel.setBackground(clickedPanel.getBackground().darker());
		
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
