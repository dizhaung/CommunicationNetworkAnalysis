# Call Graph Writer
GraphWriter.export(graph, myArgs.outputFileList.get(0));  
`export(Graph, String fileName)`  
`outputFileList` is a list contain file names retrieved from arguments.  
`get(0)` – get element have at 0 index.
# Call Graph Parser
`UndirectedWeightedGraph graph = GraphParser.parse(path);`
`path: String, stores fileName`

# Call ShortestPath
`ShortestPath sPath = new ShortestPath(graph, "1", "2"`  
`UndirectedWeightedgraph graph`  
`"1": target nodeId`  
`"2": destination nodeId`  


# Call Betweenness Centrality
`BetweennessCentrality bCentrality = new BetweennessCentrality(graph, "2");`  
`UndirectedWeightedgraph graph`  
`"2": nodeId`  

