package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class UndirectedUnweightedSPThread extends Thread {

    private int name;
    private int index;
    private int start;
    private int end;
    private HashMap<String, HashMap<String, ShortestPath>> shortestPathMatrix;
    private ArrayList<String> nodeArrayList;
    private UndirectedWeightedGraph graph;

    @Override
    public void run() {

        // System.out.println(start + " " + end);
        //System.out.println("Thread " + name + " is running");
        
        for (int j = start; j <= end; j++) {

            String startId = nodeArrayList.get(index);
            String endId = nodeArrayList.get(j);
            
            // shortestPathMatrix.put(endId, new HashMap< String, ShortestPath>());

            // list of shortest path from 1st node to 2nd node
            ShortestPath shortestPath = new ShortestPath(graph, startId, endId);

            shortestPathMatrix.get(startId).put(endId, shortestPath);

            //System.out.println(startId + " " + endId + "shit" + (start == end));
            // list of shortest path from 2nd node to 1st node
            if (!startId.equals(endId)) {
                
                // System.out.println(endId + " " + startId);
                // make a copy of the shortestPath
                ShortestPath shortestPathReverse = new ShortestPath(shortestPath);

                // reverse its attributes
                shortestPathReverse.setSrc(shortestPath.getDst());
                shortestPathReverse.setDst(shortestPath.getSrc());
                for (ArrayList<String> path : shortestPathReverse.getPathList()) {
                    Collections.reverse(path);
                }

                shortestPathMatrix.get(endId).put(startId, shortestPathReverse);
                
            }

        } // END LOOP

        // try {
        //     Thread.sleep(1000);
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        
    }

    UndirectedUnweightedSPThread(int index, int start, int end, UndirectedWeightedGraph graph, HashMap<String, HashMap<String, ShortestPath>> shortestPathMatrix, ArrayList<String> nodeArrayList) {
        this.index = index;
        this.start = start;
        this.end = end;
        this.shortestPathMatrix = shortestPathMatrix;
        this.graph = graph;
        this.nodeArrayList = nodeArrayList;
    }

	public void setName(int t) {
        name = t;
	}

}