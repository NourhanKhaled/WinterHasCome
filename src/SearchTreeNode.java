import java.util.ArrayList;
import java.util.Arrays;

public class SearchTreeNode implements Comparable{
	static int id_counter = 0;
	int id;
	State state;
	SearchTreeNode parent;
	Operator operator; // Operator that causes it from the parent
	int depth;
	int pathCost;
	int h;

	public SearchTreeNode(State state, SearchTreeNode parent, Operator operator) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = parent.depth + 1;
		this.pathCost = parent.pathCost + operator.cost;
		this.id = id_counter++;
		this.h = 0;

	}

	public SearchTreeNode(State state) {
		this.state = state;
		this.depth = 0;
		this.id = id_counter++;
	}

	public ArrayList<SearchTreeNode> expand(Operator[] operators) {

		ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
		
		System.out.println("operators: " + operators.length);
		for (Operator operator : operators) {
			State newState = operator.transitionFunction(this.state);
			SearchTreeNode newSearchTreeNode = new SearchTreeNode(newState, this, operator);
			nodes.add(newSearchTreeNode);
		}
		return nodes;
	}

	public void visualize() {
		for (int i = 0; i < this.state.grid.length; i++) {
			System.out.println(Arrays.toString(this.state.grid[i]));
		}
		System.out.println("Depth: "+ this.depth + ", # Nodes: " + GeneralSearch.nodesExpanded);
		System.out.println("======================");
	}
	
	public int h1() {
		return ((WesterosState) this.state).whiteWalkersNumber;
	}
	
	//TODO: generic oder geht's so?
	public int h2() {
		
		if(((WesterosState) this.state).whiteWalkersNumber == 0)
			return 0;
		
		char[][] grid = ((WesterosState) this.state).grid;
		ArrayList<Position> whiteWalkersPos = new ArrayList<Position>();
		int minDist = (int) 1e6;
		int dist;
		
		Position jonPos = ((WesterosState) this.state).jon;
		// checking white walkers position to calculate manhattan distance
		for (int i = 0; i < grid.length; i++) {
			for( int j = 0; j < grid.length; j++) {
				if(grid[i][j] == 'w') {
					dist = Math.abs(jonPos.x - j) + Math.abs(jonPos.y - i);
					if (dist < minDist)
						minDist = dist;
				}
			}
		}
		
		return minDist;
	}

	public int compareTo(Object obj) {
		
		if (GeneralSearch.searchType == 1) 
			return this.pathCost - ((SearchTreeNode) obj).pathCost;
		
		if (GeneralSearch.searchType == 2)
			return this.h - ((SearchTreeNode) obj).h;
		
		if (GeneralSearch.searchType == 3)
			return (this.pathCost + this.h) - (((SearchTreeNode) obj).pathCost + ((SearchTreeNode) obj).h);
		
		return 0;
	}
	

	
	public String toString(){
		return "("+ this.id + ", " + this.operator.action + ")";
	}
	
	

}
