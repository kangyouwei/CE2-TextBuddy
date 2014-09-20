	
import static org.junit.Assert.assertEquals;
	
import java.io.File;
import java.io.IOException;
	
import org.junit.Test;
	
public class TextBuddyTest {
	
 @Test
public void testTextBuddy(){
	 String fileName = "testTextFile.txt";
	 File file = new File(fileName);
	 try{
		 if(!file.exists()){
			 file.createNewFile();
		 }
	 }
	 catch(IOException e){
		 System.out.println("Error in reading/ creating of file.");
	 }
	

	 
//Test for search
	  
	 TextBuddy.clear(file); 
	 assertEquals("Clear file before test" , 0 ,TextBuddy.numberOfLine(file));
	 assertEquals("Display before add", (file + " is empty."),TextBuddy.display(file));
	 assertEquals("add", ("added to " + fileName + ": \"aaa\""), TextBuddy.add(file, "add aaa"));
	 assertEquals("add 2", ("added to " + fileName + ": \"bbb\""), TextBuddy.add(file, "add bbb"));
	 assertEquals("add 3", ("added to " + fileName + ": \"bbaa\""), TextBuddy.add(file, "add bbaa"));
	 assertEquals("add 4", ("added to " + fileName + ": \"aabb\""), TextBuddy.add(file, "add aabb"));
	 assertEquals("add 5", ("added to " + fileName + ": \"aba\""), TextBuddy.add(file, "add aba"));
	 assertEquals("add 6", ("added to " + fileName + ": \"bab\""), TextBuddy.add(file, "add bab"));
	 assertEquals("add 7", ("added to " + fileName + ": \"bac\""), TextBuddy.add(file, "add bac"));
	 assertEquals("add 8", ("added to " + fileName + ": \"a\""), TextBuddy.add(file, "add a"));
	 assertEquals("search for a " , "1. aaa\n2. bbaa\n"
		    + "3. aabb\n4. aba\n5. bab\n6. bac\n7. a\n"  ,TextBuddy.search(file, "search a"));
		  
	 assertEquals("word not found" , "Phrase Not Found"  ,TextBuddy.search(file, "search d"));
		  
	 assertEquals("search for ab " , "1. aabb\n2. aba\n3. bab\n"  ,TextBuddy.search(file, "search ab"));
	 assertEquals("search for aa " , "1. aaa\n2. bbaa\n3. aabb\n"  ,TextBuddy.search(file, "search aa"));
		
	 TextBuddy.clear(file);	
	 assertEquals("File is cleared" , 0 ,TextBuddy.numberOfLine(file));
	 
//Test for Sort
	 assertEquals("add 1", ("added to " + fileName + ": \"bbnana\""), TextBuddy.add(file, "add bbnana"));
	 assertEquals("add 2", ("added to " + fileName + ": \"pineapple\""), TextBuddy.add(file, "add pineapple"));
	 assertEquals("add 3", ("added to " + fileName + ": \"apple\""), TextBuddy.add(file, "add apple"));
	 assertEquals("add 4", ("added to " + fileName + ": \"orange\""), TextBuddy.add(file, "add orange"));
	 assertEquals("add 5", ("added to " + fileName + ": \"banana\""), TextBuddy.add(file, "add banana"));
	 
	 assertEquals("sort", ("File is Sorted"), TextBuddy.sort(file));
	 assertEquals("display", "1. apple\n2. banana\n3. bbnana\n4. orange\n5. pineapple\n", TextBuddy.display(file));
	 
	 TextBuddy.clear(file);	
	 assertEquals("File is cleared" , 0 ,TextBuddy.numberOfLine(file));
	 assertEquals("add 1", ("added to " + fileName + ": \"cow\""), TextBuddy.add(file, "add cow"));
	 assertEquals("add 2", ("added to " + fileName + ": \"dog\""), TextBuddy.add(file, "add dog"));
	 assertEquals("add 3", ("added to " + fileName + ": \"Cat\""), TextBuddy.add(file, "add Cat"));
	 assertEquals("add 4", ("added to " + fileName + ": \"Crow\""), TextBuddy.add(file, "add Crow"));
	 assertEquals("add 5", ("added to " + fileName + ": \"chips\""), TextBuddy.add(file, "add chips"));
	 
	 assertEquals("sort", ("File is Sorted"), TextBuddy.sort(file));
	 assertEquals("display", "1. Cat\n2. chips\n3. cow\n4. Crow\n5. dog\n", TextBuddy.display(file));
 }
}
		
