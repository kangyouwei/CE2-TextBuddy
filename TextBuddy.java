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
	    scanner = new Scanner(System.in);
	    File file= openFile(fileName);

	    messageToUser("Welcome to TextBuddy." +fileName +" is ready to be used");
	    while(true){
	        System.out.print("command: ");
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
	    	return(clear(file));
	    }

	    else if(commandWord.equals("search")){		
	    	return search(file,command);
	    }
	    
	    else if(commandWord.equals("exit")){        
	    	System.exit(0); 
	    }

	    
	    return ("Command is Invalid");

	}
	// The function below serves to add a text into the file through the writeToFile function.
	public static String add(File file, String command) {
	    String lineToAdd = removeFirstWord(command);
	    writeToFile(lineToAdd.trim(),file);
	    return("added to " +file.getName() +": \"" +lineToAdd +"\"");
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
	        messageToUser("Unable to add line");
	    }

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
	            Vector<String> lines = new Vector<String>();

	            inputLines = new Scanner(file);
	            for(int i = 0; i<numberOfLine(file); i++){
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

	    }catch (FileNotFoundException e) {
	            return("File not found during delete");
	    }


	}
	return("Command is Invalid");
	}
	
	public static String search(File file, String command) {
	    String searchWord = removeFirstWord(command);
	    Scanner input;
	    StringBuilder stringBuilder = new StringBuilder();
	    try {
	        input = new Scanner(file);

	        if(!input.hasNext()){
	            input.close();
	            return(file.getName() +" is empty.");   
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
	            	stringBuilder.append("Phrase Not Found");
	            }
	            input.close();  
	            }
	    } catch (FileNotFoundException e) {
	        return("File not found");
	    }
	    return stringBuilder.toString();
	}
	
	private static boolean isValidDeleteParameter(String deleteString, File file) {
	    boolean lineIsInFile;

	    if (areDigits(deleteString) && (numberOfWords(deleteString) == 1)) {
	        lineIsInFile = Integer.valueOf(deleteString) <= numberOfLine(file);
	        return lineIsInFile;
	    }
	    return false;
	    }

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

	 private static int numberOfWords(String s) {
	        StringTokenizer st = new StringTokenizer(s);
	        return st.countTokens();
	        }

	/* The function below serves to re-input the text back in to the file after clearing the file
	 * during the delete text process.
	 */
	public static void reinputText(File file, Vector<String> lines) {
	    clear(file);
	    for(int i = 0; i<lines.size();i++){
	        writeToFile(lines.get(i),file);
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
	        messageToUser("File not found");
	    }
	    return lineNum;
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
	            return(file.getName() +" is empty.");   
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
	        return("File not found");
	    }
	    return stringBuilder.toString();
	}

	// The function below serves to clear all text in the file.
	public static String clear(File file) {
	    try {
	        new BufferedWriter(new FileWriter (file,false)).write("");
	    } catch (IOException e) {
	        messageToUser("Unable to clear");
	    }
	    return("All content cleared from " +file.getName());
	}

	// The function below serves to print the message to the user.  
	public static void messageToUser(String text) {
	    System.out.println(text);
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
	                System.out.println("Error in reading/ creating of file.");
	                System.exit(1);
	            }
	            return file;
	}

	 private static String removeFirstWord(String commandLine) {
	        return commandLine.replace(getFirstWord(commandLine), "").trim();
	        }

	        // Extracts commandWord from the commandLine
	        private static String getFirstWord(String commandLine) {
	        StringTokenizer tokenizedCommand = new StringTokenizer(commandLine);
	        String commandWord = tokenizedCommand.nextToken();

	        return commandWord;
	        }