package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.logging.*;

/**
 * The Class Diameter represent the diameter of the graph.
 */
public class Diameter {

	final static Logger LOGGER = Logger.getLogger("PublicLogger"); 
    
    /** Store the diameter of the graph. */
    private double diameter;

    /**
     * Calculate the diameter of the graph.
     *
     * @param graph the graph
     * @return the diameter
     */
    public static double calculate(UndirectedWeightedGraph graph) {

		LOGGER.info("Find graph diameter");

        // make a copy of the graph
		UndirectedWeightedGraph copyGraph = new UndirectedWeightedGraph(graph);
		
		// get the ArrayList of node id to run the for loop with iterator
		ArrayList<String> nodeIdList = new ArrayList<String>( graph.getNodeId() );	// make a copy of nodeId
		
		double diameter = 0;
		
		// check the length of shortest path for every pair of nodes in the list
		// loop for every node in the list
		for (String nodeId : nodeIdList) {
			
			@SuppressWarnings("unchecked")
			HashSet<Double> distance = new HashSet<Double>( ( (HashMap<String, Double>) Dijkstra.getDijkstraTree(copyGraph, nodeId).get(1) ).values() );
			for (double i : distance) {
				if (i != Double.MAX_VALUE && i > diameter) {
					diameter = i;
				}
			}
		
		}
		// return the diameter
		return diameter;
    }


}