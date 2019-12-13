package project1;

import java.util.Map;
import java.util.HashMap;

public class Node {
	
	private String id;
	public Map<Node, Edge> neighbors = new HashMap<Node, Edge>();
	
	// Constructors
	Node(){
		id = "";
		neighbors = new HashMap<>();
	}
	Node(String newId){
		id = newId;
		neighbors = new HashMap<>();
	}
	// 
	public void setId(String newId) {
		this.id = newId;
	}
	
	public String getId() {
		return this.id;
	}
	
	
	
}
