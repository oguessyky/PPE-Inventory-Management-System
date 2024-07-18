import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner userInput = new Scanner(System.in)) {
            String input;
            System.out.print("Input something: ");
            do { 
                input = userInput.findInLine("(?=.*Try)(?=.*test).*");
                if(input == null){
                    userInput.nextLine();
                    System.err.println("Something wrong\n");
                    System.out.print("Input something: ");
                    
                }
            } while (input == null);
            System.out.println("You entered: " + input);
        }
        int i = 0;
        do{
            System.out.println("try: " + ++i);
        }while(i<10);
    }
}
