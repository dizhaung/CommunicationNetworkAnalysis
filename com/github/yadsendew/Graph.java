package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The Interface Graph represents a graph.
 */
public interface Graph {
	
	/**
	 * Adds the node.
	 *
	 * @param id the ID
	 * @param node the node
	 */
	void addNode(String id, Node node);
	
	/**
	 * Adds the node.
	 *
	 * @param node the node
	 */
	void addNode(Node node);
	
	/**
	 * Adds the edge.
	 *
	 * @param edge the edge
	 */
	void addEdge(Edge edge);
	
	/**
	 * Sets the default edge.
	 *
	 * @param edge_default the new default edge
	 */
	void setEdgeDefault(String edge_default);
	
	/**
	 * Gets the total number of nodes.
	 *
	 * @return the total number of nodes
	 */
	int getTotalNodes();
	
	/**
	 * Gets the total number of edges.
	 *
	 * @return the total number of edges
	 */
	public int getTotalEdges();

	/**
	 * Gets the ID of the nodes.
	 *
	 * @return the ID of the nodes
	 */
	ArrayList<String> getNodeId();
	
	/**
	 * Gets the ID of the edges.
	 *
	 * @return the ID of the edges
	 */
	ArrayList<String> getEdgeId();
	
	/**
	 * Gets the list of nodes.
	 *
	 * @return the list of nodes
	 */
	HashMap<String, Node> getNodeList();
	
	/**
	 * Gets the list of edges.
	 *
	 * @return the list of edges
	 */
	HashMap<String, Edge> getEdgeList();
	
	/**
	 * Gets the neighbor of the node.
	 *
	 * @param node the node
	 * @return the neighbor
	 */
	ArrayList<Node> getNeighbor(Node node);
	
	
}
