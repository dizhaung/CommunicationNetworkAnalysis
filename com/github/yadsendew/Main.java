package com.github.yadsendew;

import com.github.yadsendew.GraphParser;
import com.github.yadsendew.GraphWriter;
import com.github.yadsendew.ShortestPath;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
		// Start running time
		long startpoint = System.currentTimeMillis();
		
		// Retrieve requests (arguments) from user
    	Arguments myArgs = new Arguments();
        myArgs.analyse(args);
		
		// Specify path of file name
		String path = "resources/" + myArgs.getFileName();
		System.out.println(myArgs.getFileName());

    	// Parse graphml to graph object
		UndirectedWeightedGraph graph = GraphParser.parse(path);
		System.out.println(System.currentTimeMillis() - startpoint);

		// Controller
		if (myArgs.getOutputFileList().size() == 0) { // output in command line
		// 1. Print all attributes of the graph
			System.out.println("### Graph attributes ###");
			// get number of node
			System.out.println("\t" + "Number of nodes: " + graph.getTotalNodes());
			
			// get number of edge
			System.out.println("\t" + "Number of edges: " + graph.getTotalEdges());
			
			// check connectivity
			System.out.println("\t" + "Is connected? " + ( graph.isConnected() == true ? "Yes" : "No") );
			
			long startPoint = System.currentTimeMillis();
			// get diameter
			System.out.println("\t" + "Diameter " + graph.getDiameter());
			System.out.println(System.currentTimeMillis() - startPoint);
			
			// 2. print all vertices's ID
			System.out.println("### Vertices ###");
			int i = 0;
			for (String nodeId : graph.getNodeList().keySet()) {
				System.out.println("\t" + "Node " + i + ": '" + nodeId +"'");
				i++;
			}
			
			// 3. print all edges's ID
			System.out.println("### Edges ###");
			i = 0;
			for (String edgeId : graph.getEdgeList().keySet()) {
				System.out.println("\t" + "Edge " + i + ": '" + edgeId +"'");
				i++;
			}
			
			// 4. other task
			for (ArrayList<String> task : myArgs.getTaskAnalised()) {
				// 4.1 find shortest path between 2 nodes
				if ( task.get(0).equals("-s") ) {
					System.out.println("### Shortest path ###");
					
					// get the ArrayList of nodeList
					String startId = task.get(1);
					String endId = task.get(2);
					// check if id are valid
					if ( !graph.getNodeList().keySet().contains(startId) ) {
						System.out.println("There is no node ID " + startId);
						System.exit(0);
					}
					if ( !graph.getNodeList().keySet().contains(endId) ) {
						System.out.println("There is no node ID " + endId);
						System.exit(0);
					}
					
					// get the list of shortest path
					ShortestPath shortestPath = new ShortestPath(graph, startId, endId);
					
					System.out.print("From " + startId + " to " + endId + ": ");
					// get(0): Display the first found path only
					System.out.println(shortestPath.getPathList().get(0));
					System.out.println("Length: " + shortestPath.getLength());
					
				}	// end shortest path
				
				// 4.2 betweenness centrality measure
				else if ( task.get(0).equals("-b") ) {
					System.out.println("### Betweenness centrality ###");
					
					// Get node id from arguments
					String nodeId = task.get(1);
					// Check if id is valid
					if ( !graph.getNodeList().keySet().contains(nodeId) ) {
						System.out.println("There is no node ID " + nodeId);
						System.exit(0);
					}
					// Create BetweennessCentralityMeasurement object
					BetweennessCentrality bCentrality = 
							new BetweennessCentrality(graph, nodeId);
					System.out.println("Node " + nodeId + ": " + bCentrality.getBCM());
					
				}	// end BCM
			}	// end for loop 
			
		}	// end CMD
		
		else {	// Export to file
			// Output to XML format
			GraphWriter.exportToXML(graph, myArgs.getOutputFileList().get(0));
			// Output to normal text
			//GraphWriter.exportToText(graph, myArgs.getOutputFileList().get(1));
		}

		// End of running time
		System.out.println("----------------------------------------");
		System.out.print("Executed time: ");
        System.out.println(System.currentTimeMillis() - startpoint);
        
    }
}