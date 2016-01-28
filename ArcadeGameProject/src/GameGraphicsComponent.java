import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * The base GUI of ArcadeGame
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
@SuppressWarnings("serial")
public class GameGraphicsComponent extends JComponent {
	final protected int ROW_SIZE = 24;
	final protected int COL_SIZE = 18;
	
	
//	private final int ANIMATE_MOVEMENT_INTERVAL = 150;
	
	private Level level;
	private JLabel emeraldLabel;
	private JLabel currentLevel;
	private JLabel currentGold;
	private JLabel score;
	private ImageIcon dirt; 
	private ImageIcon emerald;
	private ImageIcon emptyBlock;
	private ImageIcon gold;
	private ImageIcon goldBag;
	private ImageIcon hobbinLeft;
	private ImageIcon hobbinRight;
	private ImageIcon hobbinUp;
	private ImageIcon hobbinDown;
	private ImageIcon nobbin;
	private ImageIcon diggerUp;
	private ImageIcon diggerDown;
	private ImageIcon diggerLeft;
	private ImageIcon diggerRight;
	private ImageIcon madDiggerUp;
	private ImageIcon madDiggerDown;
	private ImageIcon madDiggerLeft;
	private ImageIcon madDiggerRight;

	private ImageIcon projectileDown;
	private ImageIcon projectileLeft;
	private ImageIcon projectileRight;
	private ImageIcon projectileUp;
	private GameController gameController;
	private ImageIcon madDirt;
	private ImageIcon madEmptyBlock;
		
