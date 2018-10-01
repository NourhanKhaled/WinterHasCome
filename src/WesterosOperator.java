
public class WesterosOperator extends Operator {

	public WesterosOperator(char action, int cost) {
		super(action, cost);
	}

	public State transitionFunction(State parentState) {

		// initializing new state variables

		// initializing new grid
		WesterosState parent = (WesterosState) parentState;
		char[][] grid = parent.grid;
		int size = grid[0].length;
		char[][] newGrid = new char[size][size];

		boolean isFailureState = false;
		int whiteWalkersNumber = parent.whiteWalkersNumber;
		int currentDragonGlass = parent.currentDragonGlass;
		boolean jonDead = false;

		// Jon coordinates
		Position jon = new Position(parent.jon.x, parent.jon.y);

		// Cloning grid and locating Jon
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newGrid[i][j] = grid[i][j];
				if (grid[i][j] == 'J') {
					jon.x = i;
					jon.y = j;
				}
			}
		}
		// New Jon coordinates
		Position newJon = new Position(jon.x, jon.y);

		// getting a new state from operator

		boolean stateChanged = false;

		switch (this.action) {
		case 'u': {
			newJon.y--;
			break;
		}
		case 'd': {
			newJon.y++;
			break;
		}
		case 'l': {
			newJon.x--;
			break;
		}
		case 'r': {
			newJon.x++;
			break;
		}
		case 'k': {
			// TODO: keep ds when jon leaves
			// locating stabbed walkers and obliterating them
			boolean usedDragonGlass = false;
			if (jon.x + 1 < size && grid[jon.x + 1][jon.y] == 'w') {
				newGrid[jon.x + 1][jon.y] = ' ';
				usedDragonGlass = true;
				whiteWalkersNumber--;
			}
			if (jon.x - 1 >= 0 && grid[jon.x - 1][jon.y] == 'w') {
				newGrid[jon.x - 1][jon.y] = ' ';
				usedDragonGlass = true;
				whiteWalkersNumber--;
			}
			if (jon.y + 1 < size && grid[jon.x][jon.y + 1] == 'w') {
				newGrid[jon.x][jon.y + 1] = ' ';
				usedDragonGlass = true;
				whiteWalkersNumber--;
			}
			if (jon.y - 1 >= 0 && grid[jon.x][jon.y - 1] == 'w') {
				newGrid[jon.x][jon.y - 1] = ' ';
				usedDragonGlass = true;
				whiteWalkersNumber--;
			}
			if (usedDragonGlass)
				currentDragonGlass--;
			break;
		}
		case 'c': {
			currentDragonGlass = parent.maxDragonGlass;
			break;
		}
		}

		if (newJon.x != jon.x || newJon.y != jon.y) {
			stateChanged = true;
		}
		if (newGrid[newJon.x][newJon.y] == 'w') {
			isFailureState = true;
			jonDead = true;

		}

		if (stateChanged) {
			char j = isFailureState ? 'X' : 'J';
			newGrid[jon.x][jon.y] = ' ';
			newGrid[newJon.x][newJon.y] = j;
			if(parent.dragonStone.x == newJon.x && parent.dragonStone.y == newJon.y)
				newGrid[parent.dragonStone.x][parent.dragonStone.y] = 'C'; //Jon is in charging position
			else
				newGrid[parent.dragonStone.x][parent.dragonStone.y] = 'd';
		}

		State newState = new WesterosState(newGrid, isFailureState, whiteWalkersNumber, parent.maxDragonGlass, currentDragonGlass,
				parent.obstacles, parent.dragonStone, newJon, jonDead);
		return  newState;
	}
}
