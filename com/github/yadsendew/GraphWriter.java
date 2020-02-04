package com.github.yadsendew;
 
import java.io.File;
import java.util.ArrayList;

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

public class GraphWriter {
      public static void exportToText(UndirectedWeightedGraph graph, String path){
            System.out.println("\nExported with file name: " + path);
      }
      public static void exportToXML(UndirectedWeightedGraph graph, String path) {

            DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder icBuilder;
            try {
                  icBuilder = icFactory.newDocumentBuilder();
                  Document doc = icBuilder.newDocument();
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
                  StreamResult streamResult = new StreamResult(new File(path));
                  transformer.transform(source, streamResult);

                  System.out.println("\nExported in XML format with file name: " + path);

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
      private static void addElementNode(Document doc, Element graphElement, String nodeId) {
            Element nodeElement = doc.createElement("node");
            nodeElement.setAttribute("id", 'n' + nodeId);
            Element data = doc.createElement("data");
            data.setAttribute("key", "v_id");
            data.appendChild(doc.createTextNode(nodeId));
            nodeElement.appendChild(data);
            graphElement.appendChild(nodeElement);
      }
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
      private static Node getElementShortestPath(Document doc, UndirectedWeightedGraph graph) {
            Element shortestPathElement = doc.createElement("shortestPath");
            ArrayList<String> allNodeIdList = graph.getNodeId();
            for (String startId : allNodeIdList){ // from start node
                  for (String endId : allNodeIdList){ // to end node
                        // get a list of shortest paths
                        ShortestPath pathList = graph.getShortestPathMatrix().getShortestPath(startId, endId);
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
      private static Node getPathElement(Document doc, Element element, String src, String dst, double len, ArrayList<String> path) {
            Element pathElement = doc.createElement("path");
            pathElement.setAttribute("source", src);
            pathElement.setAttribute("target", dst);
            pathElement.setAttribute("weight", Double.toString(len));

            String pathString = String.join(", ", path);
            pathElement.appendChild(doc.createTextNode("[" + pathString + "]"));
            return pathElement;
      }
      private static Node getElementTotalNode(Document doc, UndirectedWeightedGraph graph){
            Element totalNode = doc.createElement("totalNode");
            totalNode.setAttribute("value", Integer.toString(graph.getTotalNodes()));
            return totalNode;
      }
      private static Node getElementTotalEdge(Document doc, UndirectedWeightedGraph graph){
            Element totalEdge = doc.createElement("totalEdge");
            totalEdge.setAttribute("value", Integer.toString(graph.getTotalEdges()));
            return totalEdge;
      }

      private static Node getElementConnectivity(Document doc, UndirectedWeightedGraph graph){
            Element connectivityElement = doc.createElement("connectivity");
            boolean connectivityValue = Connectivity.isConnected(graph);
            connectivityElement.setAttribute("value", Boolean.toString(connectivityValue));
            return connectivityElement;
      }

      private static Node getElementDiameter(Document doc, UndirectedWeightedGraph graph){
            Element diameterElement = doc.createElement("diameter");
            double diameterValue = Diameter.calculate(graph);
            diameterElement.setAttribute("value", Double.toString(diameterValue));
            return diameterElement;
      }

      private static Node getElementBCM(Document doc, UndirectedWeightedGraph graph){
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
