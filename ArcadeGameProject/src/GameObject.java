import java.awt.Color;

/**
 * A GameObject is the base object of the game.  Every tile
 * of the Level is a GameObject
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public abstract class GameObject {
	
	protected int column;
	protected int row;
	
	/**
	 * 0 is right, 1 is down, 2 is left, 3 is up
	 */
	protected int direction;
	
	protected boolean shouldAnimate;
	protected int animateX;
	protected int animateY;
	
	protected Level level;
	
	protected GameObject previousObject;
	
	protected boolean isAlive = true;
	
	protected boolean positionChanged;
	protected boolean disableUpdates;
		
	public abstract String toString();
	
	/**
	 * Contructs a game object on the specified row and column
	 * @param row
	 * @param column
	 */
	public GameObject(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns the current row of the GameObject
	 * @return
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Sets the current row of the GameObject
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Returns the current column of the GameObject
	 * @return
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Sets the current column of the GameObject
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Updates any properties as needed between game ticks
	 * @param key
	 */
	public void update(KeyType key) {
		this.positionChanged = false;
		this.disableUpdates = true;
	}
	
	/**
	 * Resets positionChanged to false
	 */
	public void resetPositionChanged() {
		this.positionChanged = false;
	}
	
	/**
	 * Returns true if the position of the GameObject has changed
	 * since the last update()
	 * @return
	 */
	public boolean didPositionChange() {
		return this.positionChanged;
	}
	
	/**
	 * Sets the disableUpdates property.
	 * If true, this GameObject will not update when update()
	 * is next called
	 * @param disable
	 */
	public void setDisableUpdates(boolean disable) {
		this.disableUpdates = disable;
	}
	
	/**
	 * Returns the value of the disableUpdates field
	 * @return
	 */
	public boolean isUpdateDisabled() {
		return this.disableUpdates;
	}
	
	/**
	 * Check and handle the overlap of GameObjects
	 * Always set initialCheck to true.  It will only be false if it's called recusively
	 * 
	 * @param newObject
	 * @param initialCheck
	 */
	public abstract GameObject gameObjectOverlap(GameObject newObject, boolean initialCheck);
		
	/**
	 * Returns the color of the GameObject
	 * @return
	 */
	public abstract Color getColor();
	
	/**
	 * Removes this object from the game
	 */
	public void die() {
		this.isAlive = false;
	}
	
	/** 
	 * Returns true if the GameObject is alive
	 * @return
	 */
	public boolean getIsAlive() {
		return this.isAlive;
	}
	
	/**
	 * Sets the GameObject to alive
	 */
	public void setAlive() {
		this.isAlive = true;
	}
	
	/**
	 * Sets the Level the GameObject is contained in
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * Returns the object to the left of this.
	 * @return
	 */
	public GameObject getLeft() {
		if (this.getColumn() == 0) {
			return null;
		}
		return this.level.objectAtIndexes(this.row, this.column - 1);
	}

	/**
	 * Returns the object to the right of this.
	 * @return
	 */
	public GameObject getRight() {
		if (this.getColumn() == this.level.getSize().width -1) {
			return null;
		}
		return this.level.objectAtIndexes(this.row, this.column + 1);
	}

	/**
	 * Returns the object below this.
	 * @return
	 */
	public GameObject getBelow() {
		if (this.getRow() == this.level.getSize().height-1) {
			return null;
		}
		return this.level.objectAtIndexes(this.row + 1, this.column);
	}

	/**
	 * Returns the object above this.
	 * @return
	 */
	public GameObject getAbove() {
		if (this.getRow() == 0) {
			return null;
		}
		return this.level.objectAtIndexes(this.row - 1, this.column);
	}
	
	/**
	 * Returns the current direction of the GameObject.
	 * 0 is right, 1 is down, 2 is left, 3 is up.
	 * @return
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * set whether or not this object is animated
	 * @param animate
	 */
	public void setShouldAnimate(boolean animate) {
		this.shouldAnimate = animate;
	}
	
	/**
	 * gets whether or not this object is animated
	 * @return true or false
	 */
	public boolean shouldAnimate() {
		return this.shouldAnimate;
	}
	
	/**
	 * sets the horizonatal value of animation
	 * @param animateX
	 */
	public void setAnimateX(int animateX) {
		this.animateX = animateX;
	}
	
	/**
	 * gets the horizontal value of animation
	 * @return
	 */
	public int getAnimateX() {
		return this.animateX;
	}
	
	/**
	 * sets the vertical value of animation
	 * @param animateY
	 */
	public void setAnimateY(int animateY) {
		this.animateY = animateY;
	}
	
	/**
	 * gets the vertical value of animation
	 * @return
	 */
	public int getAnimateY() {
		return this.animateY;
	}
	
	/**
	 * Sets the GameObject that previously occupied the tile now occupied by this GameObject
	 * @param gameObject
	 */
	public void setPreviousObject(GameObject gameObject) {
		this.previousObject = gameObject;
	}
	
	/**
	 * Returns the GameObject that previously occupied the tile now occupied by this GameObject
	 * @return
	 */
	public GameObject getPreviousObject() {
		return this.previousObject;
	}
	
	/**
	 * Returns true if the GameObject is animatable
	 * @return
	 */
	public abstract boolean canAnimate();
}
