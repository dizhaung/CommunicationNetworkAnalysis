package project1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import project1.Read;

public class Main {
	public static int getNumPath( HashMap<String, HashSet<String>> precedence, String startId, String endId) {
		int num = 0;
		for (String nodeId : precedence.get(endId) ) {
			if (nodeId.equals(startId)) {
				num += 1;
			}
			else {
				num += getNumPath(precedence, startId, nodeId);
			}
		}
		return num;
	}
	
	public static Graph graph = new Graph();
	public static void main(String[] args) throws Exception {
		Graph G = Read.toGraph("./src/small_graph.graphml");

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
		
		
		Graph G1 = new Graph();
		Node N1 = new Node(); Node N2 = new Node(); Node N3 = new Node(); Node N4 = new Node();
		Edge E1 = new Edge(); Edge E2 = new Edge(); Edge E3 = new Edge(); Edge E4 = new Edge(); Edge E5 = new Edge();
		
		N1.id = "n1"; N2.id = "n2"; N3.id = "n3"; N4.id = "n4"; 
		
		E1.id = "e1"; E1.src = N1.getId(); E1.dst = N2.getId(); E1.weight = 2;
		E2.id = "e2"; E2.src = N2.getId(); E2.dst = N4.getId(); E2.weight = 3;
		E3.id = "e3"; E3.src = N1.getId(); E3.dst = N3.getId(); E3.weight = 1;
		E4.id = "e4"; E4.src = N3.getId(); E4.dst = N4.getId(); E4.weight = 4;
		E5.id = "e5"; E5.src = N1.getId(); E5.dst = N4.getId(); E5.weight = 5;
		
		N1.neighbors.put(N2, E1); N1.neighbors.put(N3, E3); N1.neighbors.put(N4, E5);
		N2.neighbors.put(N1, E1); N2.neighbors.put(N4, E2);
		N3.neighbors.put(N1, E3); N3.neighbors.put(N4, E4);
		N4.neighbors.put(N1, E5); N4.neighbors.put(N2, E2); N4.neighbors.put(N3, E4);
		
		
		G1.nodeList.put("n1", N1); G1.nodeList.put("n2", N2); G1.nodeList.put("n3", N3); G1.nodeList.put("n4", N4);
		G1.edgeList.put("e1", E1); 
		G1.edgeList.put("e2", E2); 
		G1.edgeList.put("e3", E3);
		G1.edgeList.put("e4", E4); G1.edgeList.put("e5", E5);
		String startNodeId = "n1";
		String endNodeId = "n4";
		
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
		
	}
}
