import java.util.ArrayList;
import java.util.Arrays;

public class SaveWesteros extends SearchProblem {
		
	public SaveWesteros() {
		super();
		GenGrid();
	}

	public void GenGrid() {

		// grid initialization, TODO:Fix
		int gridSize = (int) (Math.random() * 10 + 4);
		char[][] grid = new char[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid[i][j] = ' ';
			}
		}

		// TODO: will create variables and pass them all to the constructor of westeros
		// state at the end of the method

		int whiteWalkersNumber;
		boolean isFailureState = false; // initially false
		int maxDragonGlass;
		int currentDragonGlass = 0; // initially 0, Jon needs to go charge
//		Position[] whiteWalkersLoc;
		Position[] obstacles;
		Position dragonStone;
		Position jon;
		boolean jonDead = false; // initially false

		jon = new Position(gridSize - 1, gridSize - 1);
		grid[jon.x][jon.y] = 'J';

		// dragonstone and dragonglass initialization
		maxDragonGlass = (int) (Math.random() * 10 + 11); // has maximum of between 10 and 20 pieces of dragonglass
		while (true) {
			int x = (int) (Math.random() * gridSize);
			int y = (int) (Math.random() * gridSize);
			if (grid[x][y] == ' ') {
				dragonStone = new Position(x, y);
				grid[x][y] = 'd';
				break;
			}
		}

		// whitewalkers initialization
		whiteWalkersNumber = (int) (Math.random() * (Math.pow(gridSize, 2) - 2));
//		whiteWalkersLoc = new Position[whiteWalkersNumber];
		for (int i = 0; i < whiteWalkersNumber; i++) {
			while (true) {
				int x = (int) (Math.random() * gridSize);
				int y = (int) (Math.random() * gridSize);
				if (grid[x][y] == ' ') {
//					whiteWalkersLoc[i] = new Position(x, y);
					grid[x][y] = 'w';
					break;
				}
			}
		}

		// obstacles initialization
		int maxObstaclesNumber = (int) ((Math.pow(gridSize, 2) - whiteWalkersNumber) / 2);
		int obstaclesNumber = (int) (Math.random() * (maxObstaclesNumber + 1));
		obstacles = new Position[obstaclesNumber];
		for (int i = 0; i < obstaclesNumber; i++) {
			while (true) {
				int x = (int) (Math.random() * gridSize);
				int y = (int) (Math.random() * gridSize);
				if (grid[x][y] == ' ') {
					obstacles[i] = new Position(x, y);
					grid[x][y] = 'o';
					break;
				}
			}
		}
		this.initState = new WesterosState(grid, isFailureState, whiteWalkersNumber, maxDragonGlass, currentDragonGlass,
//				whiteWalkersLoc, 
				obstacles, dragonStone, jon, jonDead);
	}

	public boolean goalTest(SearchTreeNode currentNode) {
		WesterosState currentState = (WesterosState) currentNode.state;
		return currentState.whiteWalkersNumber == 0;
	}

	public Operator[] getPossibleOperators(State state) {
		WesterosState currentState = (WesterosState) state;
		
		if (currentState.jonDead || currentState.isFailureState)
			return null;
		
		ArrayList<Operator> possibleOperators = new ArrayList<Operator>();
		
		Position jon = currentState.jon;
		char[][] grid = currentState.grid;
		int gridSize = grid.length;
		
		//boundary and obstacle checks
		//add right
		if(jon.x + 1 < gridSize && grid[jon.x + 1][jon.y] != 'o') {
			possibleOperators.add(new WesterosOperator('r', 1));
		}
		//add left
		if(jon.x - 1 >= 0 && grid[jon.x - 1][jon.y] != 'o') {
			possibleOperators.add(new WesterosOperator('l', 1));
		}
		//add up
		if(jon.y - 1 >= 0 && grid[jon.x][jon.y - 1] != 'o') {
			possibleOperators.add(new WesterosOperator('u', 1));
		}
		//add down 
		if(jon.y + 1 < gridSize && grid[jon.x][jon.y + 1] != 'o') {
			possibleOperators.add(new WesterosOperator('d', 1));
		}
		
		//if Jon is standing on dragonstone, he can charge
		if(grid[jon.x][jon.y] == 'd') 
			possibleOperators.add(new WesterosOperator('c', 0));
		
		// For Jon to kill he must have dragonglass(es) and whitewalkers must be adjacent
		boolean adjacentWhiteWalkers = false;
		if(currentState.currentDragonGlass > 0) {
			
			//check right
			if(jon.x + 1 < gridSize - 1 && grid[jon.x + 1][jon.y] == 'w') {
				adjacentWhiteWalkers = true;
			}
			//check left
			if(jon.x - 1 >= 0 && grid[jon.x - 1][jon.y] == 'w') {
				adjacentWhiteWalkers = true;
			}
			//check up
			if(jon.y + 1 < gridSize - 1 && grid[jon.x][jon.y + 1] == 'w') {
				adjacentWhiteWalkers = true;
			}
			//check down
			if(jon.y - 1 >= 0 && grid[jon.x][jon.y - 1] == 'w') {
				adjacentWhiteWalkers = true;
			}
			
			if(adjacentWhiteWalkers)
				possibleOperators.add(new WesterosOperator('k', 5)); //TODO: rethink kill cost

		}
		
		
		return null;
	}

	public static void main(String[] args) {
		SaveWesteros sw = new SaveWesteros();
		char[][] grid = sw.initState.grid;

		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}

	}
}
