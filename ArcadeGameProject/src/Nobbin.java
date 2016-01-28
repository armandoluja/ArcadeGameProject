import java.awt.Color;


public class Nobbin extends Monster {
	
	/**
	 * Contructs a nobbin object at the specified row and column, with the given monsterController object
	 * @param row
	 * @param column
	 * @param monsterController
	 */
	public Nobbin(int row, int column, MonsterController monsterController) {
		super(row, column, monsterController);
		this.monsterController= monsterController;
	}
	MonsterController monsterController;
	
		
	@Override
	public Color getColor() {
		return Color.blue;
	}
	@Override
	public String toString() {
		return "N";
	}
}
