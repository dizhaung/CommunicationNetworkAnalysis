package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// TO_FIX: the HashMap object may change order of the key

public class UndirectedWeightedGraph implements Graph<UndirectedWeightedGraph> {
	
	private HashMap<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge
	private ArrayList<String> nodeId = new ArrayList<String>();
	private ArrayList<String> edgeId = new ArrayList<String>();
	private String edgeDefault;
	private String id;
	private ShortestPathMatrix shortestPathMatrix;
	private boolean sPMChange = false;	// to check if their is a need to change the shortestPathMatrix in some method
	
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
		if (shortestPathMatrix == null)
			shortestPathMatrix = new ShortestPathMatrix(this);
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
		sPMChange = true;
	}
	/*
	addEdge function adds an edge to the graph
	It also add neighbors for source node and destination node
	*/
	public void addEdge(Edge edge) {
		edgeList.put(edge.getId(), edge);
		edgeId.add(edge.getId());
		// get node
		nodeList.get(edge.getSrc()).addNeighbor(nodeList.get(edge.getDst()), edge);
		nodeList.get(edge.getDst()).addNeighbor(nodeList.get(edge.getSrc()), edge);
		sPMChange = true;
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
		
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		// for every edge of the graph
		for( Edge edge : edgeList.values() ) {
			// if the node is the source node of edge
			if ( node.getId().equals( edge.getSrc() )) {
				// add the destination node
				neighbors.add( nodeList.get(edge.getDst()) );
			}
			// if the node is the destination node of edge
			else if ( node.getId().equals( edge.getDst() )) {
				// add the source node
				neighbors.add( nodeList.get(edge.getSrc()) );
			}
		}
		
		return neighbors;
	}
	
	// create a DFS Tree from the graph
	public UndirectedWeightedGraph getDFS(Node startNode) {
		UndirectedWeightedGraph DFSTree = new UndirectedWeightedGraph();
		
		ArrayList<Node> DFSStack = new ArrayList<Node>();	// stack of DFS
		DFSTree.addNode(startNode);
		DFSStack.add(startNode);		// add the starting node into the stack
		
		Node currentNode;	 
		ArrayList<Node> currentPosNeighborNode;	// the neighbor nodes of the current node which is possible to add into the DFS tree
		Node chosenNeighbor;	// chosen node from currentPosNeighborNode
		
		// run loop while there are node in the stack
		while(DFSStack.size() != 0) {
			
			currentNode = DFSStack.get(DFSStack.size() - 1);	// get the last node in the stack
			currentPosNeighborNode = new ArrayList<Node>();
			
			// list of neighbor node that is possible to add into DFS tree
			for( Node node : this.getNeighbor(currentNode) ) {
				if( !DFSTree.getNodeList().containsValue(node) ) {	// if the node is not in the DFS tree ass it into the currentPosNeighborNode
					currentPosNeighborNode.add(node);
				}
			}
			
			// if there are possible node, choose a random node
			if( currentPosNeighborNode.size() != 0 ) {
				chosenNeighbor = currentPosNeighborNode.get( (int) (currentPosNeighborNode.size() * Math.random()) );
					
				// update the attributes of the DFSTree
				DFSTree.addNode(chosenNeighbor);	// add the chosenNode
				DFSTree.addEdge(chosenNeighbor.getNeighbors().get(currentNode));	// add the edge of chosenNode and currentNode, through the HashMap neighbors of chosenNode

				// put the chosenNeighbor into the stack
				DFSStack.add(chosenNeighbor);
			}
			// if there is no possible node, pop the last node in stack
			else {
				DFSStack.remove( DFSStack.size() - 1);
			}
		}
			
		return DFSTree;
			
	}
	// Connectivity	
	public boolean isConnected() {
		// create a DFSTree from the graph to check graph connectivity
		// choose a random node
		Random rand = new Random();
		ArrayList<String> keys = new ArrayList<String>( nodeList.keySet() );
		String randNodeId = keys.get( rand.nextInt( keys.size() ));
		
		// DFSTree
		UndirectedWeightedGraph DFSTree = this.getDFS( this.getNodeList().get(randNodeId) );

		// return true if the amount of node of the graph and DFSTree are the same
		if( DFSTree.getTotalNodes() == this.getTotalNodes()) {
			return true;
		}
		else {
			return false;
		}
	}

	// get the list of path from precedence HashMap
	public ArrayList< ArrayList<String> > getPathList(HashMap<String, HashSet<String>> precedence, String startId, String endId) {
		ArrayList< ArrayList<String> > pathList = new ArrayList< ArrayList<String> >();
		
		// recursive method: the pathList of the endNode is all paths of its preceding nodes with the endNode itself in the last index
		// the starting node does not have preceding node, the pathList will have only 1 node
		if (endId.equals(startId)) {
			ArrayList<String> path = new ArrayList<String>();
			
			path.add(endId);
			
			pathList.add( path );
		}
		else {
			for (String nodeId : precedence.get(endId)) {
				// for every path of its preceding node
				for (ArrayList<String> prePath : getPathList(precedence, startId, nodeId)) {
					// add endId to the end of the list
					prePath.add(endId);
					// then add the path to pathList
					pathList.add(prePath);
				}
			}
		}
			
		return pathList;
	}
	
	public ArrayList<Object> getDijkstraTree(String startNodeId) {
		UndirectedWeightedGraph dijkstraTree = new UndirectedWeightedGraph();
		
		// create a HashMap of updated distance from the starting node to every node
		// initially it is   0, inf, inf, inf, ...
		HashMap<String, Double> distance = new HashMap<String, Double>();	
		for (String nodeId : nodeList.keySet()) {
			if (nodeId.equals(startNodeId)) {
				// the starting node will always have 0 distance from itself
				distance.put(nodeId, 0.0);
			}
			else {
				// the others have initial distance is infinity
				distance.put(nodeId, Double.MAX_VALUE);
			}
		}
		
		// a HashMap of preceding node of each node
		HashMap<String, String> precedence = new HashMap<String, String>();
		for (String nodeId : nodeList.keySet()) {
			precedence.put(nodeId, null);
		}
		
		//
		Set<String> unvisitedNode = new HashSet<String>();
		for (String nodeId : nodeList.keySet()) {
			unvisitedNode.add(nodeId);
		}
		
		double minUnvisitedLength;
		String minUnvisitedNode;
		// run loop while not all node is visited
		while ( unvisitedNode.size() != 0 ) {
			// find the unvisited node with minimum current distance in distance list
			minUnvisitedLength = Double.MAX_VALUE;
			minUnvisitedNode = "";
			for (String nodeId : unvisitedNode) {
				if (distance.get(nodeId) < minUnvisitedLength) {
					minUnvisitedNode = nodeId;
					minUnvisitedLength = distance.get(nodeId); 
				}
			}
			
			// if there are no node that can be visited from the unvisitedNode, break the loop 
			if (minUnvisitedLength == Double.MAX_VALUE) {
				break;
			}			
	
			// TODO: add code for updating the dijkstraTree
			
			// remove the node from unvisitedNode
			unvisitedNode.remove(minUnvisitedNode);
			
			// update the distance of its neighbors
			for (Node neighborNode : this.nodeList.get(minUnvisitedNode).getNeighbors().keySet()) {
				double distanceFromTheMinNode = distance.get(minUnvisitedNode) + this.nodeList.get(minUnvisitedNode).getNeighbors().get(neighborNode).getWeight();
				// if the distance of the neighbor can be shorter from the current node, change 
				// its details in distance and precedence
				if ( distanceFromTheMinNode < distance.get(neighborNode.getId()) ) {
					distance.replace(neighborNode.getId(), distanceFromTheMinNode);
					// 
					precedence.put(neighborNode.getId(), minUnvisitedNode);
				}
				
			}			
				
		}
		
		
		return new ArrayList<Object>( Arrays.asList(dijkstraTree, distance) );
	}
	
	// get the diameter of the graph
	@Override
	public double getDiameter() {
		
		// make a copy of the graph
		UndirectedWeightedGraph copyGraph = new UndirectedWeightedGraph(this);
		
		// get the ArrayList of node id to run the for loop with iterator
		ArrayList<String> nodeIdList = new ArrayList<String>( nodeList.keySet() );
		
		double diameter = 0;
		
		// check the length of shortest path for every pair of nodes in the list
		// loop for every node in the list
		for (String nodeId : nodeIdList) {
			
			Set<Double> distance = new HashSet<Double>( ( (HashMap<String, Double>) copyGraph.getDijkstraTree(nodeId).get(1) ).values() );
			for (double i : distance) {
				if (i != Double.MAX_VALUE && i > diameter) {
					diameter = i;
				}
			}
			
			
		}
		// return the diameter
		return diameter;
		
	}
}
