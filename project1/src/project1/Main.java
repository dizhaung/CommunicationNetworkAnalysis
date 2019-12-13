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
	
	}
}
