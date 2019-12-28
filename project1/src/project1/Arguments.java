package project1;
import java.io.File;

public class Arguments {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("There were no commandline arguments passed!");
        } else {
            File f = new File(args[0]);
            if (f.exists()) {
                if (args.length==1) {
                	System.out.println("Graph properties: ");
                }
                else {
                	for (int i = 1; i < args.length; i++) {
                	
                		if (args[i].equals("-s") ) {
                			System.out.println("Graph properties:  ");
                			System.out.println("s");
                			break;
                		}
                		if (args[i].equals("-a")) {
                			System.out.println("Graph properties:  ");
                			System.out.println("a");
                			break;
                		}
                		if (args[i].equals("-b")) {
                			System.out.println("Graph properties:  ");
                			System.out.println("b");
                			break;
                		}
                		
                		else {
                			System.out.println("You have enter wrong command! Please check again."); 
                			break;
                		}
                	}
                }
            	}
                
            else {
                System.out.println("The file does not exist");
            }
        }
    }
}



