package project1;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// TO_ADD: to check the output type in the input
// TO_ADD: add the output 

public class Arguments {
	public static void main(String[] args) throws Exception {
		
		// if there is no argument, stop the program
		if (args.length == 0) {
			System.out.println("There were no commandline arguments passed!");
			System.exit(0);
        }
		
		String path = "./src/" + args[0];
		
		File f = new File(path);
		
		// check if the file argument exist before doing stuff
		if (!f.exists()) {
			System.out.println("The file does not exist");
		}
		else {
			
			// devide the task in the argument
			ArrayList<String> arguments = new ArrayList<String>( Arrays.asList(args) );
			
			boolean isOutputFile = false;	// check the type of output
			ArrayList<String> outputFileList = new ArrayList<String>();
			ArrayList< ArrayList<String>> otherTask = new ArrayList< ArrayList<String>>();	// store the task in the given order
			boolean isInputValid = true;
		
			for (int i = 0; i < args.length; i++) {
				// irrelevant task
				if (args[i].charAt(0) == '-' && !args[i].equals("-s") && !args[i].equals("-a") && !args[i].equals("-b")) {		// if the argument begin with '-'
					// begin with '-' but is not '-s', '-a', '-b'
					System.out.println("Unknown task " + args[i] + ". Try -s, -a or -b.");
				}
				// relevant task, irrelevant node input
				else if (args[i].equals("-s")) {
					// -s needs two nodes
					if (i == args.length - 1 || args[i+1].equals("-s") || args[i+1].equals("-a") || args[i+1].equals("-b")) {
						System.out.println("The task " + args[i] + "has no argument. 2 are needed");
						isInputValid = false;
					}
					else if (i == args.length - 2 || args[i+2].equals("-s") || args[i+2].equals("-a") || args[i+2].equals("-b")) {
            			System.out.println("The task " + args[i] + " has only 1 argument. 2 are needed");
            			isInputValid = false;
					}
            		else if (i != args.length - 3 && !args[i+3].equals("-s") && !args[i+3].equals("-a") && !args[i+3].equals("-b")) {
            			System.out.println("The task " + args[i] + " has more than 2 arguments.");
            			isInputValid = false;
            		}
            		else {
           				ArrayList<String> task = new ArrayList<String>();
           				task.add(args[i]);	// add -s
           				task.add(args[i+1]);	// add node1
           				task.add(args[i+2]);	// add node2
           				
           				otherTask.add( task );
           				i += 2;	// pass the next 2 arguments	
           			}
           		}	// end if -s
           		else if (args[i].equals("-b")) {
           			// -b needs 1 node
           			if (i == args.length - 1 || args[i+1].equals("-s") || args[i+1].equals("-a") || args[i+1].equals("-b")) {
           				System.out.println("The task " + args[i] + " has no argument. 1 are needed");
           				isInputValid = false;
           			}
           			else if (i != args.length - 2 && !args[i+2].equals("-s") && !args[i+2].equals("-a") && !args[i+2].equals("-b")) {
           				System.out.println("The task " + args[i] + " has more than 1 argument.");
           				isInputValid = false;
           			}
           			else {
           				ArrayList<String> task = new ArrayList<String>();
           				task.add(args[i]);	// add -b
           				task.add(args[i+1]);	// add node
           				
           				otherTask.add( task );
           				i += 1;	// pass the next argument	
           			}
           		}	// end if -b
           		else if (args[i].equals("-a")) {
           			// -a needs 1 file
           			if (i == args.length - 1 || args[i+1].equals("-s") || args[i+1].equals("-a") || args[i+1].equals("-b")) {
           				System.out.println("The task " + args[i] + "has no argument. 1 are needed");
           				isInputValid = false;
           			}
           			else if (i != args.length - 2 && !args[i+2].equals("-s") && !args[i+2].equals("-a") && !args[i+2].equals("-b")) {
           				System.out.println("The task " + args[i] + " has only 1 argument. 2 are needed");
           				isInputValid = false;
           			}
           			else {
           				
           				isOutputFile = true;
           				outputFileList.add(args[i+1]);	// add the required output file
           				
           				i += 1;	// pass the next argument
           			}
           		}	// end if -a
			
			
			}	// end for loop
			
			/*
			for (ArrayList<String> task : otherTask) {
				for (String i : task) {
					System.out.print(i + " ");
				}
				System.out.println();
			}
			for (String file : outputFileList) {
				System.out.println(file);
			}
			System.out.println(isOutputFile);
			*/
			
			if (isInputValid == false) {
				System.exit(0);
			}
                   	
			Graph graph = Read.toGraph(path);
			
			if (isOutputFile == false) {	// output in command line
				// 1. Print all attributes of the graph
				System.out.println("### Graph attributes ###");
				// get number of node
				System.out.println("\t" + "Number of nodes: " + graph.getTotalNodes());
				
				// get number of edge
				System.out.println("\t" + "Number of edges: " + graph.getTotalEdges());
				
				// check connectivity
				System.out.println("\t" + "Is connected? " + ( graph.isConnected() == true ? "Yes" : "No") );
				
				// get diameter
				System.out.println("\t" + "Diameter " + graph.getDiameter());
				
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
				for (ArrayList<String> task : otherTask) {
					// 4.1 find shortest path between 2 nodes
					if ( task.get(0).equals("-s") ) {
						System.out.println("### Dijkstra ###");
						
						// get the ArrayList of nodeList
						ArrayList<String> nodeIdList = new ArrayList<String>( graph.getNodeList().keySet() );
						
						
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
						
						// get the list of shortest path then choose the 1st as the default path
						ArrayList< ArrayList<String>> shortestPathList = (ArrayList< ArrayList<String>>) graph.getShortestPath(startId, endId).get(2);
						ArrayList<String> shortestPath = shortestPathList.get(0);
						
						System.out.print("Shortest path " + startId + " to " + endId +": " + shortestPath.get(0));
						for(String nodeId : shortestPath) {
							if (!nodeId.equals( shortestPath.get(0) )) {
								System.out.print(", " + nodeId);
							}
						}
						System.out.println();
						
					}	// end shortest path
					
					// 4.2 betweenness centrality measure
					else if ( task.get(0).equals("-b") ) {
						System.out.println("### Betweenness centrality ###");
						
						String nodeId = task.get(1);
						// check if id is valid
						if ( !graph.getNodeList().keySet().contains(nodeId) ) {
							System.out.println("There is no node ID " + nodeId);
							System.exit(0);
						}
						
						System.out.println(nodeId + ": " + graph.getBCM1(nodeId));
						
					}	// end BCM
				}	// end for loop 
				
			}	// end CMD
			else {	// output in graphml file
				
				// TO_ADD: create file whose names are listed in outputFileList
				// TO_ADD: add the relevant information into these file
				// TO_ADD: 
				
			}
			
				
			/*
			if (args.length == 1) {
				System.out.println("Graph properties: ");
			}
			else {
				for (int i = 1; i < args.length; i++) {
					
					if (args[i].equals("-s") ) {
						System.out.println("Graph properties:  ");
						System.out.println("s " + args[i+1] + " " + args[i+2]);
						break;
					}
					if (args[i].equals("-a")) {
						System.out.println("Graph properties:  ");
						System.out.println("a " + args[i+1]);
						break;
					}
					if (args[i].equals("-b")) {
						System.out.println("Graph properties:  ");
						System.out.println("b " + args[i+1]);
						break;
					}
					
					else {
						System.out.println("You have entered wrong command! Please check again! "); 
						break;
					}
				}
			}*/		//close if
		}	// close else
                
		
		
	}
}



