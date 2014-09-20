/* CE1: TextBuddy 
 * Name: Kang You Wei
 * Matriculation Number: A0108380L
 * 
 * This program serves to manipulate text in a .txt file. 
 * The .txt file is created if no file by the name is found.
 */

import java.util.Scanner;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextBuddy {
	public static void main(String[] args){
		String fileName = args[0];
		Scanner scanner = new Scanner(System.in);
		File file= openFile(fileName);
		
		messageToUser("Welcome to TextBuddy." +fileName +" is ready to be used");
		while(true){
			System.out.print("command: ");
			String command = scanner.next();
			String result = executeCommand(command,scanner,file);
			messageToUser(result);
		}
	}

	// The function below serves to choose the command depending on the keyword provided.
	private static String executeCommand(String command, Scanner scanner, File file) {
		if(command.equals("display")){
		return display(file);
		}
		
		else if(command.equals("add")){		
		return add(file,scanner);
		}
		
		else if(command.equals("delete")){		
		return delete(file,scanner);
		}
		
		else if(command.equals("clear")){		
		return(clear(file));
		}
		
		else if(command.equals("exit")){		
		System.exit(0);	
		}
		
		scanner.nextLine();
		return ("Command is Invalid");
	
	}
	// The function below serves to add a text into the file through the writeToFile function.
	private static String add(File file, Scanner scanner) {
		String lineToAdd = scanner.nextLine();
		writeToFile(lineToAdd.trim(),file);
		return("added to " +file.getName() +": \"" +lineToAdd +"\"");
	}

	// The function below writes a text into the file.
	private static void writeToFile(String lineToAdd, File file) {
		BufferedWriter fileWritten;
		try {
			fileWritten = new BufferedWriter(new FileWriter (file.getName(),true));
			if(numberOfLine(file)>0){
				fileWritten.newLine();
			}
			fileWritten.write(lineToAdd);
			fileWritten.close();
		} catch (IOException e) {
			messageToUser("Unable to add line");
		}
		
	}

	/* The function serves to delete a text from the file given a number.
	 * The texts in the file are numbered from top to bottom starting with 1.
	 */
	private static String delete(File file, Scanner scanner) {
		int deleteLineNum = scanner.nextInt();
		int totalNumberOfLine = numberOfLine(file);
		String deletedLine = "";
		try {
			if (deleteLineNum>totalNumberOfLine){ //no text on the given number
			return("Command is Invalid");
			}
			else{
			
				Scanner inputLines;
				Vector<String> lines = new Vector<String>();

				inputLines = new Scanner(file);
				for(int i = 0; i<totalNumberOfLine; i++){
					if(i == deleteLineNum-1){
						deletedLine= inputLines.nextLine();
					}
					else{
						lines.add(inputLines.nextLine());
					}
				}
				inputLines.close();
				reinputText(file, lines);
				return ("deleted from "+file.getName() +": \""+deletedLine +"\"");
			} 
		}catch (FileNotFoundException e) {
				return("File not found during delete");
		}
	}

	/* The function below serves to re-input the text back in to the file after clearing the file
	 * during the delete text process.
	 */
	private static void reinputText(File file, Vector<String> lines) {
		clear(file);
		for(int i = 0; i<lines.size();i++){
			writeToFile(lines.get(i),file);
		}
	}

	// The function below serves to count the number of lines of text present in the file.
	private static int numberOfLine(File file) {
		Scanner input;
		int lineNum = 0;
		try {
			input = new Scanner(file);
		
			if(!input.hasNext()){
				input.close();
				return lineNum;	
			}
			
			else{
				
				while(input.hasNext()){
					input.nextLine();
					lineNum++;
				}
				input.close();	
				}
		} catch (FileNotFoundException e) {
			messageToUser("File not found");
		}
		return lineNum;
	}

	/* The function below display all text in the file.
	 * The texts are numbered on their left.
	 */
	private static String display(File file) {
		Scanner input;
		try {
			input = new Scanner(file);
		
			if(!input.hasNext()){
				input.close();
				return(file.getName() +" is empty.");	
			}
			
			else{
				int listNum = 1;
				while(input.hasNext()){
					System.out.println(listNum +". " +input.nextLine());
					listNum++;
				}
				input.close();	
				}
		} catch (FileNotFoundException e) {
			return("File not found");
		}
		return ("-End of File-");
	}
	
	// The function below serves to clear all text in the file.
	private static String clear(File file) {
		try {
			new BufferedWriter(new FileWriter (file,false)).write("");
		} catch (IOException e) {
			messageToUser("Unable to clear");
		}
		return("All content cleared from " +file.getName());
	}
	
	// The function below serves to print the message to the user.	
	private static void messageToUser(String text) {
		System.out.println(text);
	}
	
	/* The function below serves to open the file given the file name.
	 * If no file by the given file name is found, the program will attempt to create the file.
	 */
	private static File openFile(String fileName) {
			File file = new File(fileName);
				try{
					if(!file.exists()){
						file.createNewFile();
					}
				}
				catch(IOException e){
					System.out.println("Error in reading/ creating of file.");
					System.exit(1);
				}
				return file;
	}
}
