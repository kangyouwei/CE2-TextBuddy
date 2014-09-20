/* CE2: TextBuddy+
 * Name: Kang You Wei
 * Matriculation Number: A0108380L
 * 
 * This program serves to manipulate text in a .txt file. 
 * The .txt file is created if no file by the name is found.
 */

import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextBuddy {
	private static Scanner scanner;
	private static final String MSG_WELCOME = "Welcome to TextBuddy. %s is ready to be used.";
	private static final String MSG_COMMAND = "command: ";
	private static final String MSG_INVALID_COMMAND = "Command is Invalid.";
	private static final String MSG_MISSING_FILE = "File not found.";
	private static final String MSG_EMPTY_FILE = "%s is empty.";
	private static final String MSG_SUCCESS_ADD = "Added to %s: \"%s\"";
	private static final String MSG_SUCCESS_DELETE = "Deleted from %s: \"%s\"";
	private static final String MSG_SUCCESS_CLEAR = "All lines cleared from %s.";
	private static final String MSG_SUCCESS_SORT = "File is sorted.";
	private static final String MSG_FAIL_ADD = "Unable to add line.";
	private static final String MSG_FAIL_CLEAR = "Unable to clear.";
	private static final String MSG_FAIL_SEARCH = "Phrase not found.";
	private static final String MSG_FAIL_READ_FILE = "Unable to read file.";
	
	
	

	public static void main(String[] args){
		String fileName = args[0];
		scanner = new Scanner(System.in);
		File file= openFile(fileName);
		
		messageToUser(String.format(MSG_WELCOME, fileName));
		while(true){
			System.out.print(MSG_COMMAND);
			String command = scanner.nextLine();
			String result = executeCommand(command,file);
			messageToUser(result);
		}
	}

	// The function below serves to choose the command depending on the keyword provided.
	public static String executeCommand(String command, File file) {
		String commandWord = getFirstWord(command);
		if(commandWord.equals("display")){
		return display(file);
		}
		
		else if(commandWord.equals("add")){		
		return add(file,command);
		}
		
		else if(commandWord.equals("delete")){		
		return delete(file,command);
		}
		
		else if(commandWord.equals("clear")){		
		return clear(file);
		}
		
		else if(commandWord.equals("search")){		
			return search(file,command);
		}
		
		else if(commandWord.equals("sort")){		
			return sort(file);
		}
		
		else if(commandWord.equals("exit")){		
		System.exit(0);	
		}
		
		return (MSG_INVALID_COMMAND);
	
	}

	// The function below serves to add a text into the file through the writeToFile function.
	public static String add(File file, String command) {
		String lineToAdd = removeFirstWord(command);
		writeToFile(lineToAdd,file);
		return(String.format(MSG_SUCCESS_ADD, file.getName(),lineToAdd));
	}

	/* The function serves to delete a text from the file given a number.
	 * The texts in the file are numbered from top to bottom starting with 1.
	 */
	public static String delete(File file, String command) {
		
		
		String deleteString = removeFirstWord(command);
		
	if (isValidDeleteParameter(deleteString, file)){
		String deletedLine = "";
		try {
				Scanner inputLines;
				int deleteLineNum = Integer.parseInt(deleteString);
				String[] lines;
				lines = new String[numberOfLine(file)];
				int j = 0;
				inputLines = new Scanner(file);
				for(int i = 0; i<numberOfLine(file); i++){
					if(i == deleteLineNum-1){
						deletedLine= inputLines.nextLine();
					}
					else{
						lines[j]=inputLines.nextLine();
						j++;
					}
				}
				inputLines.close();
				reinputText(file, lines,j);
				return (String.format(MSG_SUCCESS_DELETE, file.getName(),deletedLine)); 
		
		}catch (FileNotFoundException e) {
				return(MSG_MISSING_FILE);
		}
	
			
	}
	return(MSG_INVALID_COMMAND);
	}

	/* The function below display all text in the file.
	 * The texts are numbered on their left.
	 */
	public static String display(File file) {
		Scanner input;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			input = new Scanner(file);
		
			if(!input.hasNext()){
				input.close();
				return(String.format(MSG_EMPTY_FILE, file.getName()));	
			}
			
			else{
				
				int listNum = 1;
				while(input.hasNext()){
					stringBuilder.append(listNum +". " +input.nextLine() +"\n");
					listNum++;
				}
				input.close();	
				}
		} catch (FileNotFoundException e) {
			return(MSG_MISSING_FILE);
		}
		return stringBuilder.toString();
	}

	// The file serves to search the file based on the given string or word.
	public static String search(File file, String command) {
		String searchWord = removeFirstWord(command);
		Scanner input;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			input = new Scanner(file);
		
			if(!input.hasNext()){
				input.close();
				return(String.format(MSG_EMPTY_FILE, file.getName()));	
			}
			
			else{
				
				int listNum = 1;
				while(input.hasNext()){
					String currentLine = input.nextLine();
					if(currentLine.contains(searchWord)){
					stringBuilder.append(listNum +". " +currentLine +"\n");
					listNum++;
					}	
				}
				if (listNum == 1){
				stringBuilder.append(MSG_FAIL_SEARCH);
				}
				input.close();	
				}
		} catch (FileNotFoundException e) {
			return(MSG_MISSING_FILE);
		}
		return stringBuilder.toString();
	}

	// The function serves to call up the bubble sort to sort the lines in the file based on alphabetical order.

	public static String sort(File file) {
		Scanner input;
		try {
			input = new Scanner(file);
		
			if(!input.hasNext()){
				input.close();
				return(String.format(MSG_EMPTY_FILE, file.getName()));
			}
			
			else{
				String[] lines;
				lines = new String[numberOfLine(file)];
				scanIntoArray(lines,file);
				bubbleSort(lines,file);
				
				reinputText(file, lines, numberOfLine(file));
				input.close();
				return (MSG_SUCCESS_SORT);
			}	
		}catch (FileNotFoundException e) {
			return(MSG_MISSING_FILE);
		}
	}


	// The function below serves to clear all text in the file.
	public static String clear(File file) {
		try {
			new BufferedWriter(new FileWriter (file,false)).write("");
		} catch (IOException e) {
			messageToUser(MSG_FAIL_CLEAR);
		}
		return(String.format(MSG_SUCCESS_CLEAR, file.getName()));
	}

	/* The function below serves to open the file given the file name.
	 * If no file by the given file name is found, the program will attempt to create the file.
	 */
	public static File openFile(String fileName) {
			File file = new File(fileName);
				try{
					if(!file.exists()){
						file.createNewFile();
					}
				}
				catch(IOException e){
					System.out.println(MSG_FAIL_READ_FILE);
					System.exit(1);
				}
				return file;
	}

	// The function below serves to print the message to the user.	
	public static void messageToUser(String text) {
		System.out.println(text);
	}

	// The function extracts commandWord from the commandLine
	private static String getFirstWord(String commandLine) {
	StringTokenizer tokenizedCommand = new StringTokenizer(commandLine);
	String commandWord = tokenizedCommand.nextToken();
	
	return commandWord;
	}

	/* The functions do the sorting for the array.
	 * If the file contains two exact same word with different cases, the order will be based on which
	 * is first to be input.
	 */
	private static void bubbleSort(String[] lines, File file) {
		Scanner input;
		try {
			input = new Scanner(file);
		for ( int i =1; i< numberOfLine(file);i++){
			boolean isSorted = true;
			for( int j=0; j<numberOfLine(file)-1;j++){
				if(lines[j].compareToIgnoreCase(lines[j+1])==0){
					continue;
				}
				else if (lines[j].compareToIgnoreCase(lines[j+1])>0){
					String temp = lines[j];
					lines[j] = lines[j+1];
					lines[j+1] = temp;
					isSorted = false;
				}
			}
			if (isSorted){
				input.close();
				break;
			}
		}	
		}catch (FileNotFoundException e) {
			messageToUser(MSG_MISSING_FILE);
		}		
	}

	/* The function below serves to re-input the text back in to the file after clearing the file
	 * during the delete text process.
	 */
	public static void reinputText(File file, String[] lines, int totalLines) {
		clear(file);
		for(int i = 0; i<totalLines;i++){
			writeToFile(lines[i],file);
		}
	}
	// The function below writes a text into the file.
	public static void writeToFile(String lineToAdd, File file) {
		BufferedWriter fileWritten;
		try {
			fileWritten = new BufferedWriter(new FileWriter (file.getName(),true));
			if(numberOfLine(file)>0){
				fileWritten.newLine();
			}
			fileWritten.write(lineToAdd);
			fileWritten.close();
		} catch (IOException e) {
			messageToUser(MSG_FAIL_ADD);
		}
		
	}

	// The function serves to put all the lines in the file into a string array for sorting.
	private static void scanIntoArray(String[] lines, File file) {
		Scanner input;
		try {
			input = new Scanner(file);
		for(int i = 0; i<numberOfLine(file); i++){
			lines[i]= input.nextLine();
			}
		input.close();	
		}catch (FileNotFoundException e) {
			messageToUser(MSG_MISSING_FILE);
		}
	}

	// The function below serves to count the number of lines of text present in the file.
	public static int numberOfLine(File file) {
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
			messageToUser(MSG_MISSING_FILE);
		}
		return lineNum;
	}

	//The function serves to remove the command word which is the first word that the user inputs.
	private static String removeFirstWord(String commandLine) {
	return commandLine.replaceFirst(getFirstWord(commandLine), "").trim();
	}

	//The function serves to check whether it is possible to delete the line.
	private static boolean isValidDeleteParameter(String deleteString, File file) {
	boolean lineIsInFile;
	
	if (areDigits(deleteString) && (numberOfWords(deleteString) == 1)) {
	    lineIsInFile = Integer.valueOf(deleteString) <= numberOfLine(file);
	    return lineIsInFile;
	}
	return false;
	}

	//The function serves to check whether the input is a digit for the delete function to work.
	private static boolean areDigits(String deleteString) {
	boolean areDigits;
	
	areDigits = true;
	for (char c : deleteString.toCharArray()) {
	    if (!Character.isDigit(c)) {
		areDigits = false;
		break;
	    }
	}
	return areDigits;
	}
	
	// The function serves to count the number of words in the line for use in the delete function
	private static int numberOfWords(String s) {
	StringTokenizer st = new StringTokenizer(s);
	return st.countTokens();
	}
}

