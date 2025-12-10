import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Hash{
    private HashMap<String, ArrayList<String>> hash;

    public Hash(){
        hash = new HashMap<>();
    }

    //track the time it takes to print - exclude from total (time to hash, match, read data and join)
    static long writeTime = 0;
    //get the first two columns for each record and return
    public void write(String recA, String recB){
        //get projected columns
        String A1 = recA.substring(0, 10);
        String A2 = recA.substring(11, 19);
        String B1 = recB.substring(0, 10);
        String B2 = recB.substring(11, 19);         
        String line = A1 + ", " + A2 + ", " + B1 + ", " + B2 + "\n";
        
        //meaure the time to print and add to writeTime
        long startTime = System.currentTimeMillis();
        System.out.print("Record: " + line);
        long endTime = System.currentTimeMillis();
        writeTime = writeTime + (endTime - startTime);

    }

    //build has on dataset A -- hash function is on randv = randv
    public void readDataset(String dataset){
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
                        int bucket = randV % 50;
                        String key = String.valueOf(bucket);

                        //check the dataset - if A then add to hash index
                        if(dataset.equals("Project3Dataset-A")){
                            //check if the bucket is empty
                            if (hash.get(key) == null) {
                                hash.put(key, new ArrayList<>());
                            }
                            hash.get(key).add(recordStr);
                        } else{
                            //we are in dataset b - check if hash value exists in hash index
                            boolean hashExists = false;
                            for (String i : hash.keySet()){
                                if (key.equals(i)){
                                    hashExists = true;
                                }
                            }
                            //if hash exists - search hash for a match from dataset A
                            if (hashExists = true){
                                ArrayList<String> recordList = hash.get(key);
                                //loop through all records in the bucket for match (possible change later if sort)
                                for(String rec : recordList){
                                    //get the randv from datasetA and check against the randV of b
                                    if(rec.substring(33, 37).equals(recordStr.substring(33, 37))){
                                        //join recA and recB
                                        write(rec, recordStr);
                                    }
                                }
                            }
                            
                        } 
                    }
                    
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }

            }
        }
    }

    public void join(){
        long startTime = System.currentTimeMillis();
        System.out.print("Qualifying Records: ");
        //build hash
        readDataset("Project3Dataset-A");
        //load dataset b one by one and join with hash
        readDataset("Project3Dataset-B");
        long endTime = System.currentTimeMillis();
        //subtract time to write from total
        System.out.println("Execution Time: " + ((endTime - startTime) - writeTime) + " ms");
        writeTime = 0;
    }
 
}