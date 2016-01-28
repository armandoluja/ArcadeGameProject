import java.awt.Color;

public class Gold extends GameObject {

	private static final int SPEED = 8;
	private boolean isShaking;
	private int shakingCount;

	private boolean isFalling;
	private int blocksFallen;

	private boolean isBroken;

	private final int BREAKING_FALL_DISTANCE = 2;
	private int counter =0;

	public Gold(int row, int column) {
		super(row, column);
	}

	@Override
	public void update(KeyType key) {
		super.update(key);
		counter ++;
		if (counter < SPEED) {
			return;
		}

		GameObject belowGameObject = getBelow();

		if (belowGameObject != null
				&& EmptyBlock.class.isAssignableFrom(getBelow().getClass())) {
			if (!this.isBroken) {
				if (!this.isShaking && !this.isFalling) {
					this.isShaking = true;
				} else if (this.isShaking && !this.isFalling) {
					if (this.shakingCount > 2) {
						this.shakingCount = 0;
						this.isFalling = true;
					} else {
						this.shakingCount++;
					}
				}
			} else {
				this.isFalling = true;
				this.direction = 1;
			}

			if (this.isFalling) {
				this.direction = 1;
				this.blocksFallen++;
				this.row++;
				this.positionChanged = true;
			}

		} else if (isFalling) {
			if (belowGameObject != null
					&& (Player.class.isAssignableFrom(getBelow().getClass())
					|| Monster.class.isAssignableFrom(getBelow().getClass())
					|| Gold.class.isAssignableFrom(getBelow().getClass()))) {
				getBelow().die();
				if (counter == SPEED) {
					counter = 0;
				}
				return;
			}

			this.isFalling = false;

			if (this.blocksFallen >= BREAKING_FALL_DISTANCE) {
				this.isBroken = true;
			} else {
				this.blocksFallen = 0;
			}
		}
		
		if (counter == SPEED) {
			counter = 0;
		}
	}

	@Override
	public String toString() {
		return "G";
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject,
			boolean initialCheck) {
		if (Monster.class.isAssignableFrom(newObject.getClass())) {
			if (this.isFalling) {
				newObject.die();
			}
			return this;
		}

		if (Player.class.isAssignableFrom(newObject.getClass())) {
			if (this.isBroken) {
				return newObject;
			} else if (this.isFalling) {
				newObject.die();
				return this;
			}
		}

		return null;
	}

	@Override
	public Color getColor() {
		if (this.isBroken) {
			return Color.orange;
		}
		if (this.shakingCount == 0) {
			return Color.yellow;
		} else if (this.shakingCount == 1) {
			return Color.orange;
		} else {
			return Color.red;
		}
	}

	/**
	 * Returns true if the Gold GameObject can be pushed in a certain direction.
	 * 0 is right, 1 is down, 2 is left, 3 is up.
	 * 
	 * @param direction
	 * @return
	 */
	public boolean canPush(int direction) {
		switch (direction) {
		case 0:
			return canMoveOverObject(getRight());
		case 1:
			return canMoveOverObject(getBelow());
		case 2:
			return canMoveOverObject(getLeft());
		case 3:
			return canMoveOverObject(getAbove());
		}

		return false;
	}

	/**
	 * Moves the Gold in a given direction (for pushing)
	 * 
	 * @param direction
	 */
	public void moveInDirection(int direction) {
		switch (direction) {
		case 0:
			this.column++;
			this.direction = 0;
			this.positionChanged = true;
			break;
		case 1:
			this.row++;
			this.direction = 1;
			this.positionChanged = true;
			break;
		case 2:
			this.column--;
			this.direction = 2;
			this.positionChanged = true;
			break;
		case 3:
			this.row--;
			this.direction = 3;
			this.positionChanged = true;
			break;
		}

		this.level.addGameObject(this);
	}

	private boolean canMoveOverObject(GameObject gameObject) {
		if (gameObject == null) {
			return false;
		}

		if (EmptyBlock.class.isAssignableFrom(gameObject.getClass())) {
			return true;
		}

		return false;
	}

	/**
	 * Returns true if Gold GameObject is in the broken state
	 * 
	 * @return
	 */
	public boolean getIsBroken() {
		return this.isBroken;
	}
	
	@Override
	public void die() {
		
	}

	@Override
	public boolean canAnimate() {
		return true;
	}
}
