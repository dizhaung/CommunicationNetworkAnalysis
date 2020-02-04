package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashMap;

public class ShortestPathMatrix {
    private HashMap< String, HashMap< String, ShortestPath>> shortestPathMatrix;
    ShortestPathMatrix(UndirectedWeightedGraph graph) {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath> >();
		
		ArrayList<String> nodeArrayList = new ArrayList<String>( graph.getNodeList().keySet() );
		
		for (int i = 0; i < nodeArrayList.size(); i++) {
			
			String startId = nodeArrayList.get(i);
			shortestPathMatrix.put(startId, new HashMap< String, ShortestPath>());
			
			for (int j = 0; j < nodeArrayList.size(); j++) {
				
				String endId = nodeArrayList.get(j);
				
				// list of shortest path between 1st and 2nd node
				ShortestPath shortestPath = new ShortestPath(graph, startId, endId);
				
				
				shortestPathMatrix.get(startId).put(endId, shortestPath);
				
			}
		}
    }
    public HashMap< String, HashMap< String, ShortestPath>> getMatrix(){
        return shortestPathMatrix;
    }
	public ShortestPath getShortestPath(String startId, String endId) {
		return shortestPathMatrix.get(startId).get(endId);
	} 
}