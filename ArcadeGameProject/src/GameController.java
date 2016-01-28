
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Controls the ArcadeGame
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public class GameController {
	private Level level;
	
	private boolean isPaused;
	private boolean pausedPressed;
	private int numberOfLives = 4;
	private boolean gameOver;
	
	private int tickCount;
	private int projectileTickCount;
	
	private InputController inputController;
	private LevelLoader levelLoader;
	private GameGraphicsComponent graphicsComponent;
	private boolean projectileCooldown;

	private int fireRate;

	private boolean isAngry;
	
	/**
	 * Creates and sets up a GameController using a JFrame
	 * @param frame
	 */
	public GameController(JFrame frame) {
		this.inputController = new InputController();
		frame.addKeyListener(this.inputController);
		
		
		this.levelLoader = new LevelLoader();
		this.level = this.levelLoader.loadLevel("levelone");
		
		JPanel gui = new JPanel();
		gui.setBounds(0 ,18*20, 24*20, 80);
		
		JLabel currentLevel = new JLabel("Current Level: "+ this.level.getLevelNumber());
		JLabel emeraldCount = new JLabel("Emeralds left: " + this.level.getEmeraldCount());
		JLabel currentGold = new JLabel("       Gold Left:" + this.level.getGoldCount());
		JLabel score = new JLabel("       SCORE: " + this.level.pointsToAdd());
		
		currentLevel.setFont(new Font("Arial Bold",Font.BOLD, 25));
		emeraldCount.setFont(new Font("Arial Bold",Font.BOLD, 25));
		currentGold.setFont(new Font("Arial Bold",Font.BOLD, 25));
		score.setFont(new Font("Arial Bold",Font.BOLD, 25));
		
		gui.add(currentLevel,0);
		gui.add(score,1);
		gui.add(currentGold,2);
		gui.add(emeraldCount,3);
		
		frame.setBackground(Color.black);
		gui.setBackground(Color.black);
		
		emeraldCount.setForeground(Color.green);
		currentLevel.setForeground(Color.white);
		currentGold.setForeground(Color.yellow);
		score.setForeground(Color.yellow);
		
		this.graphicsComponent = new GameGraphicsComponent(this.level, emeraldCount, currentLevel, currentGold,score,this);
		
		frame.add(gui,0);
		frame.add(this.graphicsComponent,1);
	}
	
	/**
	 * Starts the run loop that handles the game tick
	 */
	public void startRunLoop() {
		runLoop();
	}
	
	/**
	 * The run loop that handles the game tick
	 */
	private void runLoop() {
		while(true) {
			// Add runloop calls here
			
			KeyType currentKey = this.inputController.getCurrentKey();
			
			if(this.isGameOver()){
				this.isPaused = true;
				this.level.update(KeyType.EMPTY_KEY,this);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!(currentKey == KeyType.EMPTY_KEY)){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						
					}
					this.isPaused = false;
					this.gameOver = false;
					this.setNumberOfLives(5);
					this.level = this.levelLoader.loadLevel("levelone");
					this.graphicsComponent.setLevel(this.level);
					this.graphicsComponent.updateLevelandGrid();
					currentKey = KeyType.EMPTY_KEY;
				}
			}
						
			if(currentKey == KeyType.P && !this.pausedPressed) {
				this.setPaused(!this.isPaused());
				this.pausedPressed = true;
			} else if(currentKey != KeyType.P && this.pausedPressed) {
				this.pausedPressed = false;
			}
			
			
			if(!this.isPaused()) {
				if(currentKey == KeyType.SPACE) {
					if(this.projectileTickCount == 0){
						this.level.fireProjectile();
						this.projectileTickCount ++;
						this.projectileCooldown = true;
					}
				}
				
				if(this.projectileCooldown){
					this.projectileTickCount ++;
					if(this.projectileTickCount > this.fireRate ){
						this.projectileTickCount = 0;
						this.projectileCooldown = false;
					}
				}
				
				if(tickCount == 0) {					
					if(currentKey == KeyType.U) {
						this.level = this.levelLoader.loadNextLevel(this.level);
						this.graphicsComponent.setLevel(this.level);
					} else if(currentKey == KeyType.D) {
						this.level = this.levelLoader.loadPreviousLevel(this.level);
						this.graphicsComponent.setLevel(this.level);
					}
					
					this.level.update(currentKey, this);
					
					if(this.numberOfLives == 1){
						this.setAngryMode(true);
					}else{
						this.setAngryMode(false);
					}
					
					if(this.numberOfLives <= 0){
						int totalPoints = this.level.getScoreFromThisLevel();
						this.level = this.levelLoader.loadLevel("GAMEOVER");
						this.level.setStartingPoints(totalPoints);
						this.level.update(KeyType.EMPTY_KEY, this);
						System.out.println(this.level.getStartingPoints());
						System.out.println(this.level.getScoreFromThisLevel());
						this.graphicsComponent.setLevel(this.level);
					}
					
						
					if(this.level.getEmeraldCount() == 0) {
						int totalPoints = this.level.getScoreFromThisLevel();
						this.level = this.levelLoader.loadNextLevel(this.level);
						this.level.setStartingPoints(totalPoints);
						this.graphicsComponent.setLevel(this.level);
					}
				} else if(tickCount > 5) {
					tickCount = -1;
				}
					
				tickCount++;
			} else {
				if(tickCount > 0) {
					if(tickCount > 5) {
						tickCount = -1;
					}
						
					tickCount++;
				}
			}
				
			this.graphicsComponent.updateLevelandGrid();
						
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * doge becomes very mad and gets super powers
	 * @param isAngry
	 */
	public void setAngryMode(boolean Angry){
		if(Angry){
			this.isAngry = true;
			this.fireRate = 10;
		}else {
			this.isAngry = false;
			this.fireRate = 400;
		}
	}
	
	/**
	 * returns whether player is angry or not
	 * @return
	 */
	public boolean getAngryMode(){
		return this.isAngry;
	}

	/**
	 * Returns the remaining number of lives
	 * @return
	 */
	public int getNumberOfLives() {
		return numberOfLives;
	}

	/**
	 * Sets the number of lives
	 * @param numberOfLives
	 */
	public void setNumberOfLives(int numberOfLives) {
		this.numberOfLives = numberOfLives;
	}

	/**
	 * Returns true if the game is paused
	 * @return
	 */
	public boolean isPaused() {
		return isPaused;
	}

	/**
	 * Sets whether the game is paused
	 * @param isPaused
	 */
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	/**
	 * Returns true if the game is over
	 * @return
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Sets whether the game is over
	 * @param gameOver
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
