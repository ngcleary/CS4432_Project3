import java.util.Scanner;

import javax.management.Query;

public class Main {
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        
        //start input loop
        while (true) {
        System.out.print("\nThe program is ready for the next command: ");
        String functionInput = scanner.nextLine().trim();

        if (functionInput.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            break;
        }

        switch (functionInput) {
            case "SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV":
                Hash hash = new Hash();
                hash.join();
                break;
            case "SELECT count(*) FROM A, B WHERE A.RandomV > B.RandomV":
                NestedLoop loop = new NestedLoop();
                loop.join();
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
        }
    }
    }
}
