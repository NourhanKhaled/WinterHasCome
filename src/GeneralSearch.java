import java.util.ArrayList;

public class GeneralSearch {
	
	public static SearchTreeNode Search(SearchProblem grid, String strategy, boolean visualize) {
		SearchTreeNode searchResult;		
		SearchTreeNode initialTreeNode = new SearchTreeNode(grid.currentState);		
		ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
		nodes.add(initialTreeNode);
		
		while (true) {
			if (nodes.isEmpty())
				return null;
			SearchTreeNode currentNode = nodes.remove(0);
			if (grid.goalTest())
				return currentNode;
			ArrayList<SearchTreeNode> children = currentNode.expand(grid);
			nodes = Q-ING(nodes, children, strategy);
			
		}
		
		return searchResult;
	}

}
