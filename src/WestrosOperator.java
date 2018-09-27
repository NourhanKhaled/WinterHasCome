
public class WestrosOperator extends Operator {

	public WestrosOperator(char action, int cost) {
		super(action, cost);
	}

	public State transitionFunction(State parent) {
		char[][] grid = parent.grid;
		int size = grid[0].length;
		char[][] newGrid = new char[size][size];
		
		// Jon coordinates
		Position jon = new Position(-1,-1);
		
		// Cloning grid and locating Jon
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				newGrid[i][j] = grid[i][j];
				if (grid[i][j] == 'J')  {
					jon.x = i;
					jon.y = j;
				}
			}
		}
		
		// getting a new state from operator
		
		// New Jon coordinates
		Position newJon = new Position(jon.x,jon.y);
		
		boolean stateChanged = false;
				
		switch(this.action) {
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
				break;
			}
		}
		
		if ( newJon.x != jon.x || newJon.y != jon.y)
			stateChanged = true;
	}
	
}
