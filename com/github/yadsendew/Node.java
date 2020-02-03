package com.github.yadsendew;

import java.util.HashMap;
import java.util.Set;

public class Node {
	
	private String id;
	private HashMap<Node, Edge> neighbors = new HashMap<Node, Edge>();
	
	// Default Constructors
	Node(){
		id = "";
		neighbors = new HashMap<>();
	}
	// Determined Constructors
	Node(String newId){
		id = newId;
	}
	Node(String newId, HashMap<Node, Edge> newNeighbors){
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
	
	public HashMap<Node, Edge> getNeighbors(){
		return this.neighbors;
	}
	public Set<Node> getNeighborsKeySet() {
		return this.neighbors.keySet();
	}
	
	public void addNeighbor(Node newNode, Edge newEdge) {
		neighbors.put(newNode, newEdge);
	}

}
