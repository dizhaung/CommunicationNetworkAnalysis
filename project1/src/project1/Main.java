package project1;

public class Main {

	public static void main(String[] args) {
		
		Node a = new Node();
		Node b = new Node();
	
		a.setId("A");
		b.setId("B");
	
		Edge c = new Edge();
		c.weight = 10;
		
		a.neighbors.put(b, c);
		b.neighbors.put(a, c);
		
		System.out.println(a.neighbors.size());
		
		Graph G = new Graph();
		
		Node A = new Node();
		Node B = new Node();
		Node C = new Node();
		
		Edge D = new Edge();
		Edge E = new Edge();
		
		A.id = "a";
		A.neighbors.put(B, D);
		A.neighbors.put(C, D);
		
		B.id = "b";
		B.neighbors.put(A, D);
		
		C.id = "c";
		C.neighbors.put(A, D);
		
		D.id = "d";
		D.src = A.id;
		D.dst = B.id;
		D.weight = 10;
	
		E.id = "e";
		E.src = A.id;
		E.dst = C.id;
		E.weight = 5;
		
		G.nodeList.put("a", A);
		G.nodeList.put("b", B);
		G.nodeList.put("c", C);
		G.edgeList.put("d", D);
		//G.edgeList.put("e", E);
		Graph DFSa = G.getDFS( G.nodeList.get(A.id) );
		
		System.out.println(G.isConnected());
	
	}
}
