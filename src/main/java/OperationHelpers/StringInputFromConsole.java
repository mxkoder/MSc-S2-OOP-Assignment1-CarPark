package OperationHelpers;

import java.util.Scanner;

public class StringInputFromConsole {
    private static Scanner stdin = new Scanner(System.in);

    /**
     * Method read in a string from the user from the console using the Scanner class, with a provided input prompt
     *
     * @param inputPrompt
     * @return String
     */
    public static String enterValueForStringWithPrompt(String inputPrompt) {
        System.out.printf(inputPrompt);
        return stdin.nextLine();
    }

    /**
     * Method to capitalise an input string and remove any white spaces from it.
     *
     * @param inputString
     * @return formattedString - input string capitalised and with white spaces removed.
     */
    public static String capitaliseStringAndRemoveWhitespace(String inputString) {

        String formattedString = inputString.toUpperCase().replaceAll("\\s", "");

        return formattedString;
    }
}
