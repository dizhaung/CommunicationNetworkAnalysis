package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The Class UndirectedWeightedGraph represents an undirected weighted graph.
 */
public class UndirectedWeightedGraph implements Graph {
	
	/** Store list of nodes. */
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	
	/** Store list of edges. */
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge
	
	/** Store ID of nodes. */
	private ArrayList<String> nodeId = new ArrayList<String>();
	
	/** Store ID of edges. */
	private ArrayList<String> edgeId = new ArrayList<String>();
	
	/** Store default edge. */
	private String edgeDefault;
	
	/** Store ID of the graph. */
	private String id;
	
	/** Store shortest path matrix. */
	private ShortestPathMatrix shortestPathMatrix;
	
	// Constructors
	/**
	 * Create a new undirected weighted graph.
	 */
	public UndirectedWeightedGraph()	{}
	
	/**
	 * Create a new undirected weighted graph.
	 *
	 * @param undirectedWeightedGraph the undirected weighted graph
	 */
	public UndirectedWeightedGraph(UndirectedWeightedGraph undirectedWeightedGraph) {
		nodeList = new HashMap<String, Node>(undirectedWeightedGraph.getNodeList());
		edgeList = new HashMap<String, Edge>(undirectedWeightedGraph.getEdgeList());
		edgeDefault = undirectedWeightedGraph.getEdgeDefault();
		id = undirectedWeightedGraph.getId();
	}
	
	/**
	 * Calculate shortest path matrix.
	 * 
	 * @throws NotFoundNodeException if the node is not found
	 */
	public void calculateShortestPathMatrix() throws NotFoundNodeException {
		shortestPathMatrix = new ShortestPathMatrix(this);
	}
	
	/**
	 * Gets the shortest path matrix.
	 *
	 * @return the shortest path matrix
	 * @throws NotFoundNodeException if the node is not found
	 */
	public ShortestPathMatrix getShortestPathMatrix() throws NotFoundNodeException {
		if (shortestPathMatrix == null){
			shortestPathMatrix = new ShortestPathMatrix(this);
		}
		return shortestPathMatrix;
	}

	/**
	 * Sets the ID of the graph.
	 *
	 * @param id the new ID of the graph
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the ID of the graph.
	 *
	 * @return the ID of the graph
	 */
	public String getId() {
		return id;
	}

	/**
	 * Adds the node.
	 *
	 * @param id the ID of the node
	 * @param node the node
	 */
	public void addNode(String id, Node node) {
		nodeList.put(id, node);
	}
	
	/**
	 * Adds the node.
	 *
	 * @param node the node
	 */
	public void addNode(Node node) {
		nodeList.put(node.getId(), node);
		nodeId.add(node.getId());
	}
	
	/**
	 * Adds the edge.
	 *
	 * @param edge the edge
	 */
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
	}
	
	/**
	 * Gets the node.
	 *
	 * @param id the ID of the node
	 * @return the node
	 */
	public Node getNode(String id) {
		return nodeList.get(id);
	}
	
	/**
	 * Gets the edge.
	 *
	 * @param id the ID of the edge
	 * @return the edge
	 */
	public Edge getEdge(String id) {
		return edgeList.get(id);
	}
	
	/**
	 * Gets the edge.
	 *
	 * @param node1 the node 1
	 * @param node2 the node 2
	 * @return the edge
	 */
	public Edge getEdge(Node node1, Node node2) {
		return node1.getNeighbors().get(node2);
	}
	
	/**
	 * Gets the edge.
	 *
	 * @param id1 the ID of the source node
	 * @param id2 the ID of the destination node
	 * @return the edge
	 */
	public Edge getEdge(String id1, String id2) {
		return nodeList.get(id1).getNeighbors().get( nodeList.get(id2) );
	}	

	/**
	 * Check if the graph contains the node with the given ID.
	 *
	 * @param id the ID of the node
	 * @return true, if the graph contains the node
	 */
	public boolean containsNode(String id) {
		return nodeList.containsKey(id);
	}
	
	/**
	 * Check if the graph contains the node.
	 *
	 * @param node the node
	 * @return true, if the graph contains the node
	 */
	public boolean containsNode(Node node) {
		return nodeList.containsValue(node);
	}
	
	/**
	 * Check if the graph contains the edge with the given ID.
	 *
	 * @param id the ID of the edge
	 * @return true, if the graph contains the edge
	 */
	public boolean containsEdge(String id) {
		return edgeList.containsKey(id);
	}
	
	/**
	 * Check if the graph contains the edge.
	 *
	 * @param edge the edge
	 * @return true, if the graph contains the edge
	 */
	public boolean containsEdge(Edge edge) {
		return edgeList.containsValue(edge);
	}

	/**
	 * Sets the default edge.
	 *
	 * @param edge_default the new default edge
	 */
	public void setEdgeDefault(String edge_default) {
			edgeDefault = edge_default;
	}
	
	/**
	 * Gets the default edge.
	 *
	 * @return the default edge
	 */
	public String getEdgeDefault() {
		return edgeDefault;
	}
	
	/**
	 * Gets the total number of nodes.
	 *
	 * @return the total number of nodes
	 */
	public int getTotalNodes() {
		return nodeList.size();
	}
	
	/**
	 * Gets the total number of edges.
	 *
	 * @return the total number of edges
	 */
	public int getTotalEdges() {
		return edgeList.size();
	}
		
	/**
	 * Gets the ID of nodes.
	 *
	 * @return the ID of nodes
	 */
	public ArrayList<String> getNodeId() {
		return nodeId;
	}
	
	/**
	 * Gets the ID of edges.
	 *
	 * @return the ID of edges
	 */
	public ArrayList<String> getEdgeId() {
		return edgeId;
	}
	
	/**
	 * Gets the list of nodes.
	 *
	 * @return the list of nodes
	 */
	public HashMap<String, Node> getNodeList() {
		return nodeList;
	}
	
	/**
	 * Gets the list of edges.
	 *
	 * @return the list of edges
	 */
	public HashMap<String, Edge> getEdgeList() {
		return edgeList;
	}
	
	/**
	 * Gets a list of neighbor nodes of a node.
	 *
	 * @param node the node
	 * @return a list of neighbor nodes of a node
	 */
	// return a list of neighbor nodes of a node
	public ArrayList<Node> getNeighbor(Node node) {
		
		return new ArrayList<Node>(node.getNeighborsKeySet());
	}

}
