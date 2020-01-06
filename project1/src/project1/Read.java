package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.*; // for locate information of nodes and edges in graphml
public class Read { 
	public static Graph toGraph(String path) throws Exception {

		Graph graph = new Graph();
		// Path to graphml file
		File file = new File(path); // look in project directory
		// Creating buffer to read file
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		
		long start = System.currentTimeMillis();
		// skip unnecessary lines of information
		while (!br.readLine().contains("<graph id=")) {}
		
		// start reading nodes and edges
		// using regular expression based on graphml nodes and edges format
		//Pattern regexGraph = Pattern.compile("(<graph id=\")(\\p{Alnum}+)(\" edgedefault =\")(\\\\p{Alnum}+)(\">)");
		Pattern regexNode = Pattern.compile("(<node id=\")(\\p{Alnum}+)(\">)");
		Pattern regexEdgeSrcDst = Pattern.compile("(<edge source=\")(\\p{Alnum}+)(\" target=\")(\\p{Alnum}+)(\")");
		Pattern regexEdgeID = Pattern.compile("(<data key=\"e_id\">)(\\p{Alnum}+)(</data>)");
		Pattern regexEdgeWeight = Pattern.compile("(<data key=\"e_weight\">)(\\p{Alnum}+)(</data>)");
		
		// store current reading line to analyze information 
		String line;
		while ((line = br.readLine()) != null) {
			
			// for nodes
			Matcher matcher = regexNode.matcher(line);
			if (matcher.find()) {
				//System.out.println(matcher.group(2));
				Node newNode = new Node(matcher.group(2));
				graph.addNode(newNode);
				continue;
			}
			// for edge - source and dest
			Edge newEdge = new Edge();
			Matcher matcherEdgeSrcDst = regexEdgeSrcDst.matcher(line);
			if (matcherEdgeSrcDst.find()) {
				//System.out.print("src=" + matcherEdgeSrcDst.group(2) + "\t");
				//System.out.println("dst=" + matcherEdgeSrcDst.group(4));
				newEdge.src += matcherEdgeSrcDst.group(2);
				newEdge.dst += matcherEdgeSrcDst.group(4);
				line = br.readLine();
				// for edge's id
				Matcher matcherEdgeID = regexEdgeID.matcher(line);
				if (matcherEdgeID.find()) {
					//System.out.print("ID=" + matcherEdgeID.group(2) + "\t");
					newEdge.id = matcherEdgeID.group(2);
				}
				line = br.readLine();
				// for edge weight
				Matcher matcherEdgeWeight = regexEdgeWeight.matcher(line);
				if (matcherEdgeWeight.find()) {
					//System.out.println("w=" + matcherEdgeWeight.group(2));
					newEdge.weight = Integer.parseInt(matcherEdgeWeight.group(2));
					graph.addEdge(newEdge);
				}
				
				// add neighbor for src, des
				graph.nodeList.get(newEdge.getSrc()).addNeighbor(graph.nodeList.get(newEdge.getDst()), newEdge);
				graph.nodeList.get(newEdge.getDst()).addNeighbor(graph.nodeList.get(newEdge.getSrc()), newEdge);
				 
				
				line = br.readLine();
			}
		}
		System.out.println(System.currentTimeMillis() - start);
		
		return graph;
	} 
}