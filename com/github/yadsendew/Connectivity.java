package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

/**
 * The Class Connectivity represent the connectivity of the graph.
 */
public class Connectivity {

    final static Logger LOGGER = Logger.getLogger("PublicLogger"); 
    
    /** Store the connectivity, return true if the graph is connected. */
    private boolean connectivity = false;

    /**
     * Gets the connectivity.
     *
     * @return the connectivity
     */
    public boolean getConnectivity() {
        return connectivity;
    }

    /**
     * Checks if the graph is connected.
     *
     * @param graph the graph
     * @return true, if the graph is connected
     */
    public static boolean isConnected (UndirectedWeightedGraph graph) {
        LOGGER.info("Find graph connectivity");

        // create a DFSTree from the graph to check graph connectivity
		// choose a random node
		Random rand = new Random();
		ArrayList<String> keys = new ArrayList<String>( graph.getNodeId() );
		String randNodeId = keys.get( rand.nextInt( keys.size() ));
		
		// DFSTree
		UndirectedWeightedGraph DFSTree = DepthFirstSearch.getDFSTree(graph, randNodeId);

		// return true if the amount of node of the graph and DFSTree are the same
		if( DFSTree.getTotalNodes() == graph.getTotalNodes()) {
            return true;
		}
		else {
            return false;
        }
    }
}