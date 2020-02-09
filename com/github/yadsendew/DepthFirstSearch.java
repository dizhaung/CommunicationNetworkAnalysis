package com.github.yadsendew;

import java.util.ArrayList;
import java.util.logging.*;

public class DepthFirstSearch {

	private final static Logger LOGGER = Logger.getLogger("MyLogger");
    
    public static UndirectedWeightedGraph getDFSTree(UndirectedWeightedGraph graph, String startNodeId) {

		LOGGER.info("get DFS Tree from " + startNodeId);

        UndirectedWeightedGraph DFSTree = new UndirectedWeightedGraph();

        Node startNode = graph.getNode(startNodeId);
		
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
			for( Node node : graph.getNeighbor(currentNode) ) {
				if( !DFSTree.containsNode(node) ) {	// if the node is not in the DFS tree ass it into the currentPosNeighborNode
					currentPosNeighborNode.add(node);
				}
			}
			
			// if there are possible node, choose a random node
			if( currentPosNeighborNode.size() != 0 ) {
				chosenNeighbor = currentPosNeighborNode.get( (int) (currentPosNeighborNode.size() * Math.random()) );
					
				// update the attributes of the DFSTree
				DFSTree.addNode(chosenNeighbor);	// add the chosenNode
				DFSTree.addEdge( graph.getEdge(chosenNeighbor, currentNode) );	// add the edge of chosenNode and currentNode, through the HashMap neighbors of chosenNode

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
}