package NavigationMenus;

import Exceptions.RecordCannotBeAdded;
import Models.CarParkSensor;
import Models.IDReaderBarcode;
import Models.IDReaderRegistration;
import DataStorage.Cars;

import static InputOutput.NumericInputFromConsole.readIntFromConsoleWithPrompt;

public class CarIDReaderMenu {

    //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.
    public static boolean IDReaderMenu(IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers) {
        int menuOption = readIntFromConsoleWithPrompt("\n----------ID Reader Menu----------"+
                "\nPlease choose an option: " +
                "\n1) Read vehicle barcode" +
                "\n2) Read vehicle registration" +
                "\n3) Exit to continue operating car park" +
                "\n Please select an option: ");
        //TODO - optional add in if statement to check if already recorded reg and barcode id?

        switch (menuOption) {
            case 1:
                barcodeReader.readID();
                getRegistrationForBarcodeAndAddMemberIfNotExists(barcodeReader, regReader, carMembers);

                break;
            case 2:
                regReader.readID();
                getBarcodeForRegistrationAndAddMemberIfNotExists(barcodeReader, regReader, carMembers);

                break;
            case 3:
                System.out.printf("You are exiting the ID Reader menu.\n");
                return false;
            default:
                System.out.printf("No valid option selected.\n");
                break;
        }
        return true;
    }


    public static void getRegistrationForBarcodeAndAddMemberIfNotExists (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers) {
        if(checkIfISCarParkMember(barcodeReader,regReader, carMembers)) {
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
            }
            catch (RecordCannotBeAdded e) {
                e.printStackTrace();
            }
        }
    }

    public static void getBarcodeForRegistrationAndAddMemberIfNotExists (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers) {
        if(checkIfISCarParkMember(barcodeReader,regReader, carMembers)) {
            String barcode = carMembers.getBarcodeFromVehicleReg(barcodeReader.getID());

            System.out.println("We have retrieved the barcode " + barcode + " from the car park members list for registration number " + regReader.getID());
            regReader.setID(barcode);
        }
        else {
            System.out.println("We do not have an account on record for this registration." +
                    "\n [take payment if needed, and member will then be signed up]");

            System.out.printf("\nPlease provide details to finalise car park membership.");
            barcodeReader.readID();
            try {
                carMembers.add(barcodeReader.getID(), regReader.getID());
            }
            catch (RecordCannotBeAdded e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfISCarParkMember (IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers) {
        if (carMembers.vehicleIsFoundByReg(regReader.getID()) || carMembers.vehicleIsFoundByBarcode(barcodeReader.getID())) {
            System.out.println("Confirmed as car park member.");
            return true;
        }
        else {
            return false;
        }
    }

}

