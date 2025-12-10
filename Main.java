import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create scanner
        Scanner scanner = new Scanner(System.in);
        
        //start input loop
        while (true) {
        System.out.print("\nThe program is ready for the next command: ");
        String functionInput = scanner.nextLine().trim().toLowerCase();
        String[] functionArray = functionInput.split("\\s+");


        if (functionInput.equalsIgnoreCase("exit")) {
            System.out.println("Exiting program...");
            break;
        }

        switch (functionArray[1]) {
            case "a.col1,":
                Hash hash = new Hash();
                hash.join();
                break;
            case "count(*)":
                NestedLoop loop = new NestedLoop();
                loop.join();
                break;
            case "col2,":
                Aggregation agr = new Aggregation(functionArray[2], functionArray[4]);
                agr.createHash();
                break;
            default:
                System.out.println("Unknown command: " + functionInput);
                break;
        }
    }
    }
}
