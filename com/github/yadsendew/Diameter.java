package com.github.yadsendew;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class Diameter {
    double diameter;

    public static double calculate(UndirectedWeightedGraph graph) {
        // make a copy of the graph
		UndirectedWeightedGraph copyGraph = new UndirectedWeightedGraph(graph);
		
		// get the ArrayList of node id to run the for loop with iterator
		ArrayList<String> nodeIdList = new ArrayList<String>( graph.getNodeId() );	// make a copy of nodeId
		
		double diameter = 0;
		
		// check the length of shortest path for every pair of nodes in the list
		// loop for every node in the list
		for (String nodeId : nodeIdList) {
			
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