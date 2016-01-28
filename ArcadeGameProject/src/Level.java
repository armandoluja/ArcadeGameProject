import java.awt.Dimension;
import java.util.ArrayList;

/**
 * A Level stores all of the GameObjects and base information about the running
 * game
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 * 
 */
public class Level {
	private static final int POINTS_FOR_EMERALDS = 200;
	private static final int POINTS_FOR_GOLD = 1000;
	private GameObject[][] objectArray;
	private String levelName;
	private MonsterController monsterController;
	private int numberOfEmeraldsAtStart;
	private Player player;

	private int initialPlayerRow;
	private int initialPlayerColumn;
	private int dirtCount;
	private int emeraldCount;
	private int emptyBlockCount;
	private int goldCount;
	private int hobbinCount;
	private int nobbinCount;
	private int projectileCount;
	private int pointsToAdd;
	private int numberOfGoldBagsAtStart;
	private int startingPoints;
	private int numberOfEmeraldsDestroyedByMonsters;
	private int numberOfGoldBagsDestroyedByMonsters;

	/**
	 * Creates a Level initialized to a certain game state
	 * 
	 * @param objectArray
	 * @param player
	 */
	public Level(GameObject[][] objectArray) {
		this.objectArray = objectArray;
	}

	public Level() {

	}

	/**
	 * Sets the objectArray of the Level
	 */
	public void setObjectArray(GameObject[][] objectArray) {
		this.objectArray = objectArray;
	}

	/**
	 * Update all GameObjects
	 * 
	 * @param key
	 * @param gameController
	 */
	public void update(KeyType key, GameController gameController) {
		ArrayList<GameObject> updatedObjects = new ArrayList<GameObject>();

		for (int i = 0; i < this.objectArray.length; i++) {
			for (int j = 0; j < this.objectArray[i].length; j++) {
				GameObject object = this.objectArray[i][j];
				object.setShouldAnimate(false);
				object.setDisableUpdates(false);
			}
		}

		for (int i = 0; i < this.objectArray.length; i++) {
			for (int j = 0; j < this.objectArray[i].length; j++) {
				GameObject object = this.objectArray[i][j];
				if (!object.isUpdateDisabled()) {
					object.update(key);
				} else {
					object.resetPositionChanged();
				}

				if (!object.getIsAlive()) {
					this.objectArray[i][j] = new EmptyBlock(i, j);
				}

				if (object.didPositionChange()) {
					updatedObjects.add(object);

					int newColumn = object.getColumn();
					int newRow = object.getRow();

					boolean didResetPosition = false;

					if (newRow > this.objectArray.length - 1) {
						newRow = this.objectArray.length - 1;
						object.setRow(newRow);
						didResetPosition = true;
					} else if (newRow < 0) {
						newRow = 0;
						object.setRow(newRow);
						didResetPosition = true;
					}

					if (newColumn > this.objectArray[i].length - 1) {
						newColumn = this.objectArray[i].length - 1;
						object.setColumn(newColumn);
						didResetPosition = true;
					} else if (newColumn < 0) {
						newColumn = 0;
						object.setColumn(0);
						didResetPosition = true;
					}

					if (!object.isAlive
							&& object.getClass().equals(Projectile.class)) {
						object = new EmptyBlock(object.row, object.column);
						didResetPosition = false;
					}

					if (!didResetPosition) {
						GameObject objectInNewPosition = this.objectArray[newRow][newColumn];
						objectInNewPosition.gameObjectOverlap(object, true);
						object.setPreviousObject(objectInNewPosition);
						// Checks to see if hobbin has destroyed an emerald or
						// gold object, and deducts it from points sum
						if (object.getClass().equals(Hobbin.class)) {
							// System.out.println("Hobbin");
							if (objectInNewPosition.getClass().equals(
									Emerald.class)) {
								this.numberOfEmeraldsDestroyedByMonsters++;
							} else if (objectInNewPosition.getClass().equals(
									Gold.class)) {
								this.numberOfGoldBagsDestroyedByMonsters++;
							}
						}
						// Set up for animation
						object.setAnimateX(0);
						object.setAnimateY(0);
						object.setShouldAnimate(true);

						this.objectArray[i][j] = new EmptyBlock(i, j);
						this.objectArray[newRow][newColumn] = object;
					}

				}
			}
		}

		if (this.monsterController != null) {
			this.monsterController.update(this.player);
		}

		if (!this.player.getIsAlive()) {
			playerDied();
			gameController
					.setNumberOfLives(gameController.getNumberOfLives() - 1);
			if (gameController.getNumberOfLives() <= 0) {
				gameController.setPaused(true);
				gameController.setGameOver(true);

				System.out.println("GAME OVER");
			}
		}

		countObjects();
	}

