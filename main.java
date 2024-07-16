import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Hello, World!");
        String name = userInput.nextLine();
        System.out.println("Hi, " + name);
        userInput.close();
    }
  }