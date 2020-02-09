package com.github.yadsendew;

import java.util.ArrayList;


/**
 * The Class Edge represents an edge.
 */
class Edge {

	/** Store the ID of the edge. */
	private String id;
	
	/** Store the ID of source node. */
	private String src; // id of src node
	
	/** Store the ID of destination node. */
	private String dst; // id of dst node
	
	/** Store the weight of the edge. */
	private double weight;
	
	/**
	 * Create a new edge.
	 */
	// Default Constructor - Null Edge
	Edge() {
		id = "";
		src = dst = "";
		weight = 0;
	}
	
	/**
	 * Create a new edge.
	 *
	 * @param newId the new ID of the edge
	 * @param newSrc the new ID of source node
	 * @param newDst the new ID of destination node
	 * @param newWeight the new weight of the edge
	 */
	// Constructor - Determined Edge
	Edge(String newId, String newSrc, String newDst, double newWeight){
		id = newId;
		src = newSrc;
		dst = newDst;
		weight = newWeight;
	}
	
	/**
	 * Create a new edge.
	 *
	 * @param otherEdge the other edge
	 */
	Edge(Edge otherEdge) {
		id = otherEdge.getId();
		src = otherEdge.getSrc();
		dst = otherEdge.getDst();
		weight = otherEdge.getWeight();
	}
	
	/**
	 * Sets the ID of the edge.
	 *
	 * @param newId the new ID of the edge
	 */
	public void setId(String newId) {
		this.id = newId;
	}
	
	/**
	 * Gets the ID of the edge.
	 *
	 * @return the ID of the edge
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Gets the nodes.
	 *
	 * @return the nodes
	 */
	public ArrayList<String> getNodes(){
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add(src);
		nodes.add(dst);
		return nodes;
	}
	
	/**
	 * Sets the ID of source node.
	 *
	 * @param Src the new ID of source node
	 */
	public void setSrc(String Src) {
		src = Src;
	}
	
	/**
	 * Gets the ID of source node.
	 *
	 * @return the ID of source node
	 */
	public String getSrc() {
		return src;
	}
	
	/**
	 * Sets the ID of destination node.
	 *
	 * @param Dst the new ID of destination node
	 */
	public void setDst(String Dst) {
		dst = Dst;
	}
	
	/**
	 * Gets the ID of destination node.
	 *
	 * @return the ID of destination node
	 */
	public String getDst() {
		return dst;
	}
	
	/**
	 * Sets the weight of the edge.
	 *
	 * @param Weight the new weight of the edge
	 */
	public void setWeight(double Weight) {
		weight = Weight;
	}
	
	/**
	 * Gets the weight of the edge.
	 *
	 * @return the weight of the edge
	 */
	public double getWeight() {
		return weight;
	}
}