	/**
	 * Returns the GameObject at a certain position in the Level
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public GameObject objectAtIndexes(int row, int column) {
		if (row >= this.objectArray.length
				|| column >= this.objectArray[row].length) {
			return null;
		}
		return this.objectArray[row][column];
	}

	/**
	 * Prints the Level in an easy to read format
	 */
	public void printLevel() {
		for (int i = 0; i < this.objectArray.length; i++) {
			for (int j = 0; j < this.objectArray[0].length; j++) {
				System.out.print(this.objectArray[i][j].toString());
			}
			System.out.println("");
		}
	}

	/**
	 * Returns the size of the Level
	 * 
	 * @return
	 */
	public Dimension getSize() {
		return new Dimension(this.objectArray[0].length,
				this.objectArray.length);
	}

	/**
	 * Returns the name of the Level
	 * 
	 * @return
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * Returns the name of this level as an integer
	 * 
	 * @return
	 */
	public int getLevelNumber() {
		if (this.levelName.equals("levelone")) {
			return 1;
		} else if (this.levelName.equals("leveltwo")) {
			return 2;
		} else if (this.levelName.equals("levelthree")) {
			return 3;
		} else if (this.levelName.equals("YOUWIN")) {
			return 4;
		}
		return 0;
	}

	/**
	 * Sets the name of the Level
	 * 
	 * @param levelName
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * Gets this level's monsterController object
	 * 
	 * @return MonsterController
	 */
	public MonsterController getMonsterController() {
		return monsterController;
	}

	/**
	 * Sets the monsterController for the level.
	 */
	public void setMonsterController(MonsterController monsterController) {
		this.monsterController = monsterController;
		this.monsterController.setLevel(this);
	}

	/**
	 * Adds a game object to the level.
	 */
	public void addGameObject(GameObject gameObject) {
		this.objectArray[gameObject.getRow()][gameObject.getColumn()] = gameObject;
	}

	/**
	 * Sets the player for the level.
	 */
	public void setPlayer(Player player) {
		this.player = player;
		this.initialPlayerColumn = this.player.getColumn();
		this.initialPlayerRow = this.player.getRow();
	}

	/**
	 * Gets the player for the level.
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Handles firing the projectile.
	 */
	public void fireProjectile() {
		int direction = this.player.getDirection();

		int column = this.player.getColumn();
		int row = this.player.getRow();

		if (column == this.getSize().width - 1 && direction == 0) {
			return;
		} else if (row == this.getSize().height - 1 && direction == 1) {
			return;
		}

		switch (direction) {
		case 0:
			column++;
			break;
		case 1:
			row++;
			break;
		case 2:
			column--;
			break;
		case 3:
			row--;
			break;
		default:
			break;
		}

		if (column < 0) {
			return;
		} else if (row < 0) {
			return;
		}

		GameObject gameObject = objectAtIndexes(row, column);

		Projectile projectile = new Projectile(row, column, direction);
		projectile.setLevel(this);

		GameObject overlapObject = gameObject.gameObjectOverlap(projectile,
				true);
		if (overlapObject == null) {
			overlapObject = projectile;
		}

		this.objectArray[row][column] = overlapObject;
	}

	/**
	 * Updates the count for each type of object on the level.
	 */
	public void countObjects() {
		this.dirtCount = 0;
		this.setEmeraldCount(0);
		this.emptyBlockCount = 0;
		this.setGoldCount(0);
		this.hobbinCount = 0;
		this.nobbinCount = 0;
		this.projectileCount = 0;
		for (int i = 0; i < this.objectArray.length; i++) {
			for (int j = 0; j < this.objectArray[0].length; j++) {
				GameObject object = this.objectAtIndexes(i, j);
				if (object.getClass().equals(Dirt.class)) {
					this.dirtCount++;
				} else if (object.getClass().equals(Emerald.class)) {
					this.setEmeraldCount(this.getEmeraldCount() + 1);
				} else if (object.getClass().equals(EmptyBlock.class)) {
					this.emptyBlockCount++;
				} else if (object.getClass().equals(Gold.class)) {
					this.setGoldCount(this.getGoldCount() + 1);
				} else if (object.getClass().equals(Nobbin.class)) {
					this.hobbinCount++;
				} else if (object.getClass().equals(Hobbin.class)) {
					this.nobbinCount++;
				} else if (object.getClass().equals(Projectile.class)) {
					this.projectileCount++;
				}
			}
		}

		// System.out.println("Current Level: " + this.getLevelNumber());
		// System.out.println("Dirt: "+this.dirtCount + " EmptyBlocks: " +
		// this.emptyBlockCount);
		// System.out.println("Emeralds: " + this.getEmeraldCount() +
		// " GoldBags: "+ this.getGoldCount());
		// System.out.println("Hobbins: " + this.hobbinCount + " Nobbins: " +
		// this.nobbinCount);
		// System.out.println("Projectiles: " + this.projectileCount);
	}

