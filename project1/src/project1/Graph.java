package project1;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	
	private Map<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	private Map<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge

	
	
	public int totalNodes() {
		return nodeList.size();
	}
	public int totalEdges() {
		return edgeList.size();
	}
	//public ArrayList<Node> getShortestPath(Node start, Node end) {}
	//public int getDiameter() {}
	public void addNode(Node node) {
		nodeList.put(node.getId(), node);
	}
	public void addEdge(Edge edge){
		edgeList.put(edge.getId(), edge);
	}
	public void printNodeIDs() {
		for (Node node : nodeList.values()) {
			System.out.println(node.getId());
		}
	}
	public void printEdgeIDs() {
		for (Edge edge : edgeList.values()) {
			System.out.println(edge.getId());
		}
	}
}
