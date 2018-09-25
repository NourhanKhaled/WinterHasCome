
public abstract class Operator {
	char action;
	int cost;
	
	public Operator(char action, int cost) {
		this.action = action;
		this.cost = cost;
	}
	
	public abstract State transitionFunction(State parent);
}
