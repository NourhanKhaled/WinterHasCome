import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneralSearch {
	
	public static int searchType = 0; //indicates uninformed or informed types
	static int nodesExpanded = 1;
	
	public static SearchTreeNode Search(SearchProblem grid, String strategy, boolean visualize) {
		
		if(strategy.equals("ID")){
			return SearchID(grid, strategy, visualize);
		}
		
		// setting type of search to determine field to sort on for ucs, greedy and A*
		switch(strategy) {
		case "BF": searchType = 0; break;
		case "DF": searchType = 0; break;
		case "UC": searchType = 1; break;
		case "GR1": searchType = 2; break;
		case "GR2": searchType = 2; break;
		case "A1": searchType = 3; break;
		case "A2": searchType = 3; break;
		default: searchType = -1;
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
			nodesExpanded++ ;
			if(currentNode.operator != null) {
				if(currentNode.operator.action == 'c')
					System.out.println("Coming from charging");
				if(currentNode.operator.action == 'k')
					System.out.println("IMMA KILL YA");
			}
			
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
		case "GR1": return GR(nodes, children, 1);
		case "A1": return A(nodes, children, 1);
		case "GR2": return GR(nodes, children, 2);
		case "A2": return A(nodes, children, 2);
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
	
	
	public static ArrayList<SearchTreeNode> GR(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children, int heuristic){

		for(int i = 0; i < children.size(); i++) {
			
			if(heuristic == 1)
				children.get(i).h = children.get(i).h1();
			else
				children.get(i).h = children.get(i).h2();
		}
		
		nodes.addAll(children);
		Collections.sort(nodes);
		System.out.print("h: ");
		for(SearchTreeNode node: nodes) {
			System.out.print(node.h + " ");
		}
		System.out.println();
		System.out.println(nodes);
		return nodes;
		
	}
	
	public static ArrayList<SearchTreeNode> A(ArrayList<SearchTreeNode> nodes, ArrayList<SearchTreeNode> children, int heuristic){

		for(int i = 0; i < children.size(); i++) {
			
			if(heuristic == 1)
				children.get(i).h = children.get(i).h1();
			else
				children.get(i).h = children.get(i).h2();
		}
		
		nodes.addAll(children);
		System.out.println("before sorting: ");
		for(SearchTreeNode node: nodes) {
			System.out.print((node.pathCost + node.h) + " ");
		}
		System.out.println();
		Collections.sort(nodes);
		System.out.println("nach sorting: ");
		for(SearchTreeNode node: nodes) {
			System.out.print((node.pathCost + node.h) + " ");
		}
		System.out.println();
		return nodes;
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
		
		SearchTreeNode result = Search(sw, "A2", true);	

	}
}
