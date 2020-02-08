package com.github.yadsendew;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ShortestPathMatrix {
	private HashMap< String, HashMap< String, ShortestPath>> shortestPathMatrix;

	ShortestPathMatrix() {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath>>();
	}

    ShortestPathMatrix(UndirectedWeightedGraph graph) {
		shortestPathMatrix = new HashMap< String, HashMap< String, ShortestPath> >();
		
		ArrayList<String> nodeArrayList = new ArrayList<String>( graph.getNodeList().keySet() );
		
		// initualize the shortestPathMatrix
		for (int i = 0; i < nodeArrayList.size(); i++) {
			for (int j = 0; j < nodeArrayList.size(); j++) {
				String startId = nodeArrayList.get(i);
				shortestPathMatrix.put(startId, new HashMap< String, ShortestPath>());
				String endId = nodeArrayList.get(j);
				shortestPathMatrix.put(endId, new HashMap< String, ShortestPath>());
			}
		}
		
		for (int i = 0; i < nodeArrayList.size(); i++) {
			//System.out.println(i + " run");
			String startId = nodeArrayList.get(i);
			
			
			/* Thread start here
			// divide the task into 3 threads
			int numOfThread = 5;
			ArrayList<UndirectedUnweightedSPThread> threadList = new ArrayList<UndirectedUnweightedSPThread>();
			

			// if the number of candidate is less than numOfThread, there is no need to divide the candidate into the thread, each thread get only 1 node
			if ( (nodeArrayList.size() - i) < numOfThread ) {
				threadList = new ArrayList<UndirectedUnweightedSPThread>();
				for(int j = i; j < nodeArrayList.size(); j++) {
					//System.out.println(i + " " + j);
					threadList.add( new UndirectedUnweightedSPThread(i, j, j, graph, shortestPathMatrix, nodeArrayList) );
					threadList.get(j - i).setName(j);
					threadList.get(j - i).start();
				}

				
				// wait until these thread finish
				for (UndirectedUnweightedSPThread thread : threadList) {
					while( thread.isAlive() ) {}	// loop until the thread is not alive
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				

				double dif = (double) (nodeArrayList.size() - i ) / numOfThread;
				
				ArrayList<Integer> endIndexList = new ArrayList<Integer>();
				endIndexList.add(i);
				for (int j = 1; j <= numOfThread; j++) {
					endIndexList.add( (int) Math.ceil(i + j * dif) );
				}

				threadList = new ArrayList<UndirectedUnweightedSPThread>();
				for (int j = 0; j < numOfThread; j++) {
					int start = endIndexList.get(j);
					int end = endIndexList.get(j + 1) - 1;

					//System.out.println(i + " " + endIndexList);

					
					//System.out.println(start + " " + end + " " + (start == end));
					threadList.add( new UndirectedUnweightedSPThread(i, start, end, graph, shortestPathMatrix, nodeArrayList) );
					//threadList.get(j).setName(j);
					threadList.get(j).start();
					
					
				}

				for (UndirectedUnweightedSPThread thread : threadList) {
					while( thread.isAlive() ) {}	// loop until the thread is not alive

				}
				



				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			Thread end here*/

			// Normal start
			for (int j = i; j < nodeArrayList.size(); j++) {
                
				String endId = nodeArrayList.get(j);
				//shortestPathMatrix.put(endId, new HashMap< String, ShortestPath>());
				
				// list of shortest path from 1st node to 2nd node
				ShortestPath shortestPath = new ShortestPath(graph, startId, endId);
				
				shortestPathMatrix.get(startId).put(endId, shortestPath);
				
				// list of shortest path from 2nd node to 1st node
				if (i != j) {
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
							
			} 	// END LOOP
			
		}	// END LOOP
	}
	
    public HashMap< String, HashMap< String, ShortestPath>> getMatrix(){
        return shortestPathMatrix;
    }
	public ShortestPath getShortestPath(String startId, String endId) {
		return shortestPathMatrix.get(startId).get(endId);
	} 
}

