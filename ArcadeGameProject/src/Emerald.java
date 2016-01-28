import java.awt.Color;

/**
 * An Emerald GameObject is a collectible in ArcadeGame
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 */
public class Emerald extends GameObject {
	
	/**
	 *	Constructs and emerald object on the specified row and column
	 *@param row
	 *@param column
	 */
	public Emerald(int row, int column) {
		super(row, column);
	}
	
	@Override
	public String toString(){
		return "E";
	}
	
	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject,
			boolean initialCheck) {
		return null;
	}

	@Override
	public boolean canAnimate() {
		return false;
	}
}
