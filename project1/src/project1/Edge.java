package project1;

import java.util.ArrayList;

public class Edge {

	public String id;
	public String src; // id of src node
	public String dst; // id of dst node
	public int weight;
	
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
	public String getSrc() {
		return src;
	}
	public String getDst() {
		return dst;
	}
}
