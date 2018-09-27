
public class SaveWestros extends SearchProblem {
	
	int whiteWalkersNumber;
	int maxDragonGlass;
	int countDragonGlass;
	int currentDragonGlass;
	Position[] whiteWalkersLoc;
	Position[] obstacles;
	Position dragonStone;
	Position jon;
	boolean jonDead;	
	
	public SaveWestros() {
		
		GenGrid();
	}
	
	public void GenGrid()
	{
		//grid initialization, TODO:Fix
		int gridSize = (int) (Math.random() * 10 + 4);
		char[][] grid = new char[gridSize][gridSize];
		currentState.grid = grid;
		
		//dragonstone and dragonglass initialization
		maxDragonGlass = (int) (Math.random() * 10 + 11); //has maximum of between 10 and 20 pieces of dragonglass
		while (true) {
			int x = (int) (Math.random() * gridSize);
			int y = (int) (Math.random() * gridSize);
			if (grid[x][y] == ' ') {
				dragonStone = new Position(x,y);
				grid[x][y] = 'd';
				break;
			}
		}
		
		//whitewalkers initialization
		whiteWalkersNumber = (int) (Math.random() * (Math.pow(gridSize, 2) - 2));
		whiteWalkersLoc = new Position[whiteWalkersNumber];
		for(int i = 0; i < whiteWalkersNumber; i++) {
			while (true) {
				int x = (int) (Math.random() * gridSize);
				int y = (int) (Math.random() * gridSize);
				if (grid[x][y] == ' ') {
					whiteWalkersLoc[i] = new Position(x, y);
					grid[x][y] = 'w';
					break;
				}
			}				
		}
		
		
		
		
	}

	@Override
	public boolean goalTest() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Operator[] getPossibleOperators() {
		
	}

}
