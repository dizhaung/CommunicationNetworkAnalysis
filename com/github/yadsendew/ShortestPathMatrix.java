package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.*;

/**
 * The Class ShortestPathMatrix stores all shortest paths of the graph.
 */
public class ShortestPathMatrix {

	final Logger LOGGER = Logger.getLogger("PublicLogger"); 
	
	/** Store shortest path matrix. */
	private HashMap< String, HashMap< String, ShortestPath>> shortestPathMatrix;

	/**
	 * Initialize a new shortest path matrix.
	 */
	ShortestPathMatrix() {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath>>();
	}

    /**
	 * Initialize a new shortest path matrix.
	 *
	 * @param graph the graph
	 * @throws NotFoundNodeException if the node is not found
	 */
	ShortestPathMatrix(UndirectedWeightedGraph graph) throws NotFoundNodeException {

		LOGGER.info("Get shortest math matrix");

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

		LOGGER.info("Finish shortest path matrix");
	}
	
    /**
     * Gets the shortest path matrix.
     *
     * @return the shortest path matrix
     */
    public HashMap< String, HashMap< String, ShortestPath>> getMatrix(){
        return shortestPathMatrix;
    }
	
	/**
	 * Gets the shortest path.
	 *
	 * @param startId the ID of the start node
	 * @param endId the ID of the end node
	 * @return the shortest path
	 */
	public ShortestPath getShortestPath(String startId, String endId) {
		return shortestPathMatrix.get(startId).get(endId);
	} 
}

