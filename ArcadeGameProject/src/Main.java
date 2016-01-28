
import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * Run
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("EXTREME DIGGER. DIGGERS BEWARE.");
		frame.setMinimumSize(new Dimension(20*24+5, 18*20+100));
		frame.setResizable(false);
		
		GameController gameController = new GameController(frame);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameController.startRunLoop();
		
	}

}
