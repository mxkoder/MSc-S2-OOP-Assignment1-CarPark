package Models;

import InputOutput.StringInputFromConsole;

import static InputOutput.StringInputFromConsole.enterValueForStringWithPrompt;

public class IDReaderBarcode extends IDReader {

    private String barcodeID;

    // TODO - check if need both constructors
    public IDReaderBarcode() {
    }

    public IDReaderBarcode(String barcodeID) {
        this.barcodeID = barcodeID;
    }

    @Override
    public String readID() {
        String inputBarcode;
        inputBarcode = enterValueForStringWithPrompt("Please enter the 12 digit barcode of the parking pass: ");

        if (inputBarcode.matches("^\\d{12}$")) {
            this.barcodeID = inputBarcode;
            System.out.println("The parking pass barcode " + barcodeID + " has been recorded by the barcode reader.");
            return barcodeID;
        }
        else {
            System.out.println("Invalid barcode. Please enter a valid barcode with 12 digits and no spaces.");
            return null;
        }
    }

    @Override
    public String getID() {
        return barcodeID;
    }
}
