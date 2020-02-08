package com.github.yadsendew;

import java.io.File;
import java.util.ArrayList;

public class Arguments {
	private ArrayList<ArrayList<String>> taskAnalysed = new ArrayList<ArrayList<String>>();
	private boolean isInputValid = true;
	private String fileName;

	// get, set
	public ArrayList<ArrayList<String>> getTaskAnalysed() {
		return taskAnalysed;
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
		String path = "resources/" + fileName;
		File f = new File(path);

		// check if the file argument exist, exit if not found
		if (!f.exists()) {
			System.out.print("The input file does not exist. \nPlease make sure it is located at: \"");
			System.out.println(System.getProperty("user.dir") + "/resources/\" folder.");
			System.exit(0);
		}
	}

	private boolean analyseS(String[] taskArray, int index) {
		// analyse -s, find the shortest path between 2 vertices
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

	private boolean analyseA(String[] taskArray, int index) {
		// analyse -a, output in a particular file
		// -a needs 1 file
		if (index == taskArray.length - 1 || taskArray[index + 1].charAt(0) == '-') {
			// if '-a' is the last argument or the next argument is a task
			System.out.println("The task " + taskArray[index] + " has no argument. 1 are needed");
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

	public void analyse(String[] taskArray) {
		// check if the argument array is empty
		checkEmptyArg(taskArray);

		// check if the file argument exist before doing stuff
		checkFileExistance(taskArray[0]);



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
			}
		} // end for loop

		if (isInputValid == false) 
			System.exit(0);

		
	}
}
