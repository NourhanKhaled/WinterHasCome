import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneralSearch {
	
	public static SearchTreeNode Search(SearchProblem grid, String strategy, boolean visualize) {
		
		if(strategy.equals("ID")){
			return SearchID(grid, strategy, visualize);
		}
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
			if(currentNode.operator != null)
				if(currentNode.operator.action == 'c')
					System.out.println("Coming from charging");
			
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
		case "DF": return DF(nodes, children);
		case "UC": return UC(nodes, children);
//		case "ID": return (nodes, children);
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
	
	public static ArrayList<SearchTreeNode> DF(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children){
		children.addAll(nodes);
		System.out.println(children);
		return children;
	}
	
	public static SearchTreeNode SearchID(SearchProblem grid, String strategy, boolean visualize){
		
		int maxDepth = 0;
		while(true){
			SearchTreeNode searchResult;		
			SearchTreeNode initialTreeNode = new SearchTreeNode(grid.initState);		
			ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
			nodes.add(initialTreeNode);
			
			while (true) {
				
				// if no more nodes to expand -> failure
				if (nodes.isEmpty())
					break;
				
				// dequeue
				SearchTreeNode currentNode = nodes.remove(0);
				if(currentNode.operator != null)
					if(currentNode.operator.action == 'c')
						System.out.println("Coming from charging");
				
				if(visualize)
					currentNode.visualize();
				
				// return current node if passes the goal test
				if (grid.goalTest(currentNode))
					return currentNode;
				
				// else expand current node i.e. apply all possible operators on node
				ArrayList<SearchTreeNode> children;
				if(! currentNode.state.isFailureState) {
					children = currentNode.expand(grid.getPossibleOperators(currentNode.state));
					nodes = DL(nodes, children, maxDepth);
				}
				
			}
			maxDepth++;
		}
	}
	
	
	public static ArrayList<SearchTreeNode> DL(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children, int maxDepth){
		
		// if within depth, normal dfs
		if(children.get(0).depth < maxDepth){
			children.addAll(nodes);
			return children;
		}
		else{
			return nodes;
		}
			
	}
		

	public static void main(String[] args) {
		SaveWesteros sw = new SaveWesteros();
		char[][] grid = sw.initState.grid;
		
		SearchTreeNode result = Search(sw, "ID", true);
		
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
		

	}
}
