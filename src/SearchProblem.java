
public abstract class SearchProblem {
	Operator [] operators;
	State currentState;

	public abstract boolean goalTest();
	// public abstract int pathCost()
	
	
	public abstract Operator[] getPossibleOperators();
	
}
