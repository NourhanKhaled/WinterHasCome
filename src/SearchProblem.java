
public abstract class SearchProblem {
	Operator [] operators;
	State initState;

	public abstract boolean goalTest(SearchTreeNode currentNode);
	public abstract int pathCost(SearchTreeNode currentNode);
	
	
	public abstract Operator[] getPossibleOperators(State currentState);
	
}
