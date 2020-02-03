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
      public static void exportToText(Graph<UndirectedWeightedGraph> graph, String path){
            System.out.println("\nExported with file name: " + path);
      }
      public static void exportToXML(Graph<UndirectedWeightedGraph> graph, String path) {
            
            DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder icBuilder;
            try {
                  icBuilder = icFactory.newDocumentBuilder();
                  Document doc = icBuilder.newDocument();
                  Element mainRootElement = doc.createElementNS("http://graphml.graphdrawing.org/xmlns", "graphml");
                  doc.appendChild(mainRootElement);

                  // append TotalNode element to root element
                  mainRootElement.appendChild(getElementTotalNode(doc, graph));

                  // append Node elements to root element
                  
                  // append TotalEdge element to root element
                  mainRootElement.appendChild(getElementTotalEdge(doc, graph));

                  // append Edge elements to root element

                  // append connectivity to root element

                  // append diameter to root element
                  
                  // append all shortest path
                  mainRootElement.appendChild(getElementShortestPath(doc, graph));
                  // append betweenness centrality measurement


                  mainRootElement.appendChild(getCompany(doc, "2", "eBay", "Shopping", "2000"));
                  mainRootElement.appendChild(getCompany(doc, "3", "Google", "Search", "3000"));

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

      private static Node getCompany(Document doc, String id, String name, String age, String role) {
            Element company = doc.createElement("Company");
            company.setAttribute("id", id);
            company.appendChild(getCompanyElements(doc, company, "Name", name));
            company.appendChild(getCompanyElements(doc, company, "Type", age));
            company.appendChild(getCompanyElements(doc, company, "Employees", role));
            return company;
      }

      // utility method to create text node
      private static Node getCompanyElements(Document doc, Element element, String tagName, String tagValue) {
            Element node = doc.createElement(tagName);
            node.appendChild(doc.createTextNode(tagValue));
            return node;
      }
      private static Node getElementShortestPath(Document doc, Graph<UndirectedWeightedGraph> graph) {
            Element shortestPathElement = doc.createElement("ShortestPath");
            ShortestPathMatrix matrix = new ShortestPathMatrix(graph);
            ArrayList<String> allNodeIdList = graph.getNodeId();
            for (String startId : allNodeIdList){ // from start node
                  for (String endId : allNodeIdList){ // to end node
                        // get a list of shortest paths
                        ShortestPath pathList = matrix.getShortestPath(startId, endId);
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
            Element pathElement = doc.createElement("Path");
            pathElement.setAttribute("src", src);
            pathElement.setAttribute("dst", dst);
            pathElement.setAttribute("len", Double.toString(len));

            String pathString = String.join(", ", path);
            pathElement.appendChild(doc.createTextNode("[" + pathString + "]"));
            return pathElement;
      }
      private static Node getElementTotalNode(Document doc, Graph<UndirectedWeightedGraph> graph){
            Element totalNode = doc.createElement("TotalNode");
            totalNode.setAttribute("value", Integer.toString(graph.getTotalNodes()));
            return totalNode;
      }
      private static Node getElementTotalEdge(Document doc, Graph<UndirectedWeightedGraph> graph){
            Element totalEdge = doc.createElement("TotalEdge");
            totalEdge.setAttribute("value", Integer.toString(graph.getTotalEdges()));
            return totalEdge;
      }
      /*
      private static Node getElementConnectivity(Document doc, Graph<UndirectedWeightedGraph> graph){
            Element connectivityElement = doc.createElement("Connectivity");
            boolean connectivityValue = Connectivity.isConnected(graph);
            connectivityElement.setAttribute("value", Boolean.toString(connectivityValue));
            return connectivityElement;
      }
      */
      /*
      private static Node getElementDiameter(Document doc, Graph<UndirectedWeightedGraph> graph){
            Element diameterElement = doc.createElement("Diameter");
            double diameterValue = Diameter.calculate(graph);
            diameterElement.setAttribute("value", Double.toString(diameterValue));
            return diameterElement;
      }
      */

}
