package IDReaders;

import Exceptions.RecordCannotBeAdded;
import FileHandling.MembersFile;
import DataStorage.Cars;

import static OperationHelpers.NumericInputFromConsole.readIntFromConsoleWithPrompt;

public class CarIDReaderMenu {

    //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.

    /**
     * Menu to record the barcode and registration from user input to the appropriate ID readers
     * <p>The menu gives the option of reading in a barcode or a registration number. The barcode or registration number is then checked
     * against a hashtable of car park members. If it is found, the matching barcode or registration is retrieved, if it is not found the missing
     * barcode or registration is read in and the vehicle is 'signed up' and added to the car park members list.</p>
     * <p>The method is designed to be called from while loop, so the user can exit the menu by selecting option 3 which returns a 'false' </p>
     * @param barcodeReader
     * @param regReader
     * @param carMembers - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @param carMembersFile - Instance of MembersFile - static data storage acting as a back-up log of the dynamic data storage of carMembers.
     * @return boolean - returns 'false' if the user chooses to exit the menu or records valid barcode and registration values. Returns 'true' otherwise.
     */
    public static boolean IDReaderMenu(IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, MembersFile carMembersFile) {
        int menuOption = readIntFromConsoleWithPrompt("\n----------ID Reader Menu----------"+
                "\nPlease choose an option: " +
                "\n1) Read vehicle barcode" +
                "\n2) Read vehicle registration" +
                "\n3) Exit to continue operating car park" +
                "\n Please select an option: ");

        switch (menuOption) {
            case 1:
                barcodeReader.readID();
                getRegistrationForBarcodeAndAddMemberIfNotExists(barcodeReader, regReader, carMembers, carMembersFile);
                return false;

            case 2:
                regReader.readID();
                getBarcodeForRegistrationAndAddMemberIfNotExists(barcodeReader, regReader, carMembers, carMembersFile);
                return false;

            case 3:
                System.out.printf("You are exiting the ID Reader menu.\n");
                return false;

            default:
                System.out.printf("No valid option selected.\n");
                break;
        }
        return true;
    }


    /**
     * Method to retrieve the vehicle registration associated with the input barcode from the carMembers hashtable of car park members
     *
     * <p>If the vehicle is a car park member, the barcode is used to retrieve the associated registration from the hashtable of car park members</p>
     *
     * <p>If the vehicle is not a car park member, the vehicle is 'signed up' and a registration is read in using the regReader. If this is completed successfully,
     * the barcode and registration for the vehicle are added to the hashtable of car park members.</p>
     * @param barcodeReader
     * @param regReader
     * @param carMembers - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @param carMembersFile - Instance of MembersFile - static data storage acting as a back-up log of the dynamic data storage of carMembers.
     */
    public static void getRegistrationForBarcodeAndAddMemberIfNotExists (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, MembersFile carMembersFile) {
        if(checkIfIsCarParkMember(barcodeReader,regReader, carMembers)) {
            String registration = carMembers.getRegistrationByBarcode(barcodeReader.getID());

            System.out.println("We have retrieved the registration number " + registration + " from the car park members list for barcode " + barcodeReader.getID());
            regReader.setID(registration);
        }
        else
        {
            System.out.println("We do not have an account on record for this barcode." +
                    "\n [take payment if needed, and member will then be signed up]");

            System.out.printf("\nPlease provide details to finalise car park membership.");
            regReader.readID();
            try {
                carMembers.add(barcodeReader.getID(), regReader.getID());
                carMembersFile.addMember(barcodeReader.getID(), regReader.getID());
            }
            catch (RecordCannotBeAdded e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to retrieve the barcode associated with the input registration from the carMembers hashtable of car park members
     *
     * <p>If the vehicle is a car park member, the registration is used to retrieve the associated barcode from the hashtable of car park members</p>
     *
     * <p>If the vehicle is not a car park member, the vehicle is 'signed up' and a barcode is read in using the barcodeReader. If this is completed successfully,
     * the barcode and registration for the vehicle are added to the hashtable of car park members.</p>
     * @param barcodeReader
     * @param regReader
     * @param carMembers - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @param carMembersFile - Instance of MembersFile - static data storage acting as a back-up log of the dynamic data storage of carMembers.
     */
    public static void getBarcodeForRegistrationAndAddMemberIfNotExists (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, MembersFile carMembersFile) {
        if(checkIfIsCarParkMember(barcodeReader,regReader, carMembers)) {
            String barcode = carMembers.getBarcodeFromVehicleReg(regReader.getID());

            System.out.println("We have retrieved the barcode " + barcode + " from the car park members list for registration number " + regReader.getID());
            barcodeReader.setID(barcode);
        }
        else {
            System.out.println("We do not have an account on record for this registration." +
                    "\n [take payment if needed, and member will then be signed up]");

            System.out.printf("\nPlease provide details to finalise car park membership.");
            barcodeReader.readID();
            try {
                carMembers.add(barcodeReader.getID(), regReader.getID());
                carMembersFile.addMember(barcodeReader.getID(), regReader.getID());
            }
            catch (RecordCannotBeAdded e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to check if the barcode or registration recorded in the ID Readers are registered car park members
     * <p>The registration and barcode are checked against the hashtable in carMembers which contains dynamic data storage of current car park members.</p>
     * @param barcodeReader
     * @param regReader
     * @param carMembers - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @return boolean - returns true if the registration or barcode recorded in the ID readers belong to registered car park members as checked against the hashtable in carMembers
     */
    public static boolean checkIfIsCarParkMember (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers) {
        if (carMembers.vehicleIsFoundByReg(regReader.getID()) || carMembers.vehicleIsFoundByBarcode(barcodeReader.getID())) {
            System.out.println("Confirmed as car park member.");
            return true;
        }
        else {
            return false;
        }
    }

}

