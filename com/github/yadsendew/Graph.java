package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface Graph<T> {
	
	void addNode(String id, Node node);
	void addNode(Node node);
	void addEdge(Edge edge);
	void setEdgeDefault(String edge_default);
	
	int getTotalNodes();
	public int getTotalEdges();

	ArrayList<String> getNodeId();
	ArrayList<String> getEdgeId();
	HashMap<String, Node> getNodeList();
	HashMap<String, Edge> getEdgeList();
	
	ArrayList<Node> getNeighbor(Node node);
	T getDFS(Node startNode);
	
	boolean isConnected();
	
	ArrayList<Object> getDijkstraTree(String startNodeId);
	double getDiameter();
	
}
