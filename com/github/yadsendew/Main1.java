package com.github.yadsendew;

public class Main1 {

	
    // public static void main(final String[] args) {
    //     final long startPoint = System.currentTimeMillis();
		
	// 	final Arguments a = new Arguments();
	// 	a.analyse(args);
		
	// 	//System.out.println("shit");
		
	// 	//Graph G = Read.toGraph("./src/small_graph.graphml");
	// 	//String a = "abc";
	// 	//System.out.println(a.charAt(0));

	// 	/*
	// 	 * Node a = new Node(); Node b = new Node();
	// 	 * 
	// 	 * a.setId("A"); b.setId("B");
	// 	 * 
	// 	 * Edge c = new Edge(); c.weight = 10;
	// 	 * 
	// 	 * a.neighbors.put(b, c); b.neighbors.put(a, c);
	// 	 * 
	// 	 * System.out.println(a.neighbors.size());
	// 	 * 
	// 	 * Graph G = new Graph();
	// 	 * 
	// 	 * Node A = new Node(); Node B = new Node(); Node C = new Node();
	// 	 * 
	// 	 * Edge D = new Edge(); Edge E = new Edge();
	// 	 * 
	// 	 * A.id = "a"; A.neighbors.put(B, D); A.neighbors.put(C, D);
	// 	 * 
	// 	 * B.id = "b"; B.neighbors.put(A, D);
	// 	 * 
	// 	 * C.id = "c"; C.neighbors.put(A, D);
	// 	 * 
	// 	 * D.id = "d"; D.src = A.id; D.dst = B.id; D.weight = 10;
	// 	 * 
	// 	 * E.id = "e"; E.src = A.id; E.dst = C.id; E.weight = 5;
	// 	 * 
	// 	 * G.addNode("a", A); G.addNode("b", B); G.addNode("c", C);
	// 	 * G.addEdge("d", D); //G.addEdge("e", E);
	// 	 */		
	// 	//Graph DFSa = G.getDFS( G.nodeList.get("n0") );
		
	// 	//System.out.println(G.isConnected());
		
		
	// 	final ArrayList<Integer> HH = new ArrayList<Integer>();
	// 	HH.add(1);
	// 	HH.add(2);
	// 	HH.add(3);
		
	// 	final Set<Integer> FF = new HashSet<Integer>(HH);
	// 	FF.remove(2);
	// 	System.out.println(HH);
	// 	System.out.println(FF);
		
	// 	final Graph G1 = new Graph();
	// 	final Node N1 = new Node(); final Node N2 = new Node(); final Node N3 = new Node(); final Node N4 = new Node(); final Node N5 = new Node();
	// 	final Edge E1 = new Edge(); final Edge E2 = new Edge(); final Edge E3 = new Edge(); final Edge E4 = new Edge(); final Edge E5 = new Edge(); final Edge E6 = new Edge();
		
	// 	N1.setId("A"); N2.setId("B"); N3.setId("C"_; N4.setId("D"); N5.setId("E");
		
	// 	E1.setId("e1"); E1.getSrc(N1.getId()); E1.setDst(N2.getId()); E1.setWeight(2);	// AB
	// 	E2.setId("e2"); E2.getSrc(N1.getId()); E2.setDst(N3.getId()); E2.setWeight(1);	// AC
	// 	E3.setId("e3"); E3.getSrc(N3.getId()); E3.setDst(N4.getId()); E3.setWeight(3);	// CD
	// 	E4.setId("e4"); E4.getSrc(N2.getId()); E4.setDst(N4.getId()); E4.setWeight(2);	// BD
	// 	E5.setId("e5"); E5.getSrc(N2.getId()); E5.setDst(N5.getId()); E5.setWeight(3);	// BE
	// 	E6.setId("e6"); E6.getSrc(N4.getId()); E6.setDst(N5.getId()); E6.setWeight(1);	// DE
		
	// 	N1.addNeighbor(N2, E1); N1.addNeighbor(N3, E2);
	// 	N2.addNeighbor(N1, E1); N2.addNeighbor(N4, E4); N2.addNeighbor(N5, E5);
	// 	N3.addNeighbor(N1, E2); N3.addNeighbor(N4, E3);
	// 	N4.addNeighbor(N2, E4); N4.addNeighbor(N3, E3); N4.addNeighbor(N5, E6);
	// 	N5.addNeighbor(N2, E5); N5.addNeighbor(N4, E6);
		
	// 	G1.addNode("A", N1); 
	// 	G1.addNode("B", N2);
	// 	G1.addNode("C", N3); 
	// 	G1.addNode("D", N4); 
	// 	G1.addNode("E", N5);
	// 	G1.addEdge("e1", E1); 
	// 	G1.addEdge("e2", E2); 
	// 	G1.addEdge("e3", E3);
	// 	G1.addEdge("e4", E4); 
	// 	G1.addEdge("e5", E5);
	// 	G1.addEdge("e6", E6);
		
		
	// 	final String startNodeId = "A";
	// 	final String endNodeId = "E";

		
	// 	final ArrayList<Object> demo = G1.getShortestPath(startNodeId, endNodeId);
	// 	final double length = (double) demo.get(0);
	// 	final int numOfPath = (int) demo.get(1);
	// 	final ArrayList<ArrayList<String>> pathList = (ArrayList<ArrayList<String>>) demo.get(2);
		
	// 	System.out.println("Shortest path from " + startNodeId + " to " + endNodeId + ": ");
	// 	if (length == 0) {
	// 		System.out.println("\tthere is no shortest path between them");
	// 	}
	// 	else {
	// 		for (final ArrayList<String> path : pathList) {
	// 			System.out.print("\t");
	// 			for(final String nodeId : path) {
	// 				if (nodeId != path.get(0)) {
	// 					System.out.print( " -> " + nodeId);
	// 				}
	// 				else {
	// 					System.out.print(nodeId);
	// 				}
	// 			}
	// 			System.out.println();
				
	// 		}
	// 		System.out.println("\tLength: " + length);
	// 	}
		
	// 	System.out.println(G1.getBCM1("B"));
	// 	System.out.println(G1.getBCM2("B"));
	// 	System.out.println(G1.getDiameter());
		
		
		
    // }


}