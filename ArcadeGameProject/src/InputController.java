import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


/**
 * Keeps track of what button is pressed 
 * Keys U and P switch levels 
 * Up, down, left, right arrow keys are for movement
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 * 
 */

public class InputController implements KeyListener {
	private ArrayList<KeyType> pressedKeys;
	Player player;
	
	/**
	 * Creates a new input controller
	 */
	public InputController() {
		this.pressedKeys = new ArrayList<KeyType>();
	}

	@Override
	public void keyPressed(KeyEvent key) {		
		int keyPressed = key.getKeyCode();
		
		switch (keyPressed) {
			case KeyEvent.VK_UP:
				addKeyToArray(KeyType.UP);
				break;
				
			case KeyEvent.VK_DOWN:
				addKeyToArray(KeyType.DOWN);
				break;
				
			case KeyEvent.VK_LEFT:
				addKeyToArray(KeyType.LEFT);
				break;
				
			case KeyEvent.VK_RIGHT:
				addKeyToArray(KeyType.RIGHT);
				break;
				
			case KeyEvent.VK_U:
				addKeyToArray(KeyType.U);
				break;
				
			case KeyEvent.VK_D:
				addKeyToArray(KeyType.D);
				break;
				
			case KeyEvent.VK_P:
				addKeyToArray(KeyType.P);
				break;
				
			case KeyEvent.VK_SPACE:
				addKeyToArray(KeyType.SPACE);
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * Adds key to the end of the pressedKeys array
	 * 
	 * @param key
	 */
	private void addKeyToArray(KeyType key) {
		if(pressedKeys.contains(key)) {
			pressedKeys.remove(key);
		}
		
		pressedKeys.add(key);
	}

	/**
	 * Removes a key from the pressedKeys array
	 * 
	 * @param key
	 */
	private void removeKeyFromArray(KeyType key) {
		if(pressedKeys.contains(key)) {
			pressedKeys.remove(key);
		}
	}
	
	/**
	 * Returns the most recently pressed (and currently still pressed) key
	 * @return
	 */
	public KeyType getCurrentKey() {
		@SuppressWarnings("unchecked")
		ArrayList<KeyType> array = (ArrayList<KeyType>) this.pressedKeys.clone();
		int length = array.size();
		if(length > 0) {
			try{
				return array.get(length-1);
			}catch(IndexOutOfBoundsException e){
				return KeyType.EMPTY_KEY;
			}
		}
		return KeyType.EMPTY_KEY;
	}
	
	@Override
	public void keyReleased(KeyEvent key) {
		int keyPressed = key.getKeyCode();
		
		switch (keyPressed) {
			case KeyEvent.VK_UP:
				removeKeyFromArray(KeyType.UP);
				break;
				
			case KeyEvent.VK_DOWN:
				removeKeyFromArray(KeyType.DOWN);
				break;
				
			case KeyEvent.VK_LEFT:
				removeKeyFromArray(KeyType.LEFT);
				break;
				
			case KeyEvent.VK_RIGHT:
				removeKeyFromArray(KeyType.RIGHT);
				break;
				
			case KeyEvent.VK_U:
				removeKeyFromArray(KeyType.U);
				break;
				
			case KeyEvent.VK_D:
				removeKeyFromArray(KeyType.D);
				break;
				
			case KeyEvent.VK_P:
				removeKeyFromArray(KeyType.P);
				break;
			
			case KeyEvent.VK_SPACE:
				removeKeyFromArray(KeyType.SPACE);
				break;
				
			default:
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
	}

}
