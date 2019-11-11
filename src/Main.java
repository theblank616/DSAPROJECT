
/* This code parses the files assigning a unique id to every word in the Blog
* It works for all the 88000 data files just change the path variable to the
* main three directories folder
* it will roughly take 20-25 minutes for all the files*/
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {
    public static void main(String args[]) {
                //hashmap to store the word as key and ID as value
                HashMap<String,Integer> wordID = new HashMap<>();
                Integer index = 1; //index tracking
                FileReader reader;
                String path = "C:\\Users\\Tariq Farooq\\Desktop\\SEMESTER 3\\Data Structures and Algorithms\\PROJET FILS\\";
                // try-catch block to handle exceptions
        System.out.println(path);
                try {
                    // Create a file object
                    File f = new File(path);  //getting the three main folders
                    // Get all the names of the files present
                    // in the given directory
                    String[] folders = f.list();       //storing the sub-directories name in an string array
                    for (int fol = 0; fol < folders.length; fol++) {
                        String[] files = new File(path+folders[fol]).list(); //storing the file names in the array
                        System.out.println(fol);
                        for (int i = 0; i < files.length; i++) {
                            reader = new FileReader(path + folders[fol] + "\\" + files[i]);
                        Object obj = new JSONParser().parse(reader);    //parsing the file as json object
                        JSONObject jsonObject = (JSONObject) obj;
                        String text = (String) jsonObject.get("text");      //extracting the text object
                        text= text.replaceAll("(['])", "");  //replace apostrophe with nothing lel
                        text = text.replaceAll("([^a-zA-Z0-9])", " ");  //replacing punctuations with space
                        text = text.toLowerCase();   //all lower case
                        Matcher m = Pattern.compile("[a-zA-Z0-9]+").matcher(text);     //reading single word
                        while (m.find()) {
                            //store the word in the hash map if not already present
                            if(wordID.containsKey(m.group())){
                                continue;}
                            wordID.put(m.group(),index++);
                        }
                        reader.close();
                        }
                    }
                }
                catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                JSONObject json = new JSONObject();
                wordID.forEach((k,v)->{
                    json.put(k,v);  //parsing the hash map (key,value) as json object
                });
                try (FileWriter file = new FileWriter(path+"Key-Value.json"))
                {
                    System.out.println(json.toJSONString());
                    file.write(json.toJSONString()); //writing the file in json format "key":"value"
                    file.flush();
                } catch (IOException e) {
                e.printStackTrace();
                 }
            }

        }

