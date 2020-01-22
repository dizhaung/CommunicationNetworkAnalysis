import java.util.ArrayList;
import java.util.Collections;

public class Main {
	
	public static Graph graph = new Graph();
	public static void main(String[] args) throws Exception {
		
		Arguments a = new Arguments();
		a.analyse(args);
		
		//System.out.println("shit");
		
		//Graph G = Read.toGraph("./src/small_graph.graphml");
		//String a = "abc";
		//System.out.println(a.charAt(0));

		/*
		 * Node a = new Node(); Node b = new Node();
		 * 
		 * a.setId("A"); b.setId("B");
		 * 
		 * Edge c = new Edge(); c.weight = 10;
		 * 
		 * a.neighbors.put(b, c); b.neighbors.put(a, c);
		 * 
		 * System.out.println(a.neighbors.size());
		 * 
		 * Graph G = new Graph();
		 * 
		 * Node A = new Node(); Node B = new Node(); Node C = new Node();
		 * 
		 * Edge D = new Edge(); Edge E = new Edge();
		 * 
		 * A.id = "a"; A.neighbors.put(B, D); A.neighbors.put(C, D);
		 * 
		 * B.id = "b"; B.neighbors.put(A, D);
		 * 
		 * C.id = "c"; C.neighbors.put(A, D);
		 * 
		 * D.id = "d"; D.src = A.id; D.dst = B.id; D.weight = 10;
		 * 
		 * E.id = "e"; E.src = A.id; E.dst = C.id; E.weight = 5;
		 * 
		 * G.nodeList.put("a", A); G.nodeList.put("b", B); G.nodeList.put("c", C);
		 * G.edgeList.put("d", D); //G.edgeList.put("e", E);
		 */		
		//Graph DFSa = G.getDFS( G.nodeList.get("n0") );
		
		//System.out.println(G.isConnected());
		
		/*
		ArrayList<Integer> HH = new ArrayList<Integer>();
		HH.add(1);
		HH.add(2);
		HH.add(3);
		
		Set<Integer> FF = new HashSet<Integer>(HH);
		FF.remove(2);
		System.out.println(HH);
		System.out.println(FF);
		
		Graph G1 = new Graph();
		Node N1 = new Node(); Node N2 = new Node(); Node N3 = new Node(); Node N4 = new Node(); Node N5 = new Node();
		Edge E1 = new Edge(); Edge E2 = new Edge(); Edge E3 = new Edge(); Edge E4 = new Edge(); Edge E5 = new Edge(); Edge E6 = new Edge();
		
		N1.id = "A"; N2.id = "B"; N3.id = "C"; N4.id = "D"; N5.id = "E";
		
		E1.id = "e1"; E1.src = N1.getId(); E1.dst = N2.getId(); E1.weight = 2;	// AB
		E2.id = "e2"; E2.src = N1.getId(); E2.dst = N3.getId(); E2.weight = 1;	// AC
		E3.id = "e3"; E3.src = N3.getId(); E3.dst = N4.getId(); E3.weight = 3;	// CD
		E4.id = "e4"; E4.src = N2.getId(); E4.dst = N4.getId(); E4.weight = 2;	// BD
		E5.id = "e5"; E5.src = N2.getId(); E5.dst = N5.getId(); E5.weight = 3;	// BE
		E6.id = "e6"; E6.src = N4.getId(); E6.dst = N5.getId(); E6.weight = 1;	// DE
		
		N1.neighbors.put(N2, E1); N1.neighbors.put(N3, E2);
		N2.neighbors.put(N1, E1); N2.neighbors.put(N4, E4); N2.neighbors.put(N5, E5);
		N3.neighbors.put(N1, E2); N3.neighbors.put(N4, E3);
		N4.neighbors.put(N2, E4); N4.neighbors.put(N3, E3); N4.neighbors.put(N5, E6);
		N5.neighbors.put(N2, E5); N5.neighbors.put(N4, E6);
		
		G1.nodeList.put("A", N1); 
		G1.nodeList.put("B", N2);
		G1.nodeList.put("C", N3); 
		G1.nodeList.put("D", N4); 
		G1.nodeList.put("E", N5);
		G1.edgeList.put("e1", E1); 
		G1.edgeList.put("e2", E2); 
		G1.edgeList.put("e3", E3);
		G1.edgeList.put("e4", E4); 
		G1.edgeList.put("e5", E5);
		G1.edgeList.put("e6", E6);
		
		
		String startNodeId = "A";
		String endNodeId = "E";

		
		ArrayList<Object> demo = G1.getShortestPath(startNodeId, endNodeId);
		double length = (double) demo.get(0);
		int numOfPath = (int) demo.get(1);
		ArrayList<ArrayList<String>> pathList = (ArrayList<ArrayList<String>>) demo.get(2);
		
		System.out.println("Shortest path from " + startNodeId + " to " + endNodeId + ": ");
		if (length == 0) {
			System.out.println("\tthere is no shortest path between them");
		}
		else {
			for (ArrayList<String> path : pathList) {
				System.out.print("\t");
				for(String nodeId : path) {
					if (nodeId != path.get(0)) {
						System.out.print( " -> " + nodeId);
					}
					else {
						System.out.print(nodeId);
					}
				}
				System.out.println();
				
			}
			System.out.println("\tLength: " + length);
		}
		
		System.out.println(G1.getBCM1("B"));
		System.out.println(G1.getBCM2("B"));
		System.out.println(G1.getDiameter());
		*/
		
		String path = "./src/" + a.fileName;
		System.out.println(a.fileName);
		
		//File f = new File(path);
		System.out.println(path);
		
		Graph graph = Read.toGraph(path);
		
		ArrayList<String> aa = new ArrayList<String>(graph.getNodeList().keySet());
		//Collections.sort(aa);
		System.out.println();
		
		if (a.outputFileList.size() == 0) {	// output in command line
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
			for (ArrayList<String> task : a.taskAnalysed) {
				// 4.1 find shortest path between 2 nodes
				if ( task.get(0).equals("-s") ) {
					System.out.println("### Shortest path ###");
					
					// get the ArrayList of nodeList
					//ArrayList<String> nodeIdList = new ArrayList<String>( graph.getNodeList().keySet() );
					
					
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
					@SuppressWarnings("unchecked")
					ArrayList<Object> shortestPathInfo = new ArrayList<Object>(graph.getShortestPath(startId, endId));
					ArrayList< ArrayList<String>> shortestPathList = (ArrayList< ArrayList<String>>) shortestPathInfo.get(2);
					ArrayList<String> shortestPath = shortestPathList.get(0);
					double length = (Double) shortestPathInfo.get(0);
					
					System.out.print("Shortest path " + startId + " to " + endId);
					System.out.print(": path -> " + shortestPath);
					System.out.println("; length -> " + length);
					
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
					
					System.out.println("Node " + nodeId + ": " + graph.getBCM1(nodeId));
					
				}	// end BCM
			}	// end for loop 
			
		}	// end CMD
		else {	// output in graphml file
			
			// TO_ADD: create file whose names are listed in outputFileList
			// TO_ADD: add the relevant information into these file
			// TO_ADD: 
			
		}
		
		
	} 
}
