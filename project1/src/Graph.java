import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// TO_FIX: the HashMap object may change order of the key

public class Graph {
	
	public HashMap<String, Node> nodeList = new HashMap<String, Node>();// node id and node
	public HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); // edge id and edge
	private String edgeDefault;
	private String id;
	
	// Constructors
	public Graph()	{}
	
	public Graph(Graph graph) {
		nodeList = new HashMap<String, Node>(graph.getNodeList());
		edgeList = new HashMap<String, Edge>(graph.getEdgeList());
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
	public void addEdge(String id, Edge edge) {
		edgeList.put(id, edge);
	}
	
	public void setEdgedefault(String edgedefault) {
			edgeDefault = edgedefault;
	}
	
	public String getEdgedefault() {
		return edgeDefault;
	}
	
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
	
	
	// get the shortest path and the length between 2 given nodes
	// we use an alternated version of Dijkstra's Algorithm
	public ArrayList<Object> getShortestPath(String startNodeId, String endNodeId) {
		
		//
		double length;
		int numOfPath; 
		ArrayList< ArrayList<String>> pathList = new ArrayList< ArrayList<String>>();
		
		if (endNodeId.equals(startNodeId)) {
			length = 0;
			numOfPath = 0;
			ArrayList<String> path = new ArrayList<String>();
			path.add(startNodeId);
			pathList.add(path);
			return new ArrayList<Object>( Arrays.asList(length, numOfPath, pathList) ); 
		}
		
		// create a map of updated distance from the starting node to every node
		// initially it is   0, inf, inf, inf, ...
		Map<String, Double> distance = new HashMap<String, Double>();	
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
		
		// a map of preceding node of each node
		HashMap<String, HashSet<String>> precedence = new HashMap<String, HashSet<String>>();
		for (String nodeId : nodeList.keySet()) {
			if ( nodeId.equals(startNodeId) ) {
				precedence.put(nodeId, null);	// the start node will have no preceding node
			}
			else { 
				precedence.put(nodeId, new HashSet<String>());
			}
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
			
			// remove the node from unvisitedNode
			unvisitedNode.remove(minUnvisitedNode);
			
			// update the distance of its neighbors
			for (Node neighborNode : this.nodeList.get(minUnvisitedNode).neighbors.keySet()) {
				double distanceFromTheMinNode = distance.get(minUnvisitedNode) + this.nodeList.get(minUnvisitedNode).neighbors.get(neighborNode).getWeight();
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
			numOfPath = this.getNumPath(precedence, startNodeId, endNodeId);
			pathList = this.getPathList(precedence, startNodeId, endNodeId);
		}
		
		// return length, number of shortest paths and pathList
		return new ArrayList<Object>( Arrays.asList(length, numOfPath, pathList) );	
		
	}
	
	// count the number of shortest path from precedence map
	private int getNumPath( HashMap<String, HashSet<String>> precedence, String startId, String endId) {
		int num = 0;
		
		// recursive method: numPath of the endNode is the sum of numPath of its preceding node
		for (String nodeId : precedence.get(endId) ) {
			if (nodeId.equals(startId)) {
				num += 1;
			}
			else {
				num += getNumPath(precedence, startId, nodeId);
			}
		}
		return num;
	}
			

	// get the list of path from precedence map
	private ArrayList< ArrayList<String> > getPathList(HashMap<String, HashSet<String>> precedence, String startId, String endId) {
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
		Graph dijkstraTree = new Graph();
		
		// create a map of updated distance from the starting node to every node
		// initially it is   0, inf, inf, inf, ...
		Map<String, Double> distance = new HashMap<String, Double>();	
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
		
		// a map of preceding node of each node
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
			for (Node neighborNode : this.nodeList.get(minUnvisitedNode).neighbors.keySet()) {
				double distanceFromTheMinNode = distance.get(minUnvisitedNode) + this.nodeList.get(minUnvisitedNode).neighbors.get(neighborNode).getWeight();
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
	@SuppressWarnings("unchecked")
	public double getDiameter() {
		
		// make a copy of the graph
		Graph copyGraph = new Graph(this);
		
		// get the ArrayList of node id to run the for loop with iterator
		ArrayList<String> nodeIdList = new ArrayList<String>( nodeList.keySet() );
		
		double diameter = 0;
		
		// check the length of shortest path for every pair of nodes in the list
		// loop for every node in the list
		for (String nodeId : nodeIdList) {
			
			Set<Double> distance = new HashSet<Double>( ( (Map<String, Double>) copyGraph.getDijkstraTree(nodeId).get(1) ).values() );
			for (double i : distance) {
				if (i != Double.MAX_VALUE && i > diameter) {
					diameter = i;
				}
			}
			
			
		}
		/*
		for (int i = 0; i < nodeIdList.size(); i++) {
			String startId = nodeIdList.get(i);
			
			// loop for every node in the list which is different from the first node 
			for (int j = 0; j < nodeIdList.size(); j++) {
				if (j == i) {
					continue;
				}
				String endId = nodeIdList.get(j);
				
				// get the length of shortest path 
				double length = (double) this.getShortestPath(startId, endId).get(0);
				
				// if the length is longer, update the diameter
				if (length > diameter) {
					diameter = length;
				}
			}
		}*/
		
		// return the diameter
		return diameter;
		
	}
	
	
	// get the betweenness centrality measure
	@SuppressWarnings("unchecked")
	public double getBCM1(String nodeId) {
		
		double bcl = 0.0;
		
		// for each start node, different from nodeId
		for (String startId : nodeList.keySet()) {
			if (startId.equals(nodeId)) {
				continue;
			}
			
			// for each end node, different from start node, nodeId
			for (String endId : nodeList.keySet()) {
				if (endId.equals(nodeId) || endId.equals(startId)) {
					continue;
				}
				
				// find the number and the list of shortest path between 1st and 2nd node
				ArrayList<Object> spList = this.getShortestPath(startId, endId);
					
				int sigma1 = 0;
				int sigma2 = (int) spList.get(1);
				
				ArrayList< ArrayList<String>> pathList = (ArrayList< ArrayList<String>>) spList.get(2); 
					
				// find the number of SP go through third node
				for (ArrayList<String> path : pathList) {
					if (path.contains(nodeId)) {
						sigma1 += 1;
					}
				}
				
				// add sigma1 / sigma2 into bcl
				bcl += ((double) sigma1 / sigma2);
				//System.out.println(startId + "->" + nodeId + "->" + endId + ": " + sigma1 + " / " + sigma2);
			
			}	// end loop 2
		
		}	// end loop 1
		
		// return the value
		return bcl;
		
	}
	
	// another BCM method, idea from Phuoc
	public double getBCM2(String nodeId) {
		double bcl = 0.0;
		
		// start node, different from nodeId
				for (String startId : nodeList.keySet()) {
					if (startId.equals(nodeId)) {
						continue;
					}
					
					// end node, different from start node, nodeId
					for (String endId : nodeList.keySet()) {
						if (endId.equals(nodeId) || endId.equals(startId)) {
							continue;
						}
						
						// get length from startId to nodeId
						ArrayList<Object> spList1 = this.getShortestPath(startId, nodeId);
						double length1 = (double) spList1.get(0);
						
						// get length from nodeId to endId
						ArrayList<Object> spList2 = this.getShortestPath(nodeId, endId);
						double length2 = (double) spList2.get(0);
						
						// get length from startId to endId
						ArrayList<Object> spList3 = this.getShortestPath(startId, endId);
						double length3 = (double) spList3.get(0);
						
						if (length1 + length2 == length3) {
							int sigma1 = (int) spList1.get(1) * (int) spList2.get(1);	// num of path from 1st to 2nd * num of path from 2nd to 3rd
							int sigma2 = (int) spList3.get(1);
							
							//System.out.println(startId + "->" + nodeId + "->" + endId + ": " + sigma1 + " / " + sigma2);
							bcl += (double) sigma1 / sigma2;
						}
						else {
							continue;
						}
						
						
					}	// end loop 2
				}	// end loop 1
		
		// return the value
		return bcl;
	}
	
}
