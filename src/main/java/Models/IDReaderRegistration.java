package Models;

import InputOutput.StringInputFromConsole;

import static InputOutput.StringInputFromConsole.capitalizeStringAndRemoveWhitespace;
import static InputOutput.StringInputFromConsole.enterValueForStringWithPrompt;

public class IDReaderRegistration extends IDReader {

    private String regNumber;

    //TODO - improve comment for car plate readID method
    /**
     * Checks for current and standard UK reg numbers only.
     * @return
     */
    @Override
    public String readID() {
        String inputRegNumber = enterValueForStringWithPrompt("Please enter a valid UK car registration number: ");
        String formatterRegNumber = capitalizeStringAndRemoveWhitespace(inputRegNumber);

        if(formatterRegNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}$")) {
            this.regNumber = formatterRegNumber;
            System.out.println("The car registration number " + regNumber + " has been recorded by the registration number reader.");
            return regNumber;
        }
        else {
            System.out.println("Invalid registration number. Please enter a valid UK car registration number ");
            return null;
        }
    }

    @Override
    public String getID() {
        return this.regNumber;
    }
}
