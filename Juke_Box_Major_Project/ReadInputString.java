// Add a java class by importing its package to use it in your program
import java.util.Scanner;

/* 
 * This Java program, demonstrates reading inputs from standard input
 * program reads each line as a string and displays on standard output 
 * we will be using java.util.Scanner to read the input in this example, 
 * there are other ways too, which we can explore later
 * 
 * Your public class name and File name should be same for JAVA program to compile and run successfully
 */
public class ReadInputString {
    public static void main(String[] input) {
        // Instruct the user of the program how to use
        System.out.println("Enter two lines of string to read!");

        // Read from STDIN
		// PARSE it into a variable
		// Consider each line is a separate input

		// initialize scanner to read from standard input 
		Scanner scanner = new Scanner(System.in);

		// Read the entire line as String
		String strLine1 = scanner.nextLine();

        // Read one more line
		String strLine2 = scanner.nextLine();

		// When done reading expected number of inputs, close the scanner listening from input to avoid any memory leak or unexpected behavior of the program
		scanner.close();
		
        // Now display the inputs on standard output 
		System.out.println("Line 1 " + strLine1);
		System.out.println("Line 2 " + strLine2);
	}
}
