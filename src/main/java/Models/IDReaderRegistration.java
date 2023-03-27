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

    @Override
    public void setID(String ID) {
        boolean registrationRecorded = recordRegistrationIfCorrectFormat(ID);

        if(!registrationRecorded) {
            System.out.println("Invalid registration format, please make sure it is in the standard UK for of @@##@@@");
        }
    }

    @Override
    void resetToDefault() {
        this.regNumber = "";
        System.out.println("The registration reader has been reset to default and any previous recorded id has been cleared.");
    }

    private boolean recordRegistrationIfCorrectFormat (String inputRegistration) {
        String formatterRegNumber = capitalizeStringAndRemoveWhitespace(inputRegistration);
        if(formatterRegNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}$")) {
            this.regNumber = formatterRegNumber;
            System.out.println("The car registration number " + regNumber + " has been recorded by the registration number reader.");
            return true;
        }
        else {
            System.out.println();
            return false;
        }
    }
}
