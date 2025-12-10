import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class NestedLoop {
    private char[] buffer;
    private int qualifyTotal;

    public NestedLoop(){
        buffer = new char[4000];
        qualifyTotal = 0;
    }

    public int joinCondition(int randVB){
        //start a count of qualifying records
        int count = 0;
        //loop through the array of A and see if A randV > B randV
        for (int offset = 0; offset <= 3960; offset += 40){
            String record = String.valueOf(Arrays.copyOfRange(buffer, offset, offset + 40));
            int randVA = Integer.parseInt(record.substring(33, 37));
            //increase count
            if (randVA > randVB){
                count++;
            }
        }
        return count;
    }
    public void readDataset(){
        //loop over every file in A
        File folder = new File("Project3Dataset-A");
        //File folder = new File("testA");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt")); 

        //read each file in A
        if (files != null) {
            for (File file : files) {
                //store every record in current file of A in array
                try (FileReader fr = new FileReader(file)) {
                    int fileIndex = 0;
                    int charInt;
                    while((charInt = fr.read()) != -1){
                        buffer[fileIndex] = (char) charInt;
                        fileIndex++;
                    }
                    File folderB = new File("Project3Dataset-B");
                    File[] filesB = folderB.listFiles((dir, name) -> name.endsWith(".txt")); 

                    //loop through b file by file
                    if (filesB != null) {
                        for (File fileB : filesB){
                            try (FileReader frB = new FileReader(fileB)) {
                                int fileIndexB = 0;
                                int charIntB;
                                String recordStr;
                                //get entire file of b at a time
                                char[] fileContent = new char[4000];
                                while((charIntB = frB.read()) != -1){
                                    fileContent[fileIndexB] = (char) charIntB;
                                    fileIndexB++;
                                }
        
                                //loop through file B by 40 to look at each record in B
                                for (int offset = 0; offset <= 3960; offset += 40){
                                    char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
                                    //get record
                                    recordStr = new String(record);
                                    int randV = Integer.parseInt(recordStr.substring(33, 37));
                                    qualifyTotal += joinCondition(randV);
                                }
                                
                            } catch (IOException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    }
                }catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    public void join(){
        long startTime = System.currentTimeMillis();
        readDataset();
        long endTime = System.currentTimeMillis();
        System.out.println("Number of qualifying records: " + qualifyTotal);        
        System.out.println("Execution Time: " + (endTime - startTime) + " ms");

    }
}
