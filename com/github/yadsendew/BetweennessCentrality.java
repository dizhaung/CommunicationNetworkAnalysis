package com.github.yadsendew;

import java.util.ArrayList;
import java.util.logging.*;

/**
 * The Class BetweennessCentrality calculate the betweenness centrality of the node of the graph.
 */
public class BetweennessCentrality {

	final Logger LOGGER = Logger.getLogger("PublicLogger"); 
	
	/** Store the ID of the node. */
	private String nodeId;
	
	/** Store the betweenness centrality measure. */
	private double bcm;
	
	
	/**
	 * Calculate the betweenness centrality measure.
	 *
	 * @param graph  the graph
	 * @param nodeId the ID of the node
	 * @throws NotFoundNodeException if the node is not found
	 */
	BetweennessCentrality(UndirectedWeightedGraph graph, String nodeId) throws NotFoundNodeException {

		LOGGER.info("Find betweenness centrality measurement of node " + nodeId);
		this.nodeId = nodeId;

		if (!graph.containsNode(nodeId)) {
			throw new NotFoundNodeException(graph, nodeId);
		}
		
		ArrayList<String> nodeArrayList = new ArrayList<String>( graph.getNodeList().keySet() );
				
		ShortestPathMatrix sMatrix = graph.getShortestPathMatrix();

		for (int i = 0; i < nodeArrayList.size(); i++) {
			
			String startId = nodeArrayList.get(i);
			if (startId.equals(nodeId)) {
				continue;
			}
			
			// for each end node, different from start node, nodeId
			for (int j = i; j < nodeArrayList.size(); j++) {
				String endId = nodeArrayList.get(j);
				if (endId.equals(nodeId) ) {
					continue;
				}
				
				// find the number and the list of shortest path between 1st and 2nd node
				ShortestPath shortestPath = sMatrix.getShortestPath(startId, endId);
				ArrayList<ArrayList<String>> pathList = shortestPath.getPathList();
					
				int sigma1 = 0;
				int sigma2 = pathList.size(); 
					
				// find the number of SP go through third node
				for (ArrayList<String> path : pathList) {
					if (path.contains(nodeId)) {
						sigma1 += 1;
					}
				}
				
				// add sigma1 / sigma2 into bcl
				bcm += ((double) sigma1 / sigma2);		
				
			}
		}
	}
	/**
	 * Gets the ID of the node.
	 *
	 * @return the ID of the node.
	 */
	public String getNodeId() {
		return nodeId;
	}
	
	/**
	 * Gets the betweenness centrality measure.
	 *
	 * @return the betweenness centrality measure
	 */
	public double getBCM(){
		return bcm;
	}

}
