package com.github.yadsendew;

import java.util.HashMap;
import java.util.Set;

/**
 * The Class Node represents a node.
 */
public class Node {
	
	/** Store the ID of the node. */
	private String id;
	
	/** Store the neighbors of the node. */
	private HashMap<Node, Edge> neighbors = new HashMap<Node, Edge>();
	
	/**
	 * Create a new node.
	 */
	// Default Constructors
	Node(){
		id = "";
		neighbors = new HashMap<>();
	}
	
	/**
	 * Create a new node.
	 *
	 * @param newId the new ID
	 */
	// Determined Constructors
	Node(String newId){
		id = newId;
	}
	
	/**
	 * Create a new node.
	 *
	 * @param newId the new ID
	 * @param newNeighbors the new neighbors
	 */
	Node(String newId, HashMap<Node, Edge> newNeighbors){
		id = newId;
		neighbors = newNeighbors;
	}
	
	/**
	 * Create a new node.
	 *
	 * @param otherNode the other node
	 */
	Node(Node otherNode) {
		id = otherNode.getId();
		neighbors = new HashMap<Node, Edge>(otherNode.getNeighbors());
	}
	
	/**
	 * Sets the ID.
	 *
	 * @param newId the new ID
	 */
	public void setId(String newId) {
		this.id = newId;
	}
	
	/**
	 * Gets the ID.
	 *
	 * @return the ID
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Gets the neighbors.
	 *
	 * @return the neighbors
	 */
	public HashMap<Node, Edge> getNeighbors(){
		return this.neighbors;
	}
	
	/**
	 * Gets the neighbors key set.
	 *
	 * @return the neighbors key set
	 */
	public Set<Node> getNeighborsKeySet() {
		return this.neighbors.keySet();
	}
	
	/**
	 * Adds the neighbor.
	 *
	 * @param newNode the new node
	 * @param newEdge the new edge
	 */
	public void addNeighbor(Node newNode, Edge newEdge) {
		neighbors.put(newNode, newEdge);
	}

}
