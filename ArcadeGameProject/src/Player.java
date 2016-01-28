import java.awt.Color;

/**
 * A Player is the GameObject controlled by the player
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public class Player extends GameObject {
	private int counter = 0;
	private final int SPEED = 8;//lower means faster

	/**
	 * Creates a Player initialized to certain coordinates and facing right
	 * 
	 * @param row
	 * @param column
	 */
	public Player(int row, int column) {
		super(row, column);

		this.direction = 0;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public void update(KeyType key) {
		super.update(key);
		
		counter++;
		if (counter < SPEED) {
			return;
		}

		int newRow = this.row;
		int newColumn = this.column;
		int newDirection = 0;

		GameObject object = null;

		switch (key) {
		case UP:
			newRow = newRow - 1;
			newDirection = 3;
			object = getAbove();
			this.positionChanged = true;
			break;

		case DOWN:
			newRow = newRow + 1;
			newDirection = 1;
			object = getBelow();
			this.positionChanged = true;
			break;

		case LEFT:
			newColumn = newColumn - 1;
			newDirection = 2;
			object = getLeft();
			this.positionChanged = true;
			break;

		case RIGHT:
			newColumn = newColumn + 1;
			newDirection = 0;
			object = getRight();
			this.positionChanged = true;
			break;

		default:
			break;
		}

		if (object != null && (newRow != this.row || newColumn != this.column)) {
			if(Gold.class.isAssignableFrom(object.getClass()) && !((Gold) object).getIsBroken()) {
				// Unbroken gold, can't move over
				
				if(((Gold)object).canPush(newDirection)) {
					((Gold)object).moveInDirection(newDirection);
				} else {
					this.positionChanged = false;
					if (counter == SPEED) {
						counter = 0;
					}
					return;
				}
			}
			this.row = newRow;
			this.column = newColumn;
			this.direction = newDirection;
		} else {
			this.positionChanged = false;
		}
		
		if (counter == SPEED) {
			counter = 0;
		}
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	/**
	 * Returns the direction the Player is facing 0 is right, 1 is down, 2 is
	 * left, 3 is up
	 * 
	 * @return
	 */
	@Override
	public int getDirection() {
		return this.direction;
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject,
			boolean initialCheck) {
		System.out.println(this.getClass() + " overlaps "
				+ newObject.getClass());
		if (Monster.class.isAssignableFrom(newObject.getClass())) {
			this.die();
			return newObject;
		} else if (initialCheck) {
			return gameObjectOverlap(newObject, false);
		}

		return null;
	}

	/**
	 * Determines whether this object can move over the specified object
	 * @param gameObject
	 * @return true or false
	 */
	public boolean canMoveOverObject(GameObject gameObject) {
		if (Gold.class.isAssignableFrom(gameObject.getClass())) {
			Gold gold = (Gold) gameObject;
			if(gold.canPush(this.direction)) {
				gold.moveInDirection(this.direction);
				return true;
			}
			return false;
		}

		return true;
	}

	@Override
	public boolean canAnimate() {
		return true;
	}
}
