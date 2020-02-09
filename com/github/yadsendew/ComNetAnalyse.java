/*
*	In the purpose of project "Communication Network Analysis"
*	of the "Advanced OOP with Java" course - WS1920 
*	Computer Science Department – Frankfurt University of Applied Sciences
*	
*	Authors: 
*		Ngo Minh Thong, 
*		Luu Nguyen Phat, 
*		Tran Huu Le Huy,
*		Nguyen Quynh Huong.
*/

package com.github.yadsendew;

import com.github.yadsendew.GraphParser;
import com.github.yadsendew.GraphWriter;
import com.github.yadsendew.ShortestPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
public class ComNetAnalyse {
	public static void main(String[] args) throws NotFoundNodeException {
		// setting up logger
		final Logger LOGGER = Logger.getLogger("PublicLogger"); 
		LOGGER.setUseParentHandlers(false);
		Handler fileHandler;
		try {
			fileHandler = new FileHandler("logfile.log", true); 
			LOGGER. addHandler ( fileHandler ); 
			fileHandler.setFormatter(new SimpleFormatter()); 
			fileHandler.setLevel(Level.ALL); 
			throw new IOException();
		} 
		catch ( SecurityException | IOException e) {
		}
		LOGGER.info("\n\n***************Program has started running...***************\n");
		// analyse the arguments
		Arguments myArgs = new Arguments();
		myArgs.analyse(args);
		// // Specify path of file name
		String path = "resources/" + myArgs.getFileName();
		// Parse graphml to graph object
		Thread a = new Thread(new ReadFileThread(myArgs.getFileName()));
		a.start();
		LOGGER.info("Parsing graph from input file");
		UndirectedWeightedGraph graph = GraphParser.parse(path);
		
		a.interrupt();
		try {	// wait for the interrupt finish
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (myArgs.getTaskAnalysed().size() == 0) {
			ExecutingThread thread = new ExecutingThread();
			thread.start();

			// get all attributes of the graph
			int nodeNum = graph.getTotalNodes();
			int edgeNum = graph.getTotalNodes();
			ArrayList<String> nodeIdList = graph.getNodeId();
			ArrayList<String> edgeIdList = graph.getEdgeId();
			boolean connectivity = Connectivity.isConnected(graph);
			double diameter = Diameter.calculate(graph);

			thread.interrupt();
			a.interrupt();
			try {	// wait for the interrupt finish
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			// Print all attributes of the graph if there are no task from the user
			System.out.println("### Graph attributes ###");
			// get number of node
			System.out.println("\t" + "Number of nodes: " + nodeNum);
			
			// get number of edge
			System.out.println("\t" + "Number of edges: " + edgeNum);
			
			// print all vertices's ID
			System.out.println("\t" + "Vertex IDs: " + nodeIdList);
			
			// 3. print all edges's ID
			System.out.println("\t" + "Edge IDs: " + edgeIdList);
			
			// check connectivity
			System.out.println("\t" + "Graph is" + ( connectivity == true ? " " : "not ") + "connected");
			
			// get diameter
			System.out.println("\t" + "Gragh diameter: " + diameter);
		}
		else {
			ExecutingThread thread = new ExecutingThread();
			thread.start();

			ArrayList<Object> outputTask = new ArrayList<Object>();
			for (ArrayList<String> task : myArgs.getTaskAnalysed()) {
				// find shortest path between 2 nodes
				if (task.get(0).equals("-s")) {

					String startId = task.get(1);
					String endId = task.get(2);

					// get the list of shortest path then choose the 1st as the default path
					ShortestPath shortestPathInfo = new ShortestPath(graph, startId, endId);

					outputTask.add(shortestPathInfo);
				}
				// betweenness centrality measure
				else if (task.get(0).equals("-b")) {
					String nodeId = task.get(1);

					BetweennessCentrality bCentrality = new BetweennessCentrality(graph, nodeId);
					outputTask.add(bCentrality);
				} // end BCM
				else if (task.get(0).equals("-a")) {
					String outputFile = task.get(1);	

					if (outputFile.contains(".xml") || outputFile.contains(".graphml")){
						GraphWriter.exportToXML(graph, path, outputFile);
					} else {
						GraphWriter.exportToText(graph, path, outputFile);
					}
					

					outputTask.add(outputFile);
				} // end write file
			}

			thread.interrupt();
			try {	// wait for the interrupt finish
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			// 4. print task in CMD
			for (Object output : outputTask) {

				// 4.1 find shortest path between 2 nodes
				if ( output.getClass() == ShortestPath.class ) {
					ShortestPath sp = (ShortestPath) output;

					System.out.println("### Shortest path ###");					
					
					String startId = sp.getSrc();
					String endId = sp.getDst();
					
					// get the list of shortest path then choose the 1st as the default path
					ArrayList< ArrayList<String>> shortestPathList = sp.getPathList();
					ArrayList<String> shortestPath = shortestPathList.get(0);	// get the 1st path to print to the cmd
					double length = (Double) sp.getLength();
					
					System.out.print("\t" + "Shortest path " + startId + " to " + endId);
					System.out.print(": path -> " + shortestPath);
					System.out.println("; length -> " + length);
					
					System.out.println();
					
				}	// end shortest path
				
				// 4.2 betweenness centrality measure
				else if ( output.getClass() == BetweennessCentrality.class ) {
					BetweennessCentrality bcm = (BetweennessCentrality) output;
					System.out.println("### Betweenness centrality ###");
					
					String nodeId = bcm.getNodeId();

					System.out.println("\t" + "Node " + nodeId + ": " + bcm.getBCM());	
					
					System.out.println();
				}	// end BCM
				else if ( output.getClass() == String.class ) {
					System.out.println("### Output file ###");
					System.out.println("Written file(s): " + output );

					System.out.println();
				}
			}
		}	// end for loop 
		
	}
	
}