package project1;

import java.util.Map;
import java.util.HashMap;

public class Node {
	
	public String id;
	public Map<Node, Edge> neighbors = new HashMap<Node, Edge>();
	
	// Default Constructors
	Node(){
		id = "";
		neighbors = new HashMap<>();
	}
	// Determined Constructors
	Node(String newId){
		id = newId;
	}
	Node(String newId, Map<Node, Edge> newNeighbors){
		id = newId;
		neighbors = newNeighbors;
	}
	Node(Node otherNode) {
		id = otherNode.getId();
		neighbors = new HashMap<Node, Edge>(otherNode.getNeighbors());
	}
	
	public void setId(String newId) {
		this.id = newId;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Map<Node, Edge> getNeighbors(){
		return this.neighbors;
	}
	
	public void addNeighbor(Node newNode, Edge newEdge) {
		neighbors.put(newNode, newEdge);
	}

}
