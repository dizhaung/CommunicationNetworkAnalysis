package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShortestPathMatrix {
	private HashMap< String, HashMap< String, ShortestPath>> shortestPathMatrix;

	ShortestPathMatrix() {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath>>();
	}

    ShortestPathMatrix(UndirectedWeightedGraph graph) throws NotFoundNodeException {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath> >();
		
		ArrayList<String> nodeArrayList = new ArrayList<String>( graph.getNodeList().keySet() );
		
		// initualize the shortestPathMatrix
		for (int i = 0; i < nodeArrayList.size(); i++) {
			for (int j = 0; j < nodeArrayList.size(); j++) {
				String startId = nodeArrayList.get(i);
				shortestPathMatrix.put(startId, new HashMap< String, ShortestPath>());
				String endId = nodeArrayList.get(j);
				shortestPathMatrix.put(endId, new HashMap< String, ShortestPath>());
			}
		}	

		for (int i = 0; i < nodeArrayList.size(); i++) {
			String startId = nodeArrayList.get(i);

			// 
			for (int j = i; j < nodeArrayList.size(); j++) {
                
				String endId = nodeArrayList.get(j);
				
				// list of shortest path from 1st node to 2nd node
				ShortestPath shortestPath = new ShortestPath(graph, startId, endId);
				
				shortestPathMatrix.get(startId).put(endId, shortestPath);
				
				// list of shortest path from 2nd node to 1st node
				if (i != j) {
					// make a copy of the shortestPath
					ShortestPath shortestPathReverse = new ShortestPath(shortestPath);
	
					// reverse its attributes
					shortestPathReverse.setSrc(shortestPath.getDst());
					shortestPathReverse.setDst(shortestPath.getSrc());
					for (ArrayList<String> path : shortestPathReverse.getPathList()) {
						Collections.reverse(path);
					}
	
					shortestPathMatrix.get(endId).put(startId, shortestPathReverse);
				}
							
			} 	// END LOOP
			
		}	// END LOOP
	}
	
    public HashMap< String, HashMap< String, ShortestPath>> getMatrix(){
        return shortestPathMatrix;
    }
	public ShortestPath getShortestPath(String startId, String endId) {
		return shortestPathMatrix.get(startId).get(endId);
	} 
}