	/**
	 * Creates a GameGraphicsComponent initialized to a Level and with a label to list the number of emeralds on
	 * @param level
	 * @param emeraldLabel
	 * @param currentLevel 
	 * @param currentGold 
	 * @param score 
	 * @param gameController 
	 */
	public GameGraphicsComponent(Level level, JLabel emeraldLabel, JLabel currentLevel, JLabel currentGold, JLabel score, GameController gameController) {
		this.gameController = gameController;
		this.level = level;
		this.setEmeraldLabel(emeraldLabel);
		this.setCurrentLevel(currentLevel);
		this.setCurrentGold(currentGold);
		this.setScore(score);
		this.dirt = new ImageIcon("src/dirt.png");
		this.madDirt = new ImageIcon("src/mad dirt.png");
		this.emerald= new ImageIcon("src/emerald.png");
		this.emptyBlock = new ImageIcon("src/emptyblock.png");
		this.madEmptyBlock = new ImageIcon("src/mad emptyblock.png");
		this.projectileUp = new ImageIcon("src/projectile up.png");
		this.projectileDown = new ImageIcon("src/projectile down.png");
		this.projectileLeft = new ImageIcon("src/projectile left.png");
		this.projectileRight = new ImageIcon("src/projectile right.png");
		this.nobbin = new ImageIcon("src/monster.png");
		this.hobbinLeft = new ImageIcon("src/hobbin left.png");
		this.hobbinDown = new ImageIcon("src/hobbin down.png");
		this.hobbinRight = new ImageIcon("src/hobbin right.png");
		this.hobbinUp = new ImageIcon("src/hobbin up.png");
		this.gold = new ImageIcon("src/gold.png");
		this.goldBag = new ImageIcon("src/gold bag.png");
		this.diggerDown = new ImageIcon("src/digger down.png");
		this.diggerUp = new ImageIcon("src/digger up.png");
		this.diggerLeft = new ImageIcon("src/digger left.png");
		this.diggerRight = new ImageIcon("src/digger right.png");
		this.madDiggerDown = new ImageIcon("src/doge mad down.png");
		this.madDiggerUp = new ImageIcon("src/doge mad up.png");
		this.madDiggerLeft = new ImageIcon("src/doge mad left.png");
		this.madDiggerRight = new ImageIcon("src/doge mad right.png");
		
		this.setDoubleBuffered(true);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
																			
		Dimension dimension = this.level.getSize();
		if(!this.gameController.getAngryMode()){
			g2.setColor(new Color(183,121,85));
		}else {
			g2.setColor(new Color (240,62,70));
		}
		g2.fill(new Rectangle(0,0,20*24+5, 18*20+100));
		boolean playerOnBoard = false;
		for(int i = 0; i<dimension.height; i++ ){
			for(int j = 0; j<dimension.width; j++){
				GameObject gameObject = this.level.objectAtIndexes(i, j);
				@SuppressWarnings("rawtypes")
				Class class1 = gameObject.getClass();
				if(class1.equals(Dirt.class)){
					if(!this.gameController.getAngryMode()){
						g2.drawImage(this.dirt.getImage(), j*20, i*20, 20, 20, null);
					}else{
						g2.drawImage(this.madDirt.getImage(), j*20, i*20, 20, 20, null);
					}
					
				}else if(class1.equals(Emerald.class)){
					g2.drawImage(this.emerald.getImage(), j*20, i*20, 20, 20, null);
				}else if(class1.equals(EmptyBlock.class)){
					if(!this.gameController.getAngryMode()){
						g2.drawImage(this.emptyBlock.getImage(), j*20, i*20, 20, 20, null);
					}else{
						g2.drawImage(this.madEmptyBlock.getImage(), j*20, i*20, 20, 20, null);
					}
				}else if(class1.equals(Gold.class)){
					if(((Gold) gameObject).getIsBroken()){
						g2.drawImage(this.gold.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getColor() == Color.yellow){
						g2.drawImage(this.goldBag.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getColor() == Color.orange){
						g2.drawImage(this.goldBag.getImage(), j*20+4, i*20, 20, 20, null);
					}else if(gameObject.getColor() == Color.red){
						g2.drawImage(this.goldBag.getImage(), j*20-4, i*20, 20, 20, null);
					}
				}else if(class1.equals(Hobbin.class)){
					//0 is right, 1 is down, 2 is left, 3 is up
					if(gameObject.getDirection() == 0){
						g2.drawImage(this.hobbinRight.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 1){
						g2.drawImage(this.hobbinDown.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 2){
						g2.drawImage(this.hobbinLeft.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 3){
						g2.drawImage(this.hobbinUp.getImage(), j*20, i*20, 20, 20, null);
					}
				}else if(class1.equals(Nobbin.class)){
					g2.drawImage(this.nobbin.getImage(), j*20, i*20, 20, 20, null);
				}else if(class1.equals(Player.class)){
					if(!playerOnBoard){
						if(!this.gameController.getAngryMode()){
							if(gameObject.getDirection() == 0){
								g2.drawImage(this.diggerRight.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 1){
								g2.drawImage(this.diggerDown.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 2){
								g2.drawImage(this.diggerLeft.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 3){
								g2.drawImage(this.diggerUp.getImage(), j*20, i*20, 20, 20, null);
							}
						}else if(this.gameController.getAngryMode()){
							if(gameObject.getDirection() == 0){
								g2.drawImage(this.madDiggerRight.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 1){
								g2.drawImage(this.madDiggerDown.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 2){
								g2.drawImage(this.madDiggerLeft.getImage(), j*20, i*20, 20, 20, null);
							}else if(gameObject.getDirection() == 3){
								g2.drawImage(this.madDiggerUp.getImage(), j*20, i*20, 20, 20, null);
							}
						}
						playerOnBoard = true;
					}else{
						g2.drawImage(this.emptyBlock.getImage(), j*20, i*20, 20, 20, null);
					}
				}else if(class1.equals(Projectile.class)){
					//0 is right, 1 is down, 2 is left, 3 is up
					if(gameObject.getDirection() == 0){
						g2.drawImage(this.projectileRight.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 1){
						g2.drawImage(this.projectileDown.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 2){
						g2.drawImage(this.projectileLeft.getImage(), j*20, i*20, 20, 20, null);
					}else if(gameObject.getDirection() == 3){
						g2.drawImage(this.projectileUp.getImage(), j*20, i*20, 20, 20, null);
					}
				}else{
					if(!this.gameController.getAngryMode()){
						g2.drawImage(this.emptyBlock.getImage(), j*20, i*20, 20, 20, null);
					}else{
						g2.drawImage(this.madEmptyBlock.getImage(), j*20, i*20, 20, 20, null);
					}
				}
				
			}
		}
		
		// Remove the comments for animation
		
		/*for(int i = 0; i<dimension.height; i++ ){
			for(int j = 0; j<dimension.width; j++){
				GameObject gameObject = this.level.objectAtIndexes(i, j);
				
				if(gameObject.canAnimate()) {
					g2.setColor(gameObject.getColor());
					if(gameObject.shouldAnimate()) {
						int direction = gameObject.getDirection();
						
						int row = gameObject.getRow();
						int column = gameObject.getColumn();
						
						int animateX = gameObject.getAnimateX();
						int animateY = gameObject.getAnimateY();
						
						int newAnimateX = animateX;
						int newAnimateY = animateY;
						
						Rectangle2D oldRect = new Rectangle2D.Double(column*20, row*20,20,20);
						
						GameObject previousObject = gameObject.getPreviousObject();
						
						g2.setColor(previousObject.getColor());
						
						if(Gold.class.isAssignableFrom(previousObject.getClass())) {
							g2.setColor(new EmptyBlock(0, 0).getColor());
						}
						
						g2.fill(oldRect);
						g2.setColor(gameObject.getColor());
						
						switch (direction) {
						case 0:
							column--;
							newAnimateX++;
							gameObject.setAnimateX(newAnimateX);
							break;
						case 1:
							row--;
							newAnimateY++;
							gameObject.setAnimateY(newAnimateY);
							break;
						case 2:
							column++;
							newAnimateX--;
							gameObject.setAnimateX(newAnimateX);
							break;
						case 3:
							row++;
							newAnimateY--;
							gameObject.setAnimateY(newAnimateY);
							break;
						default:
							break;
						}
																	
						Rectangle2D rect = new Rectangle2D.Double(column*20+((double)20/ANIMATE_MOVEMENT_INTERVAL)*animateX,row*20+((double)20/ANIMATE_MOVEMENT_INTERVAL)*animateY,20,20);
						g2.fill(rect);
						
					} else {
						Rectangle2D rect = new Rectangle2D.Double(gameObject.getColumn()*20,gameObject.getRow()*20,20,20);
						g2.fill(rect);
					}
				}
				
			}
		}*/
	}
		
	/**
	 * Sets the Level to render
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * Renders the changes in the Level
	 */
	public void updateLevelandGrid(){
		if(!this.level.getLevelName().equals("GAMEOVER")){
		this.getEmeraldLabel().setText("       Emeralds left: " + this.level.getEmeraldCount());
		this.getCurrentLevel().setText("       Current Level: " + this.level.getLevelNumber());
		this.getCurrentGold().setText("       Gold Left: "+ this.level.getGoldCount());
		this.getScore().setText("       SCORE: " + this.level.getScoreFromThisLevel());
		}else {
			this.currentLevel.setText("GAME OVER");
			this.score.setText("");
			this.emeraldLabel.setText("Press any key to play again!");
			this.currentGold.setText("Final Score: " +this.level.getScoreFromThisLevel());
		}
		this.repaint();
	}

	/**
	 * Returns the Emerald JLabel
	 * @return
	 */
	public JLabel getEmeraldLabel() {
		return emeraldLabel;
	}

	/**
	 * Sets the Emerald JLabel
	 * @param emeraldLabel
	 */
	public void setEmeraldLabel(JLabel emeraldLabel) {
		this.emeraldLabel = emeraldLabel;
	}

	/**
	 * Returns the current level JLabel
	 * @return
	 */
	public JLabel getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Sets the current level JLabel
	 * @param currentLevel
	 */
	public void setCurrentLevel(JLabel currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * Returns the current gold JLabel
	 * @return
	 */
	public JLabel getCurrentGold() {
		return currentGold;
	}

	/**
	 * Sets the current gold JLabel
	 * @param currentGold
	 */
	public void setCurrentGold(JLabel currentGold) {
		this.currentGold = currentGold;
	}

	/**
	 * Returns the score JLabel
	 * @return
	 */
	public JLabel getScore() {
		return score;
	}

	/**
	 * Sets the score JLabel
	 * @param score
	 */
	public void setScore(JLabel score) {
		this.score = score;
	}

}
