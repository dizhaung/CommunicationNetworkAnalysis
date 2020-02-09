package com.github.yadsendew;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.*;


/**
 * The Class Arguments represents the input arguments.
 */
public class Arguments {
	private final Logger LOGGER = Logger.getLogger("MyLogger");

	/** Store analysed task. */
	private ArrayList<ArrayList<String>> taskAnalysed = new ArrayList<ArrayList<String>>();
	
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
			LOGGER.warning("There were no commandline arguments passed!\nProgram has terminated\n\n");
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
			LOGGER.warning("The input file does not exist. \nPlease make sure it is located at: \"" +
							System.getProperty("user.dir") + "/resources/\" folder.");
			System.out.print("The input file does not exist. \nPlease make sure it is located at: \"");
			System.out.println(System.getProperty("user.dir") + "/resources/\" folder.");

			LOGGER.info("Program has terminated.\n\n");
			System.exit(0);
		} else {
			LOGGER.info("Input file check existence: OK");
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
		//LOGGER.
		// -s needs 2 nodes
		if (index == taskArray.length - 1 || taskArray[index + 1].charAt(0) == '-') {
			// if -s is the last argument or the next is a task
			System.out.println("The task " + taskArray[index] + " has no argument. 2 are needed.");
			isInputValid = false;
			return false;
		} else if (index == taskArray.length - 2 || taskArray[index + 2].charAt(0) == '-') {
			// if only 1 argument left or the the second arg from -s is a task
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
		if (index == taskArray.length - 1 || taskArray[index + 1].charAt(0) == '-') {
			// if -b is the last argument or the next is a task
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
			isInputValid = false;
			return false;
		} else if (index != taskArray.length - 2 && taskArray[index + 2].charAt(0) != '-') {
			// if the seconf argument from -b is a task
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
		if (index == taskArray.length - 1 || taskArray[index + 1].charAt(0) == '-') {
			// if '-a' is the last argument or the next argument is a task
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed.");
			LOGGER.warning("The task " + taskArray[index] + " has no argument. 1 are needed." + 
						   "\nProgram has terminated.\n\n");
			isInputValid = false;
			return false;
		} else {

			// run loop while the taskArray is not end and the argument does not begin with
			// '-'
			while (true) { // while
				index += 1;

				ArrayList<String> task = new ArrayList<String>();
				task.add("-a"); // add -a
				task.add(taskArray[index]); // add node

				if (!taskAnalysed.contains(task)) {
					taskAnalysed.add(task);
				}

				if (index == taskArray.length - 1)
					break;

				if (taskArray[index + 1].charAt(0) == '-')
					break;
			}

		}

		return true;
	}

	/**
	 * Analyse the input argument.
	 *
	 * @param taskArray stores input arguments
	 */
	public void analyse(String[] taskArray) {
		LOGGER.info("Initialized \"Arguments\" class");
		// check if the argument array is empty
		LOGGER.info("Checking empty argument...");
		checkEmptyArg(taskArray);
		LOGGER.info("No empty argument passed.");
		// check if the file argument exist before doing stuff
		checkFileExistance(taskArray[0]);

		// parse next arguments
		LOGGER.info("Parsing next arguments...");
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
				if (analyseA(taskArray, index) == true) {}
			} else if (taskArray[index].charAt(0) == '-' && !taskArray[index].equals("-s") && !taskArray[index].equals("-a")
			&& !taskArray[index].equals("-b")) { // unknown task
				// begin with '-' but is not '-s', '-a', '-b'
				System.out.println("Unknown task " + taskArray[index] + ". Try -s, -a or -b.");
				isInputValid = false;
			}
		} // end for loop

		if (isInputValid == false){
			LOGGER.warning("Input arguments is invalid.\nProgram has terminated.\n\n");
			System.exit(0);
		} 
			
	}
}
