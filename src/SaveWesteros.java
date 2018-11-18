import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveWesteros extends SearchProblem {

	public SaveWesteros() throws IOException {
		super();
		GenGrid();
//		HardcodedGrid1(); //used for evaluation and comparison
	}

	public void GenGrid() throws IOException {

//		int gridSize = (int) (Math.random() * 10 + 4);
		int gridSize = 4;
		char[][] grid = new char[gridSize][gridSize];

		// Empty grid cells
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid[i][j] = ' ';
			}
		}


		int whiteWalkersNumber;
		boolean isFailureState = false; // initially false
		int maxDragonGlass;
		int currentDragonGlass = 0; // initially 0, Jon needs to go charge
		Position[] obstacles;
		Position dragonStone;
		Position jon;
		boolean jonDead = false; // initially false
		
		// placing Jon at bottom right
		jon = new Position(gridSize - 1, gridSize - 1);
		grid[jon.y][jon.x] = 'J';

		// whitewalkers initialization
		whiteWalkersNumber = (int) (Math.random() * ((Math.pow(gridSize, 2)) - 2));

		for (int i = 0; i < whiteWalkersNumber; i++) {
			while (true) {
				int x = (int) (Math.random() * gridSize);
				int y = (int) (Math.random() * gridSize);
				if (grid[y][x] == ' ') {
					grid[y][x] = 'w';
					break;
				}
			}
		}
		
		// dragonstone and dragonglass initialization
		maxDragonGlass = (int) (Math.random() * whiteWalkersNumber + 1);
		while (true) {
			int x = (int) (Math.random() * gridSize);
			int y = (int) (Math.random() * gridSize);
			if (grid[y][x] == ' ') {
				dragonStone = new Position(x, y);
				grid[y][x] = 'd';
				break;
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
				if (grid[y][x] == ' ') {
					obstacles[i] = new Position(x, y);
					grid[y][x] = 'o';
					break;
				}
			}
		}
		
		// initial state
		this.initState = new WesterosState(grid, isFailureState, whiteWalkersNumber, maxDragonGlass, currentDragonGlass,
								obstacles, dragonStone, jon, jonDead);
		
		for(int i = 0; i < grid.length; i++)
			System.out.println(Arrays.toString(grid[i]));
		
		System.out.println();
		
		WriteInKB(grid, dragonStone,maxDragonGlass);
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
		
		// if Jon is standing on dragonstone, he can charge
		if (grid[jon.y][jon.x] == 'C' && currentState.currentDragonGlass < currentState.maxDragonGlass)
			possibleOperators.add(new WesterosOperator('c', 2));
		
		// For Jon to kill he must have dragonglass(es) and whitewalkers must be adjacent
		boolean adjacentWhiteWalkers = false;
		if (currentState.currentDragonGlass > 0) {

			// check right
			if (jon.x + 1 < gridSize - 1 && grid[jon.y][jon.x + 1] == 'w') {
				adjacentWhiteWalkers = true;
			}
			// check left
			if (jon.x - 1 >= 0 && grid[jon.y][jon.x - 1] == 'w') {
				adjacentWhiteWalkers = true;
			}
			// check down
			if (jon.y + 1 < gridSize - 1 && grid[jon.y + 1][jon.x] == 'w') {
				adjacentWhiteWalkers = true;
			}
			// check up
			if (jon.y - 1 >= 0 && grid[jon.y - 1][jon.x] == 'w') {
				adjacentWhiteWalkers = true;
			}
			int killCost = grid.length * grid.length;
			if (adjacentWhiteWalkers)
				possibleOperators.add(new WesterosOperator('k', killCost)); // TODO: rethink kill cost
		
		}
		int movementCost = 3;
		// boundary and obstacle checks
		// add right
		if (jon.x + 1 < gridSize && grid[jon.y][jon.x + 1] != 'o') {
			possibleOperators.add(new WesterosOperator('r', movementCost));
		}
		// add left
		if (jon.x - 1 >= 0 && grid[jon.y][jon.x - 1] != 'o') {
			possibleOperators.add(new WesterosOperator('l', movementCost));
		}
		// add up
		if (jon.y - 1 >= 0 && grid[jon.y - 1][jon.x] != 'o') {
			possibleOperators.add(new WesterosOperator('u', movementCost));
		}
		// add down
		if (jon.y + 1 < gridSize && grid[jon.y + 1][jon.x] != 'o') {
			possibleOperators.add(new WesterosOperator('d', movementCost));
		}

	

		Operator[] possibleOperatorsArr = new Operator[possibleOperators.size()];
		for (int i = 0; i < possibleOperatorsArr.length; i++) {
			possibleOperatorsArr[i] = possibleOperators.get(i);
		}

		return possibleOperatorsArr;
	}
	public void HardcodedGrid() {
		int gridSize = 4;
		char[][] grid = new char[gridSize][gridSize];
		
		grid[0] = new char []{' ',' ',' ','o'};
		grid[1] = new char []{' ','w',' ',' '};
		grid[2] = new char []{'w','d',' ',' '};
		grid[3] = new char []{' ','w',' ','J'};
		
		Position [] obstacles = new Position [1];
		obstacles[0] = new Position(2,0);
		this.initState = new WesterosState(grid, false, 3, 3, 0, obstacles, new Position(1,2), new Position(3,3), false);
		
	}
	public void HardcodedGrid1() {
		int gridSize = 4;
		char[][] grid = new char[gridSize][gridSize];
		
		grid[0] = new char []{' ','w','o',' '};
		grid[1] = new char []{'w',' ',' ','w'};
		grid[2] = new char []{'o','o','o','d'};
		grid[3] = new char []{' ',' ','o','J'};
		
		Position [] obstacles = new Position [5];
		obstacles[0] = new Position(0,2);
		obstacles[1] = new Position(3,2);
		obstacles[2] = new Position(2,0);
		obstacles[3] = new Position(2,1);
		obstacles[4] = new Position(2,2);
		
		this.initState = new WesterosState(grid, false, 3, 3, 0, obstacles, new Position(3,2), new Position(3,3), false);
		
	}
	
	public static int h1(SearchTreeNode node) {
		return ((WesterosState) node.state).whiteWalkersNumber;
	}

	public static int h2(SearchTreeNode node) {
		if(((WesterosState) node.state).whiteWalkersNumber == 0)
			return 0;
		
		char[][] grid = ((WesterosState) node.state).grid;
		ArrayList<Position> whiteWalkersPos = new ArrayList<Position>();
		int minDist = (int) 1e6;
		int dist;
		
		Position jonPos = ((WesterosState) node.state).jon;
		
		// checking white walkers position to calculate manhattan distance
		for (int i = 0; i < grid.length; i++) {
			for( int j = 0; j < grid.length; j++) {
				if(grid[i][j] == 'w') {
					dist = Math.abs(jonPos.x - j) + Math.abs(jonPos.y - i);
					if(node.operator.action == 'k')
						dist = 1;
					if (dist < minDist)
						minDist = dist;
				}
			}
		}
		
		return minDist;
	}
	
	public int pathCost(SearchTreeNode currentNode) {
		if(currentNode.parent == null)
			return 0;
			
		return currentNode.operator.cost + this.pathCost(currentNode.parent);
	}
	
	public static void WriteInKB(char[][] grid,  Position dragonStone, int maxDragonGlass) throws IOException
	{
		
		String KB = "";
		KB += "max_dimensions(" + grid.length +").\n";
		KB += "jon("+(grid.length - 1) +","+(grid.length - 1)+",0, s0).\n";
		KB += "max_dragon_glass(" + maxDragonGlass +").\n";
		KB += "dragon_stone(" + dragonStone.x + "," + dragonStone.y +").\n";
		
		for(int i = 0; i<grid.length; i++)
		{
			for (int j = 0; j< grid.length; j++)
			{
				if(grid[i][j] == 'w')
				{
					KB += "whitewalker(" + j + "," + i +", s0).\n";
				}
				if(grid[i][j] == 'o')
				{
					KB += "obstacle(" + j + "," + i +").\n";
				}
			}
		}
		System.out.println(KB);
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Nourhan Khaled/Semester 9 Material/AI/KB.txt"));
	    writer.write(KB);
	    writer.close();
		
	}
	
	public static void main(String[] args) throws IOException {
		
		SaveWesteros sw = new SaveWesteros();
		sw.GenGrid();
	}

}
