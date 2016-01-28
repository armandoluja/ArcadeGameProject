import java.util.ArrayList;
import java.util.Random;


/**
 * A MonsterController handles the spawning and operation of monsters
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 *
 */
public class MonsterController {
	private int spawnerRow;
	private int spawnerColumn;
	private int maxSpawned;
	private Level level;
	
	private ArrayList<GameObject> monsters;
	
	private Random randomizer;
	private int tickCount =0;
	
	public MonsterController(int row, int column) {
		this.spawnerRow = row;
		this.spawnerColumn = column;
		
		this.monsters = new ArrayList<GameObject>();
		
		this.randomizer = new Random(483208340);
	}
	
	/**
	 * Randomly spawns new Monsters and changes Monster types
	 * @param player
	 */
	public void update(Player player) {
		if(this.tickCount == 0){
			if(this.monsters.size() < maxSpawned) {
				if(this.randomizer.nextFloat() > .9) {
//					System.out.println("Spawning new mob");
					spawnMonster();
					
				}
			}
					
			if(this.randomizer.nextFloat() > .95 && this.monsters.size() > 0) {
//						System.out.println("Swaping mob");
				int index = this.randomizer.nextInt(this.monsters.size());
				Monster monster = (Monster)this.monsters.get(index);
					
				Monster newMonster = null;
					
				if(Hobbin.class.isAssignableFrom(monster.getClass())) {
					newMonster = new Nobbin(monster.getRow(), monster.getColumn(), this);
				} else {
					newMonster = new Hobbin(monster.getRow(), monster.getColumn(), this);
				}
					
				monster.die();
					
				newMonster.setLevel(this.level);
						
				this.monsters.add(newMonster);
						
				this.level.addGameObject(newMonster);
			}	
		} else if(this.tickCount > 5){
			this.tickCount = -1;
		}
		
		this.tickCount++;
		
	}
	
	/**
	 * Spawn a randomly chosen Monster
	 */
	private void spawnMonster() {
		if(this.level.objectAtIndexes(this.spawnerRow, this.spawnerColumn) == null) {
			return;
		}
		
		
		Monster monster;
		
		if(this.randomizer.nextFloat() > .7) {
			monster = new Nobbin(this.spawnerRow, this.spawnerColumn, this);
		} else {
			monster = new Hobbin(this.spawnerRow, this.spawnerColumn, this);
		}
		
		monster.setLevel(this.level);
		
		this.level.addGameObject(monster);
		this.monsters.add(monster);
	}
	
	/**
	 * Sets the maximum number of Monsters that can be spawned at a given time
	 * @param maxSpawned
	 */
	public void setMaxSpawned(int maxSpawned) {
		this.maxSpawned = maxSpawned;
	}
	
	/**
	 * Sets the Level
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	/**
	 * Returns the Level
	 * @return
	 */
	public Level getLevel() {
		return this.level;
	}
	
	/**
	 * Removes a currently spawned Monster
	 * @param object
	 */
	public void removeSpawned(GameObject object) {
		this.monsters.remove(object);
	}
	
	/**
	 * Resets the MonsterController
	 */
	public void reset() {
		this.monsters = new ArrayList<GameObject>();
	}
}
