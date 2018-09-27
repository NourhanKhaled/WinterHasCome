import java.util.ArrayList;

public class SearchTreeNode {
	State state;
	SearchTreeNode parent;	
	Operator operator; // Operator that causes it from the parent
	int depth;
	int pathCost;
	
	public SearchTreeNode(State state, SearchTreeNode parent, Operator operator) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = parent.depth + 1;
		this.pathCost = parent.pathCost + operator.cost;
	}
	
	public SearchTreeNode(State state) {
		this.state = state;
		this.depth = 0;
	}
	
	public ArrayList<SearchTreeNode> expand(SearchProblem searchProblem) {
		
		Operator[] operators = searchProblem.operators;
		ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
		
		for (Operator operator : operators) {
			State newState = operator.transitionFunction(this.state);
			SearchTreeNode newSearchTreeNode = new SearchTreeNode(newState, this, operator);
			nodes.add(newSearchTreeNode);
		}
		return nodes;
	}
	
}
