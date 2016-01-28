import java.awt.Color;

public class Monster extends GameObject {

	private static final int SPEED = 8;
	private MonsterController monsterController;
	protected int currentDirection = 0;
	private int counter;

	public Monster(int row, int column, MonsterController monsterController) {
		super(row, column);
		this.monsterController = monsterController;
	}

	@Override
	public String toString() {
		return "M";
	}

	@Override
	public Color getColor() {
		return Color.blue;
	}

	@Override
	public void update(KeyType key) {
		super.update(key);
		counter++;
		if (counter < SPEED) {
			return;
		}
		if (this.currentDirection == 0) {
			this.checkForWalls();
		} else if (this.currentDirection == 1) {
			if (canMoveOverObject(getLeft())) {
				this.setColumn(this.getColumn() - 1);
				this.direction = 2;
				this.positionChanged = true;
			} else {
				this.checkForWalls();
			}
		} else if (this.currentDirection == 2) {
			if (canMoveOverObject(getRight())) {
				this.setColumn(this.getColumn() + 1);
				this.direction = 0;
				this.positionChanged = true;
			} else {
				this.checkForWalls();
			}
		} else if (this.currentDirection == 3) {
			if (canMoveOverObject(getBelow())) {
				this.setRow(this.getRow() + 1);
				this.direction = 1;
				this.positionChanged = true;
			} else {
				this.checkForWalls();
			}
		} else if (this.currentDirection == 4) {
			if (canMoveOverObject(getAbove())) {
				this.setRow(this.getRow() - 1);
				this.direction = 3;
				this.positionChanged = true;
			} else {
				this.checkForWalls();
			}
		}

		this.checkForWalls();
		if (counter == SPEED) {
			counter = 0;
		}
	}

	/**
	 * checks for walls, and changes the monsters direction accordinly
	 */
	public void checkForWalls() {
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		if (canMoveOverObject(getLeft())) {
			left = true;
			this.currentDirection = 1; // left
		}
		if (canMoveOverObject(getRight())) {
			this.currentDirection = 2; // right
			right = true;
		}
		if (canMoveOverObject(getBelow())) {
			down = true;
			this.currentDirection = 3; // down
		}
		if (canMoveOverObject(getAbove())) {
			up = true;
			this.currentDirection = 4; // up
		}
		if (!left && !right && !down && !up) {
			this.currentDirection = 0;
			return;
		}

		if (this.monsterController.getLevel().getPlayer().getRow() == this.row) {
			if (this.monsterController.getLevel().getPlayer().getColumn() == this.column) {
				// this.monsterController.getLevel().setPlayer(new Player(0,0));
				// this.level.isPaused = true; // should try something like this
				// TODO
			} else if (this.monsterController.getLevel().getPlayer()
					.getColumn() < this.column
					&& canMoveOverObject(getLeft())) {
				this.currentDirection = 1; // left
				if (this.getClass().equals(Nobbin.class)) {
				}
				return;
			} else if (right) {
				this.currentDirection = 2;
				if (this.getClass().equals(Nobbin.class)) {
				}
				return; // right
			}
		}

		if (this.monsterController.getLevel().getPlayer().getColumn() == this.column) {
			if (this.monsterController.getLevel().getPlayer().getRow() < this.row
					&& up) {
				this.currentDirection = 4;
				if (this.getClass().equals(Nobbin.class)) {
				}
				return; // up
			} else if (down) {
				this.currentDirection = 3;
				if (this.getClass().equals(Nobbin.class)) {
				}
				return; // down
			}
		}
	}

	public boolean canMoveOverObject(GameObject gameObject) {
		if (gameObject == null) {
			return false;
		}

		if (Hobbin.class.isAssignableFrom(this.getClass())
				|| Dirt.class.isAssignableFrom(gameObject.getClass())) {
			return false;
		}

		if (Emerald.class.isAssignableFrom(gameObject.getClass())
				|| Monster.class.isAssignableFrom(gameObject.getClass())
				|| Gold.class.isAssignableFrom(gameObject.getClass())) {
			return false;
		}

		return true;
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject,
			boolean initialCheck) {
		System.out.println(this.getClass() + " overlaps "
				+ newObject.getClass());
		if (Player.class.isAssignableFrom(newObject.getClass())) {
			newObject.die();
			return this;
		} else if(Projectile.class.isAssignableFrom(newObject.getClass())) {
			this.die();
			newObject.die();
		} else if (initialCheck) {
			return newObject.gameObjectOverlap(newObject, false);
		}

		return null;
	}

	@Override
	public void die() {
		this.isAlive = false;
		this.monsterController.removeSpawned(this);
	}

	@Override
	public boolean canAnimate() {
		return true;
	}
}
