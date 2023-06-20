import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String expStr = in.nextLine();

        try {

            System.out.println(Calculator.calculate(expStr));

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();

        }

    }
}