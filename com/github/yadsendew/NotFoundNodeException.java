package com.github.yadsendew;

import java.util.logging.*;

/**
 * The Class NotFoundNodeException throw exception if the node is not found.
 */
public class NotFoundNodeException extends Exception {

    final Logger LOGGER = Logger.getLogger("PublicLogger"); 

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new exception if the node is not found.
     */
    NotFoundNodeException() {}

    /**
     * Instantiates a new exception if the node is not found.
     *
     * @param msg the msg
     */
    NotFoundNodeException(String msg) {
        super(msg);
    }
 
    /**
     * Instantiates a new exception if the node is not found.
     *
     * @param graph the graph
     * @param nodeId the ID of the node
     */
    NotFoundNodeException(UndirectedWeightedGraph graph, String nodeId) {
        System.out.println("\nNode '" + nodeId + "' does not exist in the graph");
        LOGGER.warning("Node '" + nodeId + "' does not exist in the graph\nProgram has terminated\n\n");
        System.exit(0);
    } 

}