import java.awt.Color;

/**
 * A Dirt GameObject represents diggable material in ArcadeGame
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public class Dirt extends GameObject {
	public Dirt(int row, int column) {
		super(row, column);
	}
	
	@Override
	public String toString(){
		return "D";
	}
	
	@Override
	public void update(KeyType key) {
		
	}
	@Override
	public Color getColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public GameObject gameObjectOverlap(GameObject newObject, boolean initialCheck) {
		// Do nothing
		return null;
	}

	@Override
	public boolean canAnimate() {
		return false;
	}
}
