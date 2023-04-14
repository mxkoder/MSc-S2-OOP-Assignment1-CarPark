package OperationHelpers;

import java.util.Scanner;

public class NumericInputFromConsole {

    private static Scanner stdin = new Scanner(System.in);

    /**
     * Method that reads a user input integer from the console.
     * It will print an error message if a non-integer value is entered
     *
     * @return integer from user input
     */
    public static int readIntFromConsoleNoPrompt() {
        do {
            try {
                return Integer.parseInt(stdin.nextLine());

            } catch (NumberFormatException e) {
                System.out.printf("The value entered is not valid. Please enter a whole number:");
            }
        } while (true);

    }

    public static int readIntFromConsoleWithPrompt(String prompt) {
        System.out.println(prompt);
        return readIntFromConsoleNoPrompt();
    }
}
