import java.awt.Color;

public class Projectile extends GameObject {
	private static final int SPEED = 1;//projectiles are twice as fast as everything else, with respect to this constant
	private int counter;

	/**
	 * Contructs a projectile at the specified row and column, that moves in the specified direction
	 * @param row
	 * @param column
	 * @param direction
	 */
	public Projectile(int row, int column, int direction) {
		super(row, column);

		this.direction = direction;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public Color getColor() {
		return Color.BLACK;
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

		GameObject object = null;

		switch (direction) {
		case 0:
			newColumn++;
			object = getRight();
			this.positionChanged = true;
			break;

		case 1:
			newRow++;
			object = getBelow();
			this.positionChanged = true;
			break;

		case 2:
			newColumn--;
			object = getLeft();
			this.positionChanged = true;
			break;

		case 3:
			newRow--;
			object = getAbove();
			this.positionChanged = true;
			break;

		default:
			break;
		}

		if (object != null && (newRow != this.row || newColumn != this.column)) {
			if (Dirt.class.isAssignableFrom(object.getClass())
					|| Gold.class.isAssignableFrom(object.getClass())
					|| Emerald.class.isAssignableFrom(object.getClass())) {
				this.die();
				this.positionChanged = false;
				if (counter == SPEED) {
					counter = 0;
				}
				return;
			} else if(Monster.class.isAssignableFrom(object.getClass())) {
				this.die();
				object.die();
				if (counter == SPEED) {
					counter = 0;
				}
				return;
			}
			this.row = newRow;
			this.column = newColumn;
		} else {
			this.die();
			this.positionChanged = false;
		}
		if (counter == SPEED) {
			counter = 0;
		}
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject,
			boolean initialCheck) {
		if (Monster.class.isAssignableFrom(newObject.getClass())) {
			this.die();
			newObject.die();
			return this;
		}

		if (Dirt.class.isAssignableFrom(newObject.getClass())
				|| Emerald.class.isAssignableFrom(newObject.getClass())
				|| Gold.class.isAssignableFrom(newObject.getClass())) {
			return newObject;
		}

		return newObject;
	}

	@Override
	public boolean canAnimate() {
		return true;
	}
}
