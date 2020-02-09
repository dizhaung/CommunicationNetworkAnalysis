package com.github.yadsendew;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * The Class GraphParser.
 */
public class GraphParser extends DefaultHandler {
   private final Logger LOGGER = Logger.getLogger("MyLogger");
   /** Return true if current reading element is a node element. */
   private boolean bNodeKey = false;
   
   /** Return true if current reading element is a edge ID element. */
   private boolean bEdgeId = false;
   
   /** Return true if current reading element is a edge weight element. */
   private boolean bEdgeWeight = false;
   
   /** Create new undirected weighted graph. */
   static UndirectedWeightedGraph undirectedWeightedGraph = new UndirectedWeightedGraph();
   
   /** Store new node ID. */
   static String newNodeId;
   
   /** Store new edge ID. */
   static String newEdgeId;
   
   /** Store new edge source. */
   static String newEdgeSrc;
   
   /** Store new edge destination. */
   static String newEdgeDst;
   
   /** Store new edge weight. */
   static double newEdgeWeight;

   /**
    * Parses the input file to graph
    *
    * @param path input file name
    * @return the undirected weighted graph
    */

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
   
   /**
    * Start element.
    *
    * @param uri the uri
    * @param localName the local name
    * @param qName the q name
    * @param attributes the attributes
    * @throws SAXException the SAX exception
    */
   @Override
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes) throws SAXException {

      if (qName.equalsIgnoreCase("node")) {
      } else if (qName.equalsIgnoreCase("edge")) {
         newEdgeSrc = attributes.getValue("source").substring(1);
         newEdgeDst = attributes.getValue("target").substring(1);
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

   /**
    * End element.
    *
    * @param uri the uri
    * @param localName the local name
    * @param qName the q name
    * @throws SAXException the SAX exception
    */
   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("node")) {
         Node newNode = new Node(newNodeId);
         LOGGER.info("Added node:" + newNodeId);
         undirectedWeightedGraph.addNode(newNode);
      }
      if (qName.equalsIgnoreCase("edge")) {
         // add edge
         Edge newEdge = new Edge(newEdgeId, newEdgeSrc, newEdgeDst, newEdgeWeight);
         undirectedWeightedGraph.addEdge(newEdge);
         LOGGER.info("Added edge:" + newEdgeId);
      }
   }

   /**
    * Characters.
    *
    * @param ch the character
    * @param start the start
    * @param length the length
    * @throws SAXException the SAX exception
    */
   @Override
   public void characters(char ch[], int start, int length) throws SAXException {
      if (bNodeKey){
         newNodeId = new String(ch, start, length);
         bNodeKey = false;
      }
      if (bEdgeId){
         newEdgeId = new String(ch, start, length);
         bEdgeId = false;
      }
      if (bEdgeWeight){
         newEdgeWeight = Double.parseDouble(new String(ch, start, length));
         bEdgeWeight = false;
      }
   }

}