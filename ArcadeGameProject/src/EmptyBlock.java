import java.awt.Color;

/**
 * An EmptyBlock GameObject represents air
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public class EmptyBlock extends GameObject {	
	
	/**
	 * Contructs and Empty Block object on the specified row and column
	 * @param row
	 * @param column
	 */
	public EmptyBlock(int row, int column) {
		super(row, column);
	}
	
	@Override
	public String toString() {
		return "E";
	}

	@Override
	public void update(KeyType key) {
		
	}

	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject, boolean initialCheck) {
		return null;
	}

	@Override
	public boolean canAnimate() {
		return false;
	}
}
