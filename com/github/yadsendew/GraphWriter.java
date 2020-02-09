package com.github.yadsendew;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The Class GraphWriter writes all graph properties.
 */
public class GraphWriter {
            
      private final static Logger LOGGER = Logger.getLogger("MyLogger");
      
      /**
       * Export to text file.
       *
       * @param graph   the graph to be written
       * @param pathIn  the input file name
       * @param pathOut the output file name
       * @throws NotFoundNodeException if the node is not found
       */
      public static void exportToText(UndirectedWeightedGraph graph, String pathIn, String pathOut)
                  throws NotFoundNodeException {
            try {
                  FileWriter myWriter = new FileWriter(pathOut);
                  myWriter.write("Read in file: \'" + pathIn + "\'\n");
                  LOGGER.info("Writing to file:" + pathOut);
                  myWriter.write("### Graph information ### \n");
                  LOGGER.info("Writing graph infomation to the file");
                  // get number of node
                  myWriter.write("\t" + "Number of nodes: " + graph.getTotalNodes()+ '\n');
                  // get number of edge
                  myWriter.write("\t" + "Number of edges: " + graph.getTotalEdges()+ '\n');
                  // print all vertices's ID
                  myWriter.write("\t" + "Vertex IDs: " + graph.getNodeId()+ '\n');
                  // 3. print all edges's ID
                  myWriter.write("\t" + "Edge IDs: " + graph.getEdgeId()+ '\n');
                  // check connectivity
                  myWriter.write("\t" + "Graph " + ( Connectivity.isConnected(graph) == true ? "is" : "is not") + " connected\n" );
                  // get diameter
                  myWriter.write("\t" + "Graph diameter: " + Diameter.calculate(graph) + '\n');
                  
                  myWriter.write("### Shortest paths ###\n");		
                  LOGGER.info("Writing all shortest paths of the graph");
                  ArrayList<String> allNodeIdList = graph.getNodeId();
                  int totalNodes = allNodeIdList.size();
                  for (int i = 0; i < totalNodes; i++){ // from start node
                        String startId = allNodeIdList.get(i);
                        myWriter.write("\tSource node \'"+ startId +"\':\n");
                        for (int j = i; j < totalNodes; j++){ // to end node
                              String endId = allNodeIdList.get(j);
                              // get a list of shortest paths from start node to end node
                              ShortestPath pathList = graph.getShortestPathMatrix().getShortestPath(startId, endId);
                              // length of paths
                              double len = pathList.getLength();
                              // get one shortest path from the list
                              ArrayList<String> path = pathList.getPathList().get(0);
                              // append element
                              myWriter.write("\t\t" + "To node \'" + endId + "\': ");
                              myWriter.write("path -> " + path);
                              myWriter.write("; length -> " + len + "\n");
                        }
                  }
                  myWriter.write("### Betweenness centrality ###\n");
                  LOGGER.info("Writing betweenness centrality...");
                  for (String nodeId : allNodeIdList){ // from for each node
                        myWriter.write("\tNode \'"+ nodeId +"\': ");
                        BetweennessCentrality bCentrality = new BetweennessCentrality(graph, nodeId);
                        myWriter.write(bCentrality.getBCM() + "\n");
                  }
                  myWriter.close();
                  
            } catch (IOException e) {
                  LOGGER.info("An error occurred.");
                  e.printStackTrace();
            }   
      }
      
