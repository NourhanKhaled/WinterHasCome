import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneralSearch {
	
	public static SearchTreeNode Search(SearchProblem grid, String strategy, boolean visualize) {
		SearchTreeNode searchResult;		
		SearchTreeNode initialTreeNode = new SearchTreeNode(grid.initState);		
		ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
		nodes.add(initialTreeNode);
		
		while (true) {
			
			// if no more nodes to expand -> failure
			if (nodes.isEmpty())
				return null;
			
			// dequeue
			SearchTreeNode currentNode = nodes.remove(0);
			if(visualize)
				currentNode.visualize();
			
			// return current node if passes the goal test
			if (grid.goalTest(currentNode))
				return currentNode;
			
			// else expand current node i.e. apply all possible operators on node
			ArrayList<SearchTreeNode> children;
			if(! currentNode.state.isFailureState) {
				children = currentNode.expand(grid.getPossibleOperators(currentNode.state));
				nodes = qing(nodes, children, strategy);
			}
			
			
		}
	}
	
	public static ArrayList<SearchTreeNode> qing(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children, String strategy){
		
		switch(strategy) {
		case "BF": return BF(nodes, children); 
//		case "DF": return DF(nodes, children);
		case "UC": return UC(nodes, children);
//		case "ID": return ID(nodes, children);
		default: return null;
		}
	}
	
	public static ArrayList<SearchTreeNode> BF(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children){
		nodes.addAll(children);
		return nodes;
	}

	public static ArrayList<SearchTreeNode> UC(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children){
		nodes.addAll(children);
		Collections.sort(nodes);
		//print nodes.pathcost
		System.out.print("cost: ");
		for(SearchTreeNode node: nodes) {
			System.out.print(node.pathCost + " ");
		}
		System.out.println();
		return nodes;
	}
	
	public static void main(String[] args) {
		SaveWesteros sw = new SaveWesteros();
		char[][] grid = sw.initState.grid;
		
		SearchTreeNode result = Search(sw, "UC", true);
		
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
		

	}
}
