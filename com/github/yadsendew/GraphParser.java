package com.github.yadsendew;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GraphParser extends DefaultHandler {
   boolean bNodeKey = false;
   boolean bEdgeId = false;
   boolean bEdgeWeight = false;
   static UndirectedWeightedGraph undirectedWeightedGraph = new UndirectedWeightedGraph();
   static String newNodeId;
   static String newEdgeId;
   static String newEdgeSrc;
   static String newEdgeDst;
   static double newEdgeWeight;

   public static UndirectedWeightedGraph parse(String path) {
      try {
         File inputFile = new File(path);
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         GraphParser graphparser = new GraphParser();
         saxParser.parse(inputFile, graphparser);
      } catch (Exception e) {
         System.err.println(e.getMessage()); 
      }
      return undirectedWeightedGraph;
   }   
   @Override
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes) throws SAXException {

      if (qName.equalsIgnoreCase("node")) {
      } else if (qName.equalsIgnoreCase("edge")) {
         newEdgeSrc = attributes.getValue("source").substring(1);
         newEdgeDst = attributes.getValue("target").substring(1);
         // print 
         System.out.println("Source: " + newEdgeSrc);
         System.out.println("Source: " + newEdgeDst);
      }
      if (qName.equalsIgnoreCase("data")) {
         String keyValue = attributes.getValue("key");

         if (keyValue.equals("v_id")) {
            bNodeKey = true;
         } else if (keyValue.equals("e_id")) {
            bEdgeId = true;
         } else if (keyValue.equals("e_weight")) {
            bEdgeWeight = true;
         }

      }
      
   }

   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("node")) {
         Node newNode = new Node(newNodeId);
         undirectedWeightedGraph.addNode(newNode);
         System.out.println("-------------------------------------------------------------------");
      }
      if (qName.equalsIgnoreCase("edge")) {
         // add edge
         Edge newEdge = new Edge(newEdgeId, newEdgeSrc, newEdgeDst, newEdgeWeight);
         undirectedWeightedGraph.addEdge(newEdge);
         System.out.println("*******************************************************************");
      }
   }

   @Override
   public void characters(char ch[], int start, int length) throws SAXException {
      if (bNodeKey){
         System.out.println("v_id: " + new String(ch, start, length));
         newNodeId = new String(ch, start, length);
         bNodeKey = false;
      }
      if (bEdgeId){
         System.out.println("e_id: " + new String(ch, start, length));
         newEdgeId = new String(ch, start, length);
         bEdgeId = false;
      }
      if (bEdgeWeight){
         System.out.println("e_weight: " + new String(ch, start, length));
         newEdgeWeight = Double.parseDouble(new String(ch, start, length));
         bEdgeWeight = false;
      }
   }

}