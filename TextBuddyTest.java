
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

  }
 }

