package IDReaders;

import static OperationHelpers.StringInputFromConsole.enterValueForStringWithPrompt;

public class IDReaderBarcode extends IDReader {

    private String barcodeID;

    public IDReaderBarcode(String barcodeID) {
        this.barcodeID = barcodeID;
    }

    /**
     * Method to read in a barcode from user input via the console
     * <P>The method reads a 12 digit barcode as a string. If the barcode has the correct format (12 single digits) the value is
     * recorded in the barcode reader using the recordBarcodeIfCorrectFormat method</P>
     * <p>If the barcode is not in the correct format, the user will be prompted to re enter the barcode</p>
     */
    @Override
    public void readID() {
        String inputBarcode = "";

        while (!recordBarcodeIfCorrectFormat(inputBarcode)) {
            inputBarcode = enterValueForStringWithPrompt("Please enter the 12 digit barcode of the parking pass: ");
        }
    }

    @Override
    public String getID() {
        return barcodeID;
    }

    /**
     * Method to read in a barcode from a barcodeID input parameter
     * <P>The method takes a 12 digit barcode as a string input parameter. If the barcode has the correct format (12 single digits) the value is
     * recorded in the barcode reader using the recordBarcodeIfCorrectFormat method</P>
     * <p>If the barcode is not in the correct format, a message is printed to the console to state that the barcode format was incorrect</p>
     * @param barcodeID String - 12 digit barcode ID
     */
    @Override
    public void setID(String barcodeID) {
        boolean barcodeRecorded = recordBarcodeIfCorrectFormat(barcodeID);

        if(!barcodeRecorded) {
            System.out.println("Invalid barcode format, barcode should be 12 digits.");
        }
    }

    @Override
    public void resetToDefault() {
        this.barcodeID ="";
        System.out.println("The barcode reader has been reset to default and any previous recorded id has been cleared.");
    }

    /**
     * Method to check that the input parameter barcode ID is in the correct format to be recorded by the barcode reader
     * <p>The barcode is checked against a regex matcher, if it consists of 12 digits it is recorded as the barcodeID in the barcode reader
     * and a confirmation message is printed to the console.
     * If there is no match with the regex, a boolean false is returned and the ID is not recorded in the reader</p>
     * @param inputBarcode String - 12 digit barcode ID
     * @return Boolean - returns 'true' if the ID is recorded in the reader, and 'false' if the ID is not recorded
     */
    public boolean recordBarcodeIfCorrectFormat (String inputBarcode) {
        if (inputBarcode.matches("^\\d{12}$")) {
            this.barcodeID = inputBarcode;
            System.out.println("The value of the barcode reader has been set to " + barcodeID);
            return true;
        } else {
            System.out.println();
            return false;
        }
    }
}
