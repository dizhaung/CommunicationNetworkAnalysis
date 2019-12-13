package project1;

public class Edge {

	private String id;
	public Node src;
	public Node des;
	public int weight;
	
	// Constructor
	Edge() {
		id = "";
		src = des = new Node();
		weight = 0;
	}
	
	//
	public void setId(String newId) {
		this.id = newId;
	}
	
}
