import java.util.Scanner;

interface Function {
    public boolean apply(double output);
}

public class inputValidation {
    public static void main(String[] args) {
        System.out.println(getDouble(new Scanner(System.in), "insert double", "not even double noob",(output)->output>5));
    }

    public static double getDouble(Scanner input, String inputMsg, String errorMsg) {
        double output = 0;
        boolean invalid;
        do {
            System.out.println(inputMsg);
            invalid = !input.hasNextDouble();
            if (!invalid) {
                output = input.nextDouble();
            } else {
                input.next();
                System.out.println(errorMsg);
            }
        } while (invalid);
        return output;
    }
    
    public static double getDouble(Scanner input, String inputMsg, String errorMsg, Function condition) {
        double output = 0;
        boolean invalid;
        do {
            System.out.println(inputMsg);
            invalid = !input.hasNextDouble();
            if (!invalid) {
                output = input.nextDouble();
                invalid = !condition.apply(output);
            } else {
                input.next();
                System.out.println(errorMsg);
            }
        } while (invalid);
        return output;
    }
    
    public static int getInt(Scanner input, String inputMsg, String errorMsg) {
        int output = 0;
        boolean invalid;
        do {
            System.out.println(inputMsg);
            invalid = !input.hasNextInt();
            if (!invalid) {
                output = input.nextInt();
            } else {
                input.next();
                System.out.println(errorMsg);
            }
        } while (invalid);
        return output;
    }
}
