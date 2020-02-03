package com.github.yadsendew;
import java.io.File;
import java.util.ArrayList;

public class Arguments {
	private ArrayList< ArrayList<String> > taskAnalysed = new ArrayList< ArrayList<String> >();
	private ArrayList<String> outputFileList = new ArrayList<String>();
	private boolean isInputValid = true;
	private String fileName;
	
	// get, set
	public ArrayList< ArrayList<String> > getTaskAnalysed() {
		return taskAnalysed;
	}
	
	public ArrayList<String> getOutputFileList() {
		return outputFileList;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	private void checkEmptyArg(String[] taskArray) {
		// if there is no argument, stop the program
		if (taskArray.length == 0) {
			System.out.println("There were no commandline arguments passed!");
			System.exit(0);
		}
	}
	
	private void checkFileExistance(String fileName) { 
		this.fileName = fileName;
		
		System.out.println(System.getProperty("user.dir"));
		String path = "resources/" + fileName;
		File f = new File(path);
		
		// check if the file argument exist, exit if not found
		if (!f.exists()) {
			System.out.println("The file does not exist.");
			System.exit(0);
		}
	}
	
	private boolean analyseS(String[] taskArray, int index) {
		// analyse -s, find the shortest path between 2 vertices
		// -s needs two nodes
		if (index == taskArray.length - 1 || taskArray[index+1].equals("-s") || taskArray[index+1].equals("-a") || taskArray[index+1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + "has no argument. 2 are needed.");
			isInputValid = false;
			return false;
		}
		else if (index == taskArray.length - 2 || taskArray[index+2].equals("-s") || taskArray[index+2].equals("-a") || taskArray[index+2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has only 1 argument. 2 are needed.");
			isInputValid = false;
			return false;
		}
		else if (index != taskArray.length - 3 && !taskArray[index+3].equals("-s") && !taskArray[index+3].equals("-a") && !taskArray[index+3].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has more than 2 arguments.");
			isInputValid = false;
			return false;
		}
		else {
			ArrayList<String> task = new ArrayList<String>();
			task.add(taskArray[index]);	// add -s
			task.add(taskArray[index+1]);	// add node1
			task.add(taskArray[index+2]);	// add node2
			
			taskAnalysed.add( task );
		}
		
		return true;
	}
	
	private boolean analyseB(String[] taskArray, int index) {
		// analyse -b, find the betweenness centrality of a vertice
		// -b needs 1 node
		if (index == taskArray.length - 1 || taskArray[index+1].equals("-s") || taskArray[index+1].equals("-a") || taskArray[index+1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
			isInputValid = false;
			return false;
		}
		else if (index != taskArray.length - 2 && !taskArray[index+2].equals("-s") && !taskArray[index+2].equals("-a") && !taskArray[index+2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has more than 1 argument.");
			isInputValid = false;
			return false;
		}
		else {
			ArrayList<String> task = new ArrayList<String>();
			task.add(taskArray[index]);	// add -b
			task.add(taskArray[index+1]);	// add node
			
			taskAnalysed.add( task );
		}
		
		return true;
	}
	
	private boolean analyseA(String[] taskArray, int index) {
		// analyse -a, output in a particular file
		// -a needs 1 file
		if (index == taskArray.length - 1 || taskArray[index+1].equals("-s") || taskArray[index+1].equals("-a") || taskArray[index+1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
			isInputValid = false;
			return false;
		}
		else if (index != taskArray.length - 2 && !taskArray[index+2].equals("-s") && !taskArray[index+2].equals("-a") && !taskArray[index+2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has only 1 argument. 2 are needed");
			isInputValid = false;
			return false;
		}
		else {			
			outputFileList.add(taskArray[index+1]);	// add the required output file
		}
		
		return true;
	}
	
	public void analyse(String[] taskArray) {
		
		// check if the argument array is empty
		checkEmptyArg(taskArray);
		
		// check if the file argument exist before doing stuff
		checkFileExistance(taskArray[0]);
	
		for (int index = 0; index < taskArray.length; index++) {
			// irrelevant task
			if (taskArray[index].charAt(0) == '-' && !taskArray[index].equals("-s") && !taskArray[index].equals("-a") && !taskArray[index].equals("-b")) {		
				// begin with '-' but is not '-s', '-a', '-b'
				System.out.println("Unknown task " + taskArray[index] + ". Try -s, -a or -b.");
			}
			// relevant task
			else if (taskArray[index].equals("-s")) {	// task -s
				if (analyseS(taskArray, index) == true) {
					index += 2;
				}
			} // end else if
           	else if (taskArray[index].equals("-b")) {	// task -b
           		if (analyseB(taskArray, index) == true) {
          			index += 1;
           		}
           	} // end else if	
           	else if (taskArray[index].equals("-a")) {	// task -a
           		if (analyseA(taskArray, index) == true) {
           			index += 1;
          		}
        	} // end else if	
		
		}	// end for loop
	}
}
