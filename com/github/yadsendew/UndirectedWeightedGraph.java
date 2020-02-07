package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;


// TO_FIX: the HashMap object may change order of the key

public class UndirectedWeightedGraph implements Graph {
	
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge
	private ArrayList<String> nodeId = new ArrayList<String>();
	private ArrayList<String> edgeId = new ArrayList<String>();
	private String edgeDefault;
	private String id;
	private ShortestPathMatrix shortestPathMatrix;
	
	private HashMap< String, Double > bcmMap = new HashMap< String, Double >();
	
	// Constructors
	public UndirectedWeightedGraph()	{}
	
	public UndirectedWeightedGraph(UndirectedWeightedGraph undirectedWeightedGraph) {
		nodeList = new HashMap<String, Node>(undirectedWeightedGraph.getNodeList());
		edgeList = new HashMap<String, Edge>(undirectedWeightedGraph.getEdgeList());
		edgeDefault = undirectedWeightedGraph.getEdgeDefault();
		id = undirectedWeightedGraph.getId();
	}
	public void calculateShortestPathMatrix () {
		shortestPathMatrix = new ShortestPathMatrix(this);
	}
	public ShortestPathMatrix getShortestPathMatrix() {
		if (shortestPathMatrix == null){
			shortestPathMatrix = new ShortestPathMatrix(this);
		}
		return shortestPathMatrix;
	}

	//
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public void addNode(String id, Node node) {
		nodeList.put(id, node);
	}
	public void addNode(Node node) {
		nodeList.put(node.getId(), node);
		nodeId.add(node.getId());
	}
	/*
	addEdge function adds an edge to the graph
	It also add neighbors for source node and destination node
	*/
	public void addEdge(Edge edge) {
		edgeList.put(edge.getId(), edge);
		edgeId.add(edge.getId());
		// add neighbor for src and dst
		// try catch block
		try {
			Node srcNode = nodeList.get(edge.getSrc());
			Node dstNode = nodeList.get(edge.getDst());
			srcNode.addNeighbor(dstNode, edge);
			dstNode.addNeighbor(srcNode, edge);
		} catch (Exception e){
			System.out.println("ERROR: Insert an edge that has either source or destination not existed. The edge was ignored.");
		}
		// end try catch block
		// Node srcNode = nodeList.get(edge.getSrc());
		// Node dstNode = nodeList.get(edge.getDst());
		// srcNode.addNeighbor(dstNode, edge);
		// dstNode.addNeighbor(srcNode, edge);
		// change = true;
	}
	
	public Node getNode(String id) {
		return nodeList.get(id);
	}
	public Edge getEdge(String id) {
		return edgeList.get(id);
	}
	public Edge getEdge(Node node1, Node node2) {
		return node1.getNeighbors().get(node2);
	}
	public Edge getEdge(String id1, String id2) {
		return nodeList.get(id1).getNeighbors().get( nodeList.get(id2) );
	}	

	public boolean containsNode(String id) {
		return nodeList.containsKey(id);
	}
	public boolean containsNode(Node node) {
		return nodeList.containsValue(node);
	}
	public boolean containsEdge(String id) {
		return edgeList.containsKey(id);
	}
	public boolean containsEdge(Edge edge) {
		return edgeList.containsValue(edge);
	}

	public void setEdgeDefault(String edge_default) {
			edgeDefault = edge_default;
	}
	public String getEdgeDefault() {
		return edgeDefault;
	}
	
	public int getTotalNodes() {
		return nodeList.size();
	}
	public int getTotalEdges() {
		return edgeList.size();
	}
		
	public ArrayList<String> getNodeId() {
		return nodeId;
	}
	public ArrayList<String> getEdgeId() {
		return edgeId;
	}
	
	public HashMap<String, Node> getNodeList() {
		return nodeList;
	}
	public HashMap<String, Edge> getEdgeList() {
		return edgeList;
	}
	
	// return a list of neighbor nodes of a node
	public ArrayList<Node> getNeighbor(Node node) {
		
		return new ArrayList<Node>(node.getNeighborsKeySet());
	}

}
