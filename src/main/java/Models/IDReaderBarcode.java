package Models;

import InputOutput.StringInputFromConsole;

import static InputOutput.StringInputFromConsole.enterValueForStringWithPrompt;

public class IDReaderBarcode extends IDReader {

    private String barcodeID;

    // TODO - check if need both constructors

    public IDReaderBarcode(String barcodeID) {
        this.barcodeID = barcodeID;
    }

    //TODO - add java doc comment to explain fixed type of barcode input
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

    @Override
    public void setID(String ID) {
        boolean barcodeRecorded = recordBarcodeIfCorrectFormat(ID);

        if(!barcodeRecorded) {
            System.out.println("Invalid barcode format, barcode should be 12 digits.");
        }
    }

    @Override
    public void resetToDefault() {
        this.barcodeID ="";
        System.out.println("The barcode reader has been reset to default and any previous recorded id has been cleared.");
    }

    private boolean recordBarcodeIfCorrectFormat (String inputBarcode) {
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
