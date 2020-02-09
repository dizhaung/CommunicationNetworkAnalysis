package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class ShortestPath finds the shortest path between 2 nodes of a graph.
 */
public class ShortestPath {
	
	/** Store the source of the edge. */
	private String src;
	
	/** Store the destination of the edge. */
	private String dst;
	
	/** Store the list of shortest paths. */
	private ArrayList<ArrayList<String>> pathList = new ArrayList<ArrayList<String>>();
	
	/** Store the number of shortest paths. */
	private int numOfPath = 0;
	
	/** Store the length of the shortest path. */
	private double length = 0;

	/**
	 * Sets the source of the edge.
	 *
	 * @param startId the ID of the source node
	 */
	public void setSrc(String startId) {
		src = startId;
	}
	
	/**
	 * Sets the destination of the edge.
	 *
	 * @param endId the ID of the destination node
	 */
	public void setDst(String endId) {
		dst = endId;
	}
	
	/**
	 * Gets the ID of the source node.
	 *
	 * @return the ID of the source node
	 */
	public String getSrc() {
		return src;
	}
	
	/**
	 * Gets the ID of the destination node.
	 *
	 * @return the ID of the destination node
	 */
	public String getDst() {
		return dst;
	}
	
	/**
	 * Gets the list of shortest paths.
	 *
	 * @return the list of shortest paths
	 */
	public ArrayList<ArrayList<String>> getPathList() {
		return pathList;
	}
	
	/**
	 * Gets the length of the shortest path.
	 *
	 * @return the length of the shortest path
	 */
	public double getLength(){
		return length;
	}
	
	/**
	 * Gets the number of shortest paths.
	 *
	 * @return the number of shortest paths
	 */
	public int getNumOfPath(){
		return numOfPath;
	}
	
	/**
	 * Find a shortest path.
	 *
	 * @param sp the shortest path
	 */
	ShortestPath(ShortestPath sp) {
		src = sp.getSrc();
		dst = sp.getDst();
		pathList = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> path : sp.getPathList()) {
			pathList.add( new ArrayList<>(path) );
		}
		numOfPath = sp.getNumOfPath();
		length = sp.getLength();
	}
	
	/**
	 * Find a shortest path.
	 *
	 * @param graph       the graph
	 * @param startNodeId the ID of the start node
	 * @param endNodeId   the ID of the end node
	 * @throws NotFoundNodeException if the node is not found
	 */
	ShortestPath(UndirectedWeightedGraph graph, String startNodeId, String endNodeId) throws NotFoundNodeException {
		
		if (!graph.containsNode(startNodeId)) {
			throw new NotFoundNodeException(graph, startNodeId);
		}		
		if (!graph.containsNode(endNodeId)) {
			throw new NotFoundNodeException(graph, endNodeId);
		}	

		src = startNodeId;
		dst = endNodeId;
		
		if (endNodeId.equals(startNodeId)) {
			length = 0;
			numOfPath = 1;
			ArrayList<String> path = new ArrayList<String>();
			path.add(startNodeId);
			pathList.add(path);
		}
		else {
			// create a HashMap of updated distance from the starting node to every node
			// initially it is   0, inf, inf, inf, ...
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
			HashMap<String, HashSet<String>> precedence = new HashMap<String, HashSet<String>>();
			for (String nodeId : graph.getNodeList().keySet()) {
				if ( nodeId.equals(startNodeId) ) {
					precedence.put(nodeId, null);	// the start node will have no preceding node
				}
				else { 
					precedence.put(nodeId, new HashSet<String>());
				}
			}
			
			//
			Set<String> unvisitedNode = new HashSet<String>();
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
				for (Node neighborNode : graph.getNodeList().get(minUnvisitedNode).getNeighbors().keySet()) {
					double distanceFromTheMinNode = distance.get(minUnvisitedNode) + graph.getNodeList().get(minUnvisitedNode).getNeighbors().get(neighborNode).getWeight();
					// if the distance of the neighbor can be shorter from the current node, change 
					// its details in distance and precedence
					if ( distanceFromTheMinNode < distance.get(neighborNode.getId()) ) {
						distance.replace(neighborNode.getId(), distanceFromTheMinNode);
						// renew the precedence of the neighbor node
						precedence.put(neighborNode.getId(), new HashSet<String>());
						precedence.get(neighborNode.getId()).add(minUnvisitedNode);
					}
					else if (distanceFromTheMinNode == distance.get(neighborNode.getId())) {
						// unlike dijkstra's algorithm, multiple path should be update into the precedence
						// if from another node the distance is the same, add it to the precedence
						precedence.get(neighborNode.getId()).add(minUnvisitedNode);
					}
				}			
				
				// if the current node in the process is the end node, we can stop the while loop here
				if (minUnvisitedNode == endNodeId) {
					break;
				}
					
			}
			
			
			
			if (distance.get(endNodeId) == Double.MAX_VALUE) {
				// in case there is no shortest path between the 2 nodes
				length = 0;
				numOfPath = 0;
				pathList = null;
			}
			else {
				// other wise we have these information
				length = distance.get(endNodeId);
				//numOfPath = this.getNumPath(precedence, startNodeId, endNodeId);
				pathList = this.findPathList(precedence, startNodeId, endNodeId);
				numOfPath = pathList.size();
			}
			
		}	// END ELSE		
	}
	
	/**
	 * Find the list of shortest paths.
	 *
	 * @param precedence the map of parents of node
	 * @param startId the ID of start node 
	 * @param endId the ID of end node
	 * @return the list of shortest paths
	 */
	// get the list of path from precedence HashMap
	private ArrayList< ArrayList<String> > findPathList(HashMap<String, HashSet<String>> precedence, String startId, String endId) {
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
				for (ArrayList<String> prePath : findPathList(precedence, startId, nodeId)) {
					// add endId to the end of the list
					prePath.add(endId);
					// then add the path to pathList
					pathList.add(prePath);
				}
			}
		}
			
		return pathList;
	}

}
