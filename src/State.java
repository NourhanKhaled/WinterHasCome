
public class State {
	char[][] grid;
	boolean isFailureState;
//	boolean visited; To be used for optimization (maybe?)
	
	public State(char[][] grid, boolean isFailureState) {
		this.grid = grid;
		this.isFailureState = isFailureState;
	}
	
	
}
