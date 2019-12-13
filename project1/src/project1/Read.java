package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.*; // for locate information of nodes and edges in graphml
public class Read { 
  public static void main(String[] args)throws Exception { 
	  // Path to graphml file
	  File file = new File("/Users/steven/Yadsendew/Java OOP/Project/small_graph.graphml"); 
	  // Creating buffer to read file
	  BufferedReader br = new BufferedReader(new FileReader(file)); 
	  
	  // skip unnecessary lines of information
	  while (!br.readLine().contains("<graph id=")) {}
	  
	  // start reading nodes and edges
	  // using regular expression based on graphml nodes and edges format
	  Pattern regexNode = Pattern.compile("(<node id=\")(\\p{Alnum}+)(\">)");
	  Pattern regexEdgeSrcDst = Pattern.compile("(<edge source=\")(\\p{Alnum}+)(\" target=\")(\\p{Alnum}+)(\")");
	  Pattern regexEdgeID = Pattern.compile("(<data key=\"e_id\">)(\\p{Alnum}+)(</data>)");
	  Pattern regexEdgeWeight = Pattern.compile("(<data key=\"e_weight\">)(\\p{Alnum}+)(</data>)");
	  
	  // store current reading line to analyze information 
	  String line;
	  
	  while ((line = br.readLine()) != null) {
		  // for nodes
		  Matcher matcher = regexNode.matcher(line);
		  while (matcher.find()) {
			  System.out.println(matcher.group(2));
		  }
		  // for edge - source and dest
		  Matcher matcherEdgeSrcDst = regexEdgeSrcDst.matcher(line);
		  while (matcherEdgeSrcDst.find()) {
			  System.out.print("src=" + matcherEdgeSrcDst.group(2) + "\t");
			  System.out.println("dst=" + matcherEdgeSrcDst.group(4));
		  }
		  // for edge's id
		  Matcher matcherEdgeID = regexEdgeID.matcher(line);
		  while (matcherEdgeID.find()) {
			  System.out.print("ID=" + matcherEdgeID.group(2) + "\t");
		  }
		  // for edge weight
		  Matcher matcherEdgeWeight = regexEdgeWeight.matcher(line);
		  while (matcherEdgeWeight.find()) {
			  System.out.println("w=" + matcherEdgeWeight.group(2));
		  }
		  
	  } 
  } 
}