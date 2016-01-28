import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Parses a txt file into a game level
 * 
 * @author Adam Gastineau, Zachary Schafer, and Armando Luja Salmeron
 */
public class LevelLoader {
	
	private final String[] PARSEABLE_TOKENS = { "0", "1", "2", "3","4","5"};
	private final String DIRT_TYPE = "0";
	private final String EMERALD_TYPE = "1";
	private final String PLAYER_TYPE = "2";
	private final String EMPTY_TYPE = "3";
	private final String SPAWNER_TYPE = "4";
	private final String GOLD_TYPE = "5";
	private ArrayList<Monster> monsters;
	private Player player;
	
	final protected int ROW_SIZE = 18;
	final protected int COL_SIZE = 24;
		
	private final String[] LEVELS = {"levelone", "leveltwo", "levelthree"};

	/**
	 * Loads a Level from a given file
	 * @param levelName
	 * @return
	 */
	public Level loadLevel(String levelName) {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		this.monsters = monsters;
		int numberOfEmeralds = 0;
		int numberOfGoldBags = 0;
		File inputFile = new File(levelName);
		GameObject[][] objectArray = new GameObject[ROW_SIZE][COL_SIZE];
		
		MonsterController spawner = null;
		Level loadedLevel = new Level();
		
		Scanner in = null;
		ArrayList<String> strings = new ArrayList<>();
		try {
			in = new Scanner(inputFile);
			in.useDelimiter("");
			while (in.hasNext()) {
				String nextVal = in.next();
				if (Arrays.asList(PARSEABLE_TOKENS).contains(nextVal)) {
					strings.add(nextVal);
				}

			}
			in.close();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		int j = 0;
		int jCount = 0;
		for (int i = 0; i < strings.size(); i++) {
			if(strings.get(i).equals(DIRT_TYPE)) {
				GameObject object = new Dirt(j, i%COL_SIZE);
				object.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE]= object;
				
			} else if(strings.get(i).equals(EMERALD_TYPE)) {
				GameObject object = new Emerald(j, i%COL_SIZE);
				object.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE]= object;

				numberOfEmeralds += 1;
				
			} else if(strings.get(i).equals(PLAYER_TYPE)) {
				Player p1 = new Player(j, i%COL_SIZE);
				p1.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE] = p1;
				this.player = p1;
				
			} else if(strings.get(i).equals(EMPTY_TYPE)) {
				GameObject object = new EmptyBlock(j, i%COL_SIZE);
				object.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE]= object;
				
			} else if(strings.get(i).equals(SPAWNER_TYPE)) {
				spawner = new MonsterController(j, i%COL_SIZE);
				GameObject object = new EmptyBlock(j, i%COL_SIZE);
				object.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE]= object;
				
			} else if(strings.get(i).equals(GOLD_TYPE)){
				numberOfGoldBags ++;
				GameObject object = new Gold(j, i%COL_SIZE);
				object.setLevel(loadedLevel);
				objectArray[j][i%COL_SIZE]= object;
			}
			jCount++;
			if(jCount==COL_SIZE){
				j++;
				jCount=0;
			}
			
		}
		
		loadedLevel.setObjectArray(objectArray);
		loadedLevel.setLevelName(levelName);
		
		if(spawner != null) {
			loadedLevel.setPlayer(this.player);
			spawner.setLevel(loadedLevel);
			if(loadedLevel.getLevelNumber() < 2){
				spawner.setMaxSpawned(5);
			}else if(loadedLevel.getLevelNumber()< 4){
				spawner.setMaxSpawned(8);
			}
			loadedLevel.setMonsterController(spawner);
		}
		loadedLevel.setEmeraldCount(numberOfEmeralds);
		loadedLevel.setNumberOfEmeraldsAtStart(numberOfEmeralds);
		loadedLevel.setNumberOfGoldBagsAtStart(numberOfGoldBags);
		return loadedLevel;
	}

	/**
	 * Loads and returns the next Level
	 * @param currentLevel
	 * @return
	 */
	public Level loadNextLevel(Level currentLevel) {
		int i = indexOfLevel(currentLevel.getLevelName());
		
		if(i == -1) {
			System.out.println("Level not found");
			return currentLevel;
		}
		
		i++;
		
		if(i >= LEVELS.length) {
			i = 0;
		}
		
		return loadLevel(LEVELS[i]);
	}
	
	/**
	 * Loads and returns the previous Level
	 * @param currentLevel
	 * @return
	 */
	public Level loadPreviousLevel(Level currentLevel) {
		int i = indexOfLevel(currentLevel.getLevelName());
		
		if(i == -1) {
			System.out.println("Level not found");
			return currentLevel;
		}
		
		i--;
		
		if(i < 0) {
			i = LEVELS.length-1;
		}
		
		return loadLevel(LEVELS[i]);
	}
	
	/**
	 * Returns the index of the Level based on its name
	 * @param levelName
	 * @return
	 */
	private int indexOfLevel(String levelName) {
		for(int i = 0; i<LEVELS.length; i++) {
			if(LEVELS[i] == levelName) {
				return i;
			}
		}
		
		return -1;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}
}
