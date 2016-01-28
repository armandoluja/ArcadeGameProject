import java.awt.Color;

public class Hobbin extends Monster {
	private static final int SPEED = 8;
	private int counter;

	public Hobbin(int row, int column, MonsterController monsterController) {
		super(row, column, monsterController);
		this.monsterController = monsterController;
	}

	MonsterController monsterController;

	@Override
	public void update(KeyType key) {
		this.positionChanged = false;
		this.disableUpdates = true;
		counter++;
		if (counter < SPEED) {
			return;
		}
		Player player = this.monsterController.getLevel().getPlayer();
		int col = player.getColumn();
		int row = player.getRow();
		if (this.column < col && canMoveOverObject(getRight())) {
			this.column += 1;
			this.direction = 0;
			this.positionChanged = true;
		} else if (this.column > col && canMoveOverObject(getLeft())) {
			this.column -= 1;
			this.direction = 2;
			this.positionChanged = true;
		} else if (this.column == col && canMoveOverObject(getBelow())) {
			if (this.row < row) {
				this.row += 1;
				this.direction = 1;
				this.positionChanged = true;
			} else if (this.row > row && canMoveOverObject(getAbove())) {
				this.row -= 1;
				this.direction = 3;
				this.positionChanged = true;
			}
		}

		if (counter == SPEED) {
			counter = 0;
		}

	}

	public boolean canMoveOverObject(GameObject gameObject) {
		if(gameObject == null) {
			return false;
		}
		if (Monster.class.isAssignableFrom(gameObject.getClass())) {
			return false;
		}

		return true;
	}

	@Override
	public Color getColor() {
		return Color.CYAN;
	}

	@Override
	public String toString() {
		return "P";
	}
}
