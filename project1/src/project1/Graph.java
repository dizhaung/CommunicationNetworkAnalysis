package project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Graph {
	
	public Map<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	public Map<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge

	
	
	public int getTotalNodes() {
		return nodeList.size();
	}
	public int getTotalEdges() {
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
	
	public Map<String, Node> getNodeList() {
		return nodeList;
	}
	
	public Map<String, Edge> getEdgeList() {
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
	public Graph getDFS(Node startNode) {
		Graph DFSTree = new Graph();
		
		ArrayList<Node> DFSStack = new ArrayList<Node>();	// stack of DFS
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
				DFSTree.addEdge(chosenNeighbor.getNeighbors().get(currentNode));	// add the edge of chosenNode and currentNode, through the Map neighbors of chosenNode
				
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
		
		public boolean isConnected() {
			// create a DFSTree from the graph to check graph connectivity
			// choose a random node
			Random rand = new Random();
			ArrayList<String> keys = new ArrayList<String>( nodeList.keySet() );
			String randNodeId = keys.get( rand.nextInt( keys.size() ));
			// DFSTree
			Graph DFSTree = this.getDFS( this.getNodeList().get(randNodeId) );
					
			// return true if the amount of node of the graph and DFSTree are the same
			if( DFSTree.getTotalNodes() == this.getTotalNodes()) {
				return true;
			}
			else {
				return false;
			}
		}
	
		
}
