package InputOutput;

import java.util.Scanner;

public class NumericInputFromConsole {

    private static Scanner stdin = new Scanner(System.in);

    /**Method that reads a user input integer from the console.
     * It will print an error message if a non-integer value is entered
     *
     * @return integer from user input
     */
    public static int readIntFromConsoleNoPrompt()
    {
        do
        {
            try
            {
                return Integer.parseInt(stdin.nextLine());

            }
            catch (NumberFormatException e)
            {
                System.out.printf("The value entered is not valid. Please enter a whole number:");
            }
        } while(true);

    }

    /** Method that reads a user input integer from the console within a set range.
     *  <p> If the user enters a value outside of the specified range, this will be caught and
     * the user will receive a message with a user promptMessage (from input parameters) asking them
     * to re enter a value within the range. </p>
     *
     * @param rangeMin - Integer - minimum bound of range
     * @param rangeMax - Integer - maximum bound of range
     * @param promptMessage - String - Prompt for the user which appears if a value outside the range is entered
     * @return integer within the specified range from user input
     */
    public static int readIntFromConsoleInRangeWithPrompt(int rangeMin, int rangeMax, String promptMessage)
    {
        while(true)
        {
            int number = readIntFromConsoleNoPrompt();
            if(number >= rangeMin && number <= rangeMax)
            {
                return number;
            }

            System.out.printf("%s %d and %d:", promptMessage, rangeMin, rangeMax);
        }
    }

    /**
     * Method to check if an integer is within a specified range
     * @param rangeMin - Integer - minimum bound of range
     * @param rangeMax - Integer - maximum bound of range
     * @param inputInteger - Integer to check
     * @param promptMessage - String - Prompt for the user which appears if a value outside the range is entered
     * @return boolean - True if the integer to check is withing the specified range
     */
    public static boolean checkIntIsInRangeWithPrompt(int rangeMin, int rangeMax, int inputInteger, String promptMessage) {
        if (inputInteger >= rangeMin && inputInteger <= rangeMax) {
            return true;
        }

        System.out.printf("%s %d and %d.\n", promptMessage, rangeMin, rangeMax);
        return false;

    }
}
