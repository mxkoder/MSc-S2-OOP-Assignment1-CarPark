package InputOutput;

import java.util.Scanner;

public class StringInputFromConsole {
    private static Scanner stdin = new Scanner(System.in);

    /**
     * Generic method that takes in an input prompt (an instruction visible on the console). Generic function which can be used to
     * take a String input from a user.
     * @param inputPrompt
     * @return String
     */
    public static String enterValueForStringWithPrompt(String inputPrompt) {
        System.out.printf(inputPrompt);
        return stdin.nextLine();
    }

    /**
     * Generic method capitalises an input string, removes white spaces, and returns the input string.
     * returns the formatted input String
     * @param string
     * @return String
     */
    public static String capitalizeStringAndRemoveWhitespace (String string) {

        string = string.toUpperCase().replaceAll("\\s","");

        return string;
    }
}
