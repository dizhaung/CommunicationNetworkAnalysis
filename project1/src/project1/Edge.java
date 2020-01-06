package project1;

import java.util.ArrayList;

public class Edge {

	public String id;
	public String src; // id of src node
	public String dst; // id of dst node
	public double weight;
	
	// Default Constructor - Null Edge
	Edge() {
		id = "";
		src = dst = "";
		weight = 0;
	}
	// Constructor - Determined Edge
	Edge(String newId, String newSrc, String newDst){
		id = newId;
		src = newSrc;
		dst = newDst;
	}
	Edge(Edge otherEdge) {
		id = otherEdge.getId();
		src = otherEdge.getSrc();
		dst = otherEdge.getDst();
		weight = otherEdge.getWeight();
	}
	
	/*
	public void setId(String newId) {
		this.id = newId;
	}
	*/
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
	public void setDst(String Dst) {
		dst = Dst;
	}
	public String getSrc() {
		return src;
	}
	public String getDst() {
		return dst;
	}
	public double getWeight() {
		return weight;
	}
}
