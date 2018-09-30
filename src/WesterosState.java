
public class WesterosState extends State {

	int whiteWalkersNumber;
	int maxDragonGlass;
	int currentDragonGlass;
	Position[] obstacles;
	Position dragonStone;
	Position jon;
	boolean jonDead;

	public WesterosState(char[][] grid, boolean isFailureState, int whiteWalkersNumber, int maxDragonGlass,
			int currentDragonGlass, Position[] obstacles, Position dragonStone, Position jon, boolean jonDead) {

		super(grid, isFailureState);

		this.whiteWalkersNumber = whiteWalkersNumber;
		this.maxDragonGlass = maxDragonGlass;
		this.currentDragonGlass = currentDragonGlass;
		this.obstacles = obstacles;
		this.dragonStone = dragonStone;
		this.jon = jon;
		this.jonDead = jonDead; // false
	}

}