      /**
       * Export to XML file.
       *
       * @param graph the graph to be written
       * @param pathIn the input file name
       * @param pathOut the output file name
       */
      public static void exportToXML(UndirectedWeightedGraph graph, String pathIn, String pathOut) {

            DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder icBuilder;
            try {
                  icBuilder = icFactory.newDocumentBuilder();
                  Document doc = icBuilder.newDocument();
                  LOGGER.info("Writing to XML file");
                  Element graphMLElement = doc.createElementNS("http://graphml.graphdrawing.org/xmlns", "graphml");
                  doc.appendChild(graphMLElement);
                  Element key = doc.createElement("key");
                  key.setAttribute("id", "v_id");
                  key.setAttribute("for", "node");
                  key.setAttribute("attr.name", "id");
                  key.setAttribute("attr.type", "double");
                  graphMLElement.appendChild(key);
                  Element key2 = doc.createElement("key");
                  key2.setAttribute("id", "e_id");
                  key2.setAttribute("for", "edge");
                  key2.setAttribute("attr.name", "id");
                  key2.setAttribute("attr.type", "double");
                  graphMLElement.appendChild(key2);
                  Element key3 = doc.createElement("key");
                  key3.setAttribute("id", "e_id");
                  key3.setAttribute("for", "edge");
                  key3.setAttribute("attr.name", "weight");
                  key3.setAttribute("attr.type", "double");
                  graphMLElement.appendChild(key3);
                  Element graphElement = doc.createElement("graph");
                  graphMLElement.appendChild(graphElement);

                  // append TotalNode element to root element
                  graphElement.appendChild(getElementTotalNode(doc, graph));

                  // append TotalEdge element to root element
                  graphElement.appendChild(getElementTotalEdge(doc, graph));

                  // append Node elements to root element
                  ArrayList<String> allNodeIdList = graph.getNodeId();
                  for (String nodeId : allNodeIdList){ // from start node
                        addElementNode(doc, graphElement, nodeId);
                  }
                  
                  // append Edge elements to root element
                  ArrayList<String> allEdgeIdList = graph.getEdgeId();
                  for (String edgeId : allEdgeIdList){ // from start node
                        Edge edge = graph.getEdge(edgeId);
                        addElementEdge(doc, graphElement, edge);
                  }
                  // append connectivity to root element
                  graphElement.appendChild(getElementConnectivity(doc, graph));
                  // append diameter to root element
                  graphElement.appendChild(getElementDiameter(doc, graph));
                  // append all shortest path
                  graphElement.appendChild(getElementShortestPath(doc, graph));
                  // append betweenness centrality measurement
                  graphElement.appendChild(getElementBCM(doc, graph));

                  // output DOM XML to file, specified in `path` variable
                  Transformer transformer = TransformerFactory.newInstance().newTransformer();
                  transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
                  DOMSource source = new DOMSource(doc);
                  StreamResult streamResult = new StreamResult(new File(pathOut));
                  transformer.transform(source, streamResult);

                  // System.out.println("\nExported in XML format with file name: " + pathOut);

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
      
      /**
       * Adds the element node.
       *
       * @param doc the document
       * @param graphElement the graph element
       * @param nodeId the node id
       */
      private static void addElementNode(Document doc, Element graphElement, String nodeId) {
            Element nodeElement = doc.createElement("node");
            nodeElement.setAttribute("id", 'n' + nodeId);
            Element data = doc.createElement("data");
            data.setAttribute("key", "v_id");
            data.appendChild(doc.createTextNode(nodeId));
            nodeElement.appendChild(data);
            graphElement.appendChild(nodeElement);
      }
      
      /**
       * Adds the element edge.
       *
       * @param doc the document
       * @param graphElement the graph element
       * @param edge the edge
       */
      private static void addElementEdge(Document doc, Element graphElement, Edge edge) {
            Element edgeElement = doc.createElement("edge");
            edgeElement.setAttribute("source", 'n' + edge.getSrc());
            edgeElement.setAttribute("target", 'n' + edge.getDst());
            Element data1 = doc.createElement("data");
            data1.setAttribute("key", "e_id");
            data1.appendChild(doc.createTextNode(edge.getId()));
            edgeElement.appendChild(data1);
            Element data2 = doc.createElement("data");
            data2.setAttribute("key", "e_weight");
            data2.appendChild(doc.createTextNode(Double.toString(edge.getWeight())));
            edgeElement.appendChild(data2);
            graphElement.appendChild(edgeElement);
      }
      
      /**
       * Gets the element shortest path.
       *
       * @param doc   the document
       * @param graph the graph
       * @return the element shortest path
       * @throws NotFoundNodeException if the node is not found
       */
      private static Node getElementShortestPath(Document doc, UndirectedWeightedGraph graph)
                  throws NotFoundNodeException {
            Element shortestPathElement = doc.createElement("shortestPath");
            ArrayList<String> allNodeIdList = graph.getNodeId();
            int totalNodes = allNodeIdList.size();
            for (int i = 0; i < totalNodes; i++){ // from start node
                  String startId = allNodeIdList.get(i);
                  for (int j = i; j < totalNodes; j++){ // to end node
                        String endId = allNodeIdList.get(j);
                        // get a list of shortest paths
                        //System.out.println(startId + " " +endId);
                        ShortestPath pathList = graph.getShortestPathMatrix().getShortestPath(startId, endId);
                        //System.out.println(pathList);
                        double len = pathList.getLength();
                        // get one shortest path from the list
                        ArrayList<String> path = pathList.getPathList().get(0);
                        // append element
                        shortestPathElement.appendChild(
                              getPathElement(doc, shortestPathElement, startId, endId, len, path));
                  }
            }
            return shortestPathElement;
      }
      
      /**
       * Gets the graph path element.
       *
       * @param doc the document
       * @param element the element
       * @param src the source
       * @param dst the destination
       * @param len the length
       * @param path the path
       * @return the path element
       */
      private static Node getPathElement(Document doc, Element element, String src, String dst, double len, ArrayList<String> path) {
            Element pathElement = doc.createElement("path");
            pathElement.setAttribute("source", src);
            pathElement.setAttribute("target", dst);
            pathElement.setAttribute("weight", Double.toString(len));

            String pathString = String.join(", ", path);
            pathElement.appendChild(doc.createTextNode("[" + pathString + "]"));
            return pathElement;
      }
      
      /**
       * Gets the element total node.
       *
       * @param doc the document
       * @param graph the graph
       * @return the element total node
       */
      private static Node getElementTotalNode(Document doc, UndirectedWeightedGraph graph){
            Element totalNode = doc.createElement("totalNode");
            totalNode.setAttribute("value", Integer.toString(graph.getTotalNodes()));
            return totalNode;
      }
      
      /**
       * Gets the element total edge.
       *
       * @param doc the document
       * @param graph the graph
       * @return the element total edge
       */
      private static Node getElementTotalEdge(Document doc, UndirectedWeightedGraph graph){
            Element totalEdge = doc.createElement("totalEdge");
            totalEdge.setAttribute("value", Integer.toString(graph.getTotalEdges()));
            return totalEdge;
      }

      /**
       * Gets the element connectivity.
       *
       * @param doc the document
       * @param graph the graph
       * @return the element connectivity
       */
      private static Node getElementConnectivity(Document doc, UndirectedWeightedGraph graph){
            Element connectivityElement = doc.createElement("connectivity");
            boolean connectivityValue = Connectivity.isConnected(graph);
            connectivityElement.setAttribute("value", Boolean.toString(connectivityValue));
            return connectivityElement;
      }

      /**
       * Gets the element diameter.
       *
       * @param doc the document
       * @param graph the graph
       * @return the element diameter
       */
      private static Node getElementDiameter(Document doc, UndirectedWeightedGraph graph){
            Element diameterElement = doc.createElement("diameter");
            double diameterValue = Diameter.calculate(graph);
            diameterElement.setAttribute("value", Double.toString(diameterValue));
            return diameterElement;
      }

      /**
       * Gets the element Betweenness Centrality Measure.
       *
       * @param doc   the document
       * @param graph the graph
       * @return the element Betweenness Centrality Measure
       * @throws NotFoundNodeException if the node is not found
       */
      private static Node getElementBCM(Document doc, UndirectedWeightedGraph graph) throws NotFoundNodeException {
            Element bCentralityElement = doc.createElement("betweennessCentrality");
            ArrayList<String> allNodeIdList = graph.getNodeId();
            for (String nodeId : allNodeIdList){ // from for each node
                  Element bCentralChild = doc.createElement("node");
                  bCentralChild.setAttribute("id", nodeId);
                  BetweennessCentrality bCentrality = new BetweennessCentrality(graph, nodeId);
                  bCentralChild.appendChild(doc.createTextNode(Double.toString(bCentrality.getBCM())));
                  bCentralityElement.appendChild(bCentralChild);
            }
            return bCentralityElement;
      }
}
