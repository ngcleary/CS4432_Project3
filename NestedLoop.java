import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class NestedLoop {
    private char[] buffer;
    private int joinCount;

    public NestedLoop(){
        buffer = new char[4000];
        joinCount = 0;
    }

    public String fetchRecord(File file, int offset){
        System.out.println("in fetch record");
        try (FileReader fr = new FileReader(file)) {
            int fileIndex = 0;
            int charInt;
            String recordStr;
            char[] fileContent = new char[4000];
            while((charInt = fr.read()) != -1){
                fileContent[fileIndex] = (char) charInt;
                fileIndex++;
            }
            //loop through file B by 40 to look at each record in B
            char[] record = Arrays.copyOfRange(fileContent, offset, offset + 40);
            //get record
            recordStr = new String(record);
            return recordStr;
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return "";
    }

    public void joinCondition(int randVB){
        System.out.println("in joincondition");
        //loop through the array of A and see if A randV > B randV
        for (int offset = 0; offset + 40 <= buffer.length; offset += 40){
            String record = String.valueOf(Arrays.copyOfRange(buffer, offset, offset + 40));
            int randVA = Integer.parseInt(record.substring(33, 37));
            if (randVA > randVB){
                joinCount++;
            }
        }
    }
    public void readDataset(){
        //loop over every file in A
        File folder = new File("Project3Dataset-A");
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
                    //read dataset B - redundant code fix later
                    File folderB = new File("Project3Dataset-B");
                    File[] filesB = folderB.listFiles((dir, name) -> name.endsWith(".txt")); 

                    if (filesB != null) {
                        for (File fileB : filesB){
                             //loop through file B by 40 to look at each record in B
                            for (int offset = 0; offset <= 3960; offset += 40){
                                String record = fetchRecord(fileB, offset);
                                int randV = Integer.parseInt(record.substring(33, 37));
                                //check join condition - loop through array 
                                joinCondition(randV);
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
        System.out.println("hello world");
        readDataset();
        System.out.println("Number of qualifying records: " + joinCount);

    }
}
