import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Aggregation {
    //keep the values of each recrd randV in an array
    private HashMap<String, ArrayList<Integer>> hash;
    private String function;
    private String dataset;

    public Aggregation(String inputFunction, String inputDataset){
        hash = new HashMap<>();
        function = inputFunction;
        dataset = "Project3Dataset-" + inputDataset.toUpperCase();
    }

    //loop through array of randv and either sum or avg
    public void aggregate(){
        //loop through key set
        for (String name : hash.keySet()){
            int sum = 0;
            for (int randV : hash.get(name)){
                sum += randV;
            }
            if (function.equals("sum(randomv)")){
                System.out.println("Record: " + name + ", " + sum);
            } else{
                System.out.println("Record: " + name + ", " + (sum / (hash.get(name).size())));
            }
        }
    }
    //build has on dataset -- key is name
    public void createHash(){
        //loop through text files in the directory
        File folder = new File(dataset);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt")); 

        //read each file
        if (files != null) {
            for (File file : files) {
                //start reading the file - read entire file at a time
                try (FileReader fr = new FileReader(file)) {
                    char[] fileContent = new char[4000];
                    int fileIndex = 0;
                    int charInt;

                    while ((charInt = fr.read()) != -1) {
                        fileContent[fileIndex] = (char) charInt;
                        fileIndex++;
                    }            
                    //loop through the file by 40 to look at each record
                    for (int offset = 0; offset + 40 <= fileContent.length; offset += 40){
                        char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
                        //get randV
                        String recordStr = new String(record);
                        int randV = Integer.parseInt(recordStr.substring(33, 37));
                        //hash based on this randv - randV % 50 = bucket (randv is 1-500 and we want 50 buckets)
                        String bucket = recordStr.substring(12, 19).trim();

                        //check if the has key has been added
                        if (!hash.containsKey(bucket)) {
                            hash.put(bucket, new ArrayList<>());
                        }
                        hash.get(bucket).add(randV);
                       
                    }
                    
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
            aggregate();
        }

        
    }
}