	/**
	 * returns the number of points earned from this level
	 */
	public int getScoreFromThisLevel() {
		int score = this.getStartingPoints()
				+ (this.numberOfEmeraldsAtStart - this.emeraldCount)
				* POINTS_FOR_EMERALDS
				- (this.numberOfEmeraldsDestroyedByMonsters)
				* POINTS_FOR_EMERALDS
				+ (this.getNumberOfGoldBagsAtStart() - this.goldCount)
				* POINTS_FOR_GOLD - (this.numberOfGoldBagsDestroyedByMonsters)
				* POINTS_FOR_GOLD;
		return score;
	}

	/**
	 * Resets monsters and player when player dies.
	 */
	private void playerDied() {
		// System.out.println("Player died");

		this.monsterController.reset();

		// Remove all monsters
		for (int i = 0; i < this.objectArray.length; i++) {
			for (int j = 0; j < this.objectArray[i].length; j++) {
				GameObject object = this.objectArray[i][j];
				if (Monster.class.isAssignableFrom(object.getClass())
						|| Projectile.class.isAssignableFrom(object.getClass())) {
					this.objectArray[i][j] = new EmptyBlock(i, j);
				}
			}
		}

		this.player.setAlive();

		this.player.setColumn(this.initialPlayerColumn);
		this.player.setRow(this.initialPlayerRow);

		this.objectArray[this.initialPlayerRow][this.initialPlayerColumn] = this.player;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets the current number of gold objects on the map
	 * 
	 * @return
	 */
	public int getGoldCount() {
		return goldCount;
	}

	/**
	 * sets the current gold count
	 * 
	 * @param goldCount
	 */
	public void setGoldCount(int goldCount) {
		this.goldCount = goldCount;
	}

	/**
	 * gets the current number of emeralds on the map
	 * 
	 * @return
	 */
	public int getEmeraldCount() {
		return emeraldCount;
	}

	/**
	 * sets the current emerald count
	 * 
	 * @param emeraldCount
	 */
	public void setEmeraldCount(int emeraldCount) {
		this.emeraldCount = emeraldCount;
	}

	/**
	 * gets the number of emeralds at the start of the game
	 * 
	 * @return
	 */
	public int getNumberOfEmeraldsAtStart() {
		return numberOfEmeraldsAtStart;
	}

	/**
	 * sets the number of emeralds at the start of the game
	 * 
	 * @param numberOfEmeralds
	 */
	public void setNumberOfEmeraldsAtStart(int numberOfEmeralds) {
		this.numberOfEmeraldsAtStart = numberOfEmeralds;
	}

	/**
	 * gets the number of points to be added to the current score
	 * 
	 * @return
	 */
	public int pointsToAdd() {
		return this.pointsToAdd;
	}

	/**
	 * Gets the number of gold bags on this level
	 * 
	 * @return
	 */
	public int getNumberOfGoldBagsAtStart() {
		return numberOfGoldBagsAtStart;
	}

	/**
	 * Sets the number of gold bags on this level
	 * 
	 * @param numberOfGoldBagsAtStart
	 */
	public void setNumberOfGoldBagsAtStart(int numberOfGoldBagsAtStart) {
		this.numberOfGoldBagsAtStart = numberOfGoldBagsAtStart;
	}

	/**
	 * Returns the number of starting points of this level
	 * 
	 * @return
	 */
	public int getStartingPoints() {
		return startingPoints;
	}

	/**
	 * sets the number of points to start with on this level
	 * 
	 * @param startingPoints
	 */
	public void setStartingPoints(int startingPoints) {
		this.startingPoints = startingPoints;
	}

}