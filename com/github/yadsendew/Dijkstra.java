package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra {

    public static ArrayList<Object> getDijkstraTree(UndirectedWeightedGraph graph, String startNodeId) {
        UndirectedWeightedGraph dijkstraTree = new UndirectedWeightedGraph();
		
		// create a HashMap of updated distance from the starting node to every node
		// initially it is 0, inf, inf, inf, ...
		HashMap<String, Double> distance = new HashMap<String, Double>();	
		for (String nodeId : graph.getNodeList().keySet()) {
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
		for (String nodeId : graph.getNodeList().keySet()) {
			precedence.put(nodeId, null);
		}
		
		//
		HashSet<String> unvisitedNode = new HashSet<String>();
		for (String nodeId : graph.getNodeList().keySet()) {
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
			
			// remove the node from unvisitedNode
			unvisitedNode.remove(minUnvisitedNode);
			
			// update the distance of its neighbors
			for (Node neighborNode : graph.getNode(minUnvisitedNode).getNeighborsKeySet()) {
				double distanceFromTheMinNode = distance.get(minUnvisitedNode) + graph.getEdge(minUnvisitedNode, neighborNode.getId()).getWeight();
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

}