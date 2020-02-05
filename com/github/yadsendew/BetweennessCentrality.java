package com.github.yadsendew;

import java.util.ArrayList;

public class BetweennessCentrality {
	String nodeId;
	double bcm;
	
	public String getNodeId() {
		return nodeId;
	}
	public double getBCM(){
		return bcm;
	}
	BetweennessCentrality(UndirectedWeightedGraph graph, String nodeId) {

		this.nodeId = nodeId;
		
		ArrayList<String> nodeArrayList = new ArrayList<String>( graph.getNodeList().keySet() );
		
		// for each start node, different from nodeId
		
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
				// System.out.println(startId + " " + endId);
				// System.out.println(sMatrix.getShortestPath(startId, endId));
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
}
