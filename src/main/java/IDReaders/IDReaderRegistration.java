package IDReaders;

import static OperationHelpers.StringInputFromConsole.capitaliseStringAndRemoveWhitespace;
import static OperationHelpers.StringInputFromConsole.enterValueForStringWithPrompt;

public class IDReaderRegistration extends IDReader {

    private String regNumber;

    public IDReaderRegistration(String regNumber) {
        this.regNumber = regNumber;
    }

    /**
     * Method to read in a car registration from user input via the console
     * <P>The method reads a standard UK registration number in as a string. If the barcode has the correct format (@@##@@@) the value is
     * recorded in the registration reader using the recordRegistrationIfCorrectFormat method</P>
     * <p>If the registration is not in the correct format (after capitalisation and removal of white spaces),
     * the user will be prompted to re enter the registration</p>
     */
    @Override
    public void readID() {
        String inputRegNumber = "";

        while(!recordRegistrationIfCorrectFormat(inputRegNumber)) {
            inputRegNumber = enterValueForStringWithPrompt("Please enter a valid UK car registration number: ");
        }
    }

    @Override
    public String getID() {
        return this.regNumber;
    }


    /**
     * Method to read in a car registration number from a registrationID input parameter
     * <P>The method takes a string standard UK registration number as a string input parameter. The registration string is then capitalised,
     * and white spaces are removed in the recordRegistrationIfCorrectFormat method. </p>
     * <p>If the registration then has the correct format (@@##@@@) the value is recorded in the registration reader using the recordRegistrationIfCorrectFormat method</p>
     * <p>If the registration is not in the correct format, a message is printed to the console to state that the registration format was incorrect</p>
     * @param registrationID String - Standard UK car registration number
     */
    @Override
    public void setID(String registrationID) {
        boolean registrationRecorded = recordRegistrationIfCorrectFormat(registrationID);

        if(!registrationRecorded) {
            System.out.println("Invalid registration format, please make sure it is in the standard UK for of @@##@@@");
        }
    }

    @Override
    public void resetToDefault() {
        this.regNumber = "";
        System.out.println("The registration reader has been reset to default and any previous recorded id has been cleared.");
    }

    /**
     * Method to check that the input parameter registration ID is in the correct format to be recorded by the registration reader
     * <p>First, the method capitalizeStringAndRemoveWhitespace is applied to capitalise the input registration and remove any white spaces.</p>
     * <p>The registration is checked against a regex matcher, if it is in the format @@##@@@ it is recorded as the regNumber in the registration reader
     * and a confirmation message is printed to the console.
     * If there is no match with the regex, a boolean false is returned and the ID is not recorded in the reader</p>
     * @param inputRegistration String - Standard UK car registration number
     * @return Boolean - returns 'true' if the ID is recorded in the reader, and 'false' if the ID is not recorded
     */
    public boolean recordRegistrationIfCorrectFormat (String inputRegistration) {
        String formatterRegNumber = capitaliseStringAndRemoveWhitespace(inputRegistration);
        if(formatterRegNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}$")) {
            this.regNumber = formatterRegNumber;
            System.out.println("The value of the registration reader has been set to " + regNumber);
            return true;
        }
        else {
            System.out.println();
            return false;
        }
    }
}
