package com.github.yadsendew;

import java.io.File;
import java.util.ArrayList;


/**
 * The Class Arguments.
 */
public class Arguments {
	
	/** Store analysed task. */
	private ArrayList<ArrayList<String>> taskAnalysed = new ArrayList<ArrayList<String>>();
	
	/** Store the output file. */
	private String outputFile;
	
	/** Return true if the input is valid */
	private boolean isInputValid = true;
	
	/** Store the file name. */
	private String fileName;

	/**
	 * Gets the analysed task.
	 *
	 * @return the analysed task
	 */
	// get, set
	public ArrayList<ArrayList<String>> getTaskAnalysed() {
		return taskAnalysed;
	}

	/**
	 * Gets the output file.
	 *
	 * @return the output file
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Check if there is no argument
	 *
	 * @param taskArray stores input arguments
	 */
	private void checkEmptyArg(String[] taskArray) {
		// if there is no argument, stop the program
		if (taskArray.length == 0) {
			System.out.println("There were no commandline arguments passed!");
			System.exit(0);
		}
	}

	/**
	 * Check if the file exists.
	 *
	 * @param fileName the file name
	 */
	private void checkFileExistance(String fileName) {
		this.fileName = fileName;
		String path = "resources/" + fileName;
		File f = new File(path);

		// check if the file argument exist, exit if it is not found
		if (!f.exists()) {
			System.out.print("The input file does not exist. \nPlease make sure it is located at: \"");
			System.out.println(System.getProperty("user.dir") + "/resources/\" folder.");
			System.exit(0);
		}
	}

	/**
	 * Analyse the argument -s, to check the format of shortest path between 2 vertices calculation.
	 *
	 * @param taskArray stores input arguments
	 * @param index points out where the list of output file begin.
	 * @return true, if successful
	 */
	private boolean analyseS(String[] taskArray, int index) {
		// analyse -s, find the shortest path between 2 vertices
		// -s needs two nodes
		if (index == taskArray.length - 1 || taskArray[index + 1].equals("-s") || taskArray[index + 1].equals("-a")
				|| taskArray[index + 1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + "has no argument. 2 are needed.");
			isInputValid = false;
			return false;
		} else if (index == taskArray.length - 2 || taskArray[index + 2].equals("-s")
				|| taskArray[index + 2].equals("-a") || taskArray[index + 2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has only 1 argument. 2 are needed.");
			isInputValid = false;
			return false;
		} else if (index != taskArray.length - 3 && !taskArray[index + 3].equals("-s")
				&& !taskArray[index + 3].equals("-a") && !taskArray[index + 3].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has more than 2 arguments.");
			isInputValid = false;
			return false;
		} else {
			ArrayList<String> task = new ArrayList<String>();
			task.add(taskArray[index]); // add -s
			task.add(taskArray[index + 1]); // add node1
			task.add(taskArray[index + 2]); // add node2

			taskAnalysed.add(task);
		}

		return true;
	}

	/**
	 * Analyse -b, to check the format of betweenness centrality measurement.
	 *
	 * @param taskArray stores input arguments
	 * @param index points out where the list of output file begin.
	 * @return true, if successful
	 */
	private boolean analyseB(String[] taskArray, int index) {
		// analyse -b, find the betweenness centrality of a vertice
		// -b needs 1 node
		if (index == taskArray.length - 1 || taskArray[index + 1].equals("-s") || taskArray[index + 1].equals("-a")
				|| taskArray[index + 1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
			isInputValid = false;
			return false;
		} else if (index != taskArray.length - 2 && !taskArray[index + 2].equals("-s")
				&& !taskArray[index + 2].equals("-a") && !taskArray[index + 2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has more than 1 argument.");
			isInputValid = false;
			return false;
		} else {
			ArrayList<String> task = new ArrayList<String>();
			task.add(taskArray[index]); // add -b
			task.add(taskArray[index + 1]); // add node

			taskAnalysed.add(task);
		}

		return true;
	}

	/**
	 * Analyse -a, to check the format of outputing in a particular file.
	 *
	 * @param taskArray stores input arguments
	 * @param index points out where the list of output file begin.
	 * @return true, if successful
	 */
	private boolean analyseA(String[] taskArray, int index) {
		// analyse -a, output in a particular file
		// -a needs 1 file
		if (index == taskArray.length - 1 || taskArray[index + 1].equals("-s") || taskArray[index + 1].equals("-a")
				|| taskArray[index + 1].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
			isInputValid = false;
			return false;
		} else if (index != taskArray.length - 2 && !taskArray[index + 2].equals("-s")
				&& !taskArray[index + 2].equals("-a") && !taskArray[index + 2].equals("-b")) {
			System.out.println("The task " + taskArray[index] + " has only 1 argument. 2 are needed");
			isInputValid = false;
			return false;
		} else {
			outputFile = taskArray[index + 1]; // add the required output file
		}

		return true;
	}

	/**
	 * Analyse the input argument.
	 *
	 * @param taskArray stores input arguments
	 */
	public void analyse(String[] taskArray) {
		// check if the argument array is empty
		checkEmptyArg(taskArray);

		// check if the file argument exist before doing stuff
		checkFileExistance(taskArray[0]);

		//ReadFileThread a = new ReadFileThread(fileName);
		Thread a = new Thread(new ReadFileThread(fileName));
		a.start();

		for (int index = 1; index < taskArray.length; index++) {
			
			if (taskArray[index].equals("-s")) { // task -s
				if (analyseS(taskArray, index) == true) {
					index += 2;
				}
			}
			else if (taskArray[index].equals("-b")) { // task -b
				if (analyseB(taskArray, index) == true) {
					index += 1;
				}
			}
			else if (taskArray[index].equals("-a")) { // task -a
				if (analyseA(taskArray, index) == true) {
					index += 1;
				}
			} else if (taskArray[index].charAt(0) == '-' && !taskArray[index].equals("-s") && !taskArray[index].equals("-a")
			&& !taskArray[index].equals("-b")) { // unknown task
				// begin with '-' but is not '-s', '-a', '-b'
				System.out.println("Unknown task " + taskArray[index] + ". Try -s, -a or -b.");
			}
		} // end for loop

		a.interrupt();
	}
}
