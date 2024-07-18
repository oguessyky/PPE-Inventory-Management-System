import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner userInput = new Scanner(System.in)) {
            String input;
            do { 
                System.out.print("Input your gmail: ");
                //input = userInput.findInLine("(?=.*Try)(?=.*test).*");
                //input = userInput.findInLine("^Try.*test.*");
                input = userInput.nextLine().trim();
                if (!input.matches("^[\\w.-]+@gmail\\.com$")) {
                    System.err.println("Invalid email. Please try again.\n");
                }
            } while (!input.matches("^[\\w.-]+@gmail\\.com$"));
            System.out.println("You entered: " + input);
        }

        int i = 0;
        do {
            System.out.println("try: " + ++i);
        } while (i < 10);
    }
}
