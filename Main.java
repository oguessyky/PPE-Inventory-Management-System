import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner userInput = new Scanner(System.in)) {
            System.out.print("Input something: ");
            String input = userInput.nextLine();
            System.out.println("You entered: " + input);
        }
    }
}
