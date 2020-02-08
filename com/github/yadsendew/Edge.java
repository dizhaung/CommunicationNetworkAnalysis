package com.github.yadsendew;

import java.util.ArrayList;

class Edge {

	private String id;
	private String src; // id of src node
	private String dst; // id of dst node
	private double weight;
	
	// Default Constructor - Null Edge
	Edge() {
		id = "";
		src = dst = "";
		weight = 0;
	}
	// Constructor - Determined Edge
	Edge(String newId, String newSrc, String newDst, double newWeight){
		id = newId;
		src = newSrc;
		dst = newDst;
		weight = newWeight;
	}
	Edge(Edge otherEdge) {
		id = otherEdge.getId();
		src = otherEdge.getSrc();
		dst = otherEdge.getDst();
		weight = otherEdge.getWeight();
	}
	
	public void setId(String newId) {
		this.id = newId;
	}
	public String getId() {
		return this.id;
	}
	public ArrayList<String> getNodes(){
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add(src);
		nodes.add(dst);
		return nodes;
	}
	public void setSrc(String Src) {
		src = Src;
	}
	public String getSrc() {
		return src;
	}
	public void setDst(String Dst) {
		dst = Dst;
	}
	public String getDst() {
		return dst;
	}
	public void setWeight(double Weight) {
		weight = Weight;
	}
	public double getWeight() {
		return weight;
	}
}
