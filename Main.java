import java.util.Scanner;

import javax.management.Query;

public class Main {
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);

        //make query object
        Query query = new Query();
        //query.readFromDisk("17 | 40");
        
        //start input loop
        while (true) {
        System.out.print("\nThe program is ready for the next command: ");
        String functionInput = scanner.nextLine().trim();
        System.out.println("input: " + functionInput);
        //String[] functionArray = functionInput.split(" ");

        if (functionInput.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            break;
        }

        switch (functionInput) {
            case "SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV":
                Hash hash = new Hash();
                hash.join();
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
        }
    }
    }
}
