package com.github.yadsendew;

public class NotFoundNodeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    NotFoundNodeException() {}

    NotFoundNodeException(String msg) {
        super(msg);
    }
 
    NotFoundNodeException(UndirectedWeightedGraph graph, String nodeId) {
        System.out.println("\nNode '" + nodeId + "' does not exist in the graph.");
        System.exit(0);
    } 

}