import FileHandling.CarLogFile;
import DataStorage.Cars;
import FileHandling.MembersFile;
import Models.*;
import java.util.Scanner;

import static OperationHelpers.CarParkOperation.printCarParkStatus;
import static OperationHelpers.CarParkOperation.update;
import static InputOutput.NumericInputFromConsole.readIntFromConsoleWithPrompt;

public class MainCarParkDemo {

    private static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args) {

        //************************************************************************************
        //-------Car Park Setup---------------------------------------------------------------

        //-------Car park

        //Option 1: Start with an empty car park
        CarPark carPark = new CarPark(100);

        //Option 2: Start with a near full car park
//        try {
//            carPark.setSpacesAvailable(2);
//        }
//        catch (InvalidAvailabilityAndCapacityException e) {
//            e.printStackTrace();
//        }

        //-------Setup car park log file
        CarLogFile carParkLogFile = new CarLogFile("carLogFile.csv");
        carParkLogFile.createLogFile(); // Will create a file if no file exists.

        //-------Setup data storage for cars in car park
        Cars carsInCarPark = new Cars();
        //TODO if want to run file w some cars already in the car park, need to run a .populate here

        //-------Setup car park members list
        Cars carMembers = new Cars();
        MembersFile carMembersFile = new MembersFile("car_park_members.csv");
        carMembersFile.createLogFile(); // Will create a file if no file exists.
        // Note: this project is starting with a dummy car park members list generated using Mockaroo ( https://mockaroo.com/ )
        carMembersFile.populateHashFromFile(carMembers);
        // [car park members hash table can be populated from a .csv file with lines in the format : "12digitBarcode, StandardUKRegistration"]

        //-------Setup car park devices
        CarParkSensor entrySensor = new CarParkSensor(false, "entrance");
        CarParkSensor exitSensor = new CarParkSensor(false, "exit");
        IDReaderBarcode barcodeReader = new IDReaderBarcode("");
        IDReaderRegistration regReader = new IDReaderRegistration("");
        CarParkBarrier entryBarrier = new CarParkBarrier("entrance");
        CarParkBarrier exitBarrier = new CarParkBarrier("exit");
        FullSign fullSign = new FullSign(false);


        //************************************************************************************
        //-------Car Park Operation and Main Menu---------------------------------------------------------------

        boolean runMainMenu = true;
        while(runMainMenu) {
            int menuOption = readIntFromConsoleWithPrompt("\n----------Main Car Park Menu----------" +
                    "\nPlease choose an option: " +
                    "\n\nPrimary project function:" +
                    "\n1) Operate car park checking for cars arriving and departing" +
                    "\nAdditional options:" +
                    "\n2) Print list of cars currently in Car Park to console" +
                    "\n3) Print list of car park members to console" +
                    "\n4) Print car park log file to console" +
                    "\n5) Restore live list of cars in Car Park from log file: " + carParkLogFile.getFileName() +
                    "\n6) Restore live list of Car Park members from members file: " + carMembersFile.getFileName() +
                    "\n7) Clear the contents of log file: " + carParkLogFile.getFileName() +
                    "\n8) Print Car Park status" +
                    "\n9) Exit program" +
                    "\n Please select an option: ");

            switch (menuOption) {
                case 1:
                    // Core method which updates the car park, taking in input for the sensors and ID readers from the console
                    while(operateCarParkUntilChooseSessionEnd(carPark, entrySensor, exitSensor, barcodeReader, regReader, carMembers, carsInCarPark, entryBarrier, exitBarrier, fullSign, carParkLogFile, carMembersFile));

                    break;
                case 2:
                    System.out.println("----------Printout of cars currently in the Car Park----------");
                    carsInCarPark.printAll();
                    break;
                case 3:
                    System.out.println("----------Printout of current Car Park members----------");
                    carMembers.printAll();
                    break;
                case 4:
                    carParkLogFile.printFileToConsole();
                    break;
                case 5:
                    carParkLogFile.restoreDataFromFile(carsInCarPark, carPark);
                    break;
                case 6:
                    carMembersFile.restoreDataFromFile(carMembers);
                    break;
                case 7:
                    carParkLogFile.clearFileContents();
                    break;
                case 8:
                    printCarParkStatus(carsInCarPark, carPark, carMembers);
                    break;
                case 9:
                    System.out.printf("You are exiting the program.\n");
                    runMainMenu = false;
                    break;
                default:
                    System.out.printf("No valid option selected.\n");
                    break;
            }
        }
    }

    public static boolean operateCarParkUntilChooseSessionEnd (CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, CarParkBarrier entryBarrier, CarParkBarrier exitBarrier, FullSign fullSign, CarLogFile carParkLogFile, MembersFile carMembersFile) {
        String choice;

        System.out.printf("\nWould you like to continue to operate the car park and poll the exit and entrance? \n");

        while(true){
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch(choice){
                case "y":
                    System.out.printf("You have chosen to continue to operate the car park. \n");
                    update(carPark, entrySensor, exitSensor, barcodeReader, regReader, carMembers, carsInCarPark, entryBarrier, exitBarrier, fullSign, carParkLogFile, carMembersFile);
                    return true;
                case "n":
                    System.out.printf("You have chosen exit back to the main menu. \n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }
        }
    }
}


//TODO make sure delete this when not needed
//-------------------initial demo run-----------------------------------------
////        IDReaderBarcode barcodeReader = new IDReaderBarcode();
////
////        barcodeReader.readID();
//
////        //----------------reg reader----------
////        // TODO - test reg plate ID reader
////        IDReaderRegistration regReader = new IDReaderRegistration();
////
////        regReader.readID();
////
////        System.out.println("reg plate recorded is: " + regReader.getID());
//
//        //--------------- log file
//
//        CarLogFile carLog1 = new CarLogFile("carLog1.csv");
//        carLog1.createLogFile();
//
//        //------------cars list----------------------------------
//        Cars cars = new Cars();
//
//        cars.add("768886", "thy7hj");
//        carLog1.recordArrival("768886", "thy7hj");
//
//        cars.add("123", "abc");
//        carLog1.recordArrival("123", "abc");
//
//        cars.add("7654886", "tyuy7hj");
//        carLog1.recordArrival("7654886", "tyuy7hj");
//
//        cars.add("7872030886", "tyuy7abcggehj");
//        carLog1.recordArrival("7872030886", "tyuy7abcggehj");
//
//        cars.add("76521246", "tyurety");
//        carLog1.recordArrival("76521246", "tyurety");
//
//        cars.printAll();
//
//        cars.remove("123", "abc");
//        carLog1.recordDeparture("123", "abc");
//
//        cars.printAll();
////
////        System.out.println("search for barcode, present: " + cars.checkVehiclePresentByBarcode("768886"));
////        System.out.println("search for barcode,  not present: " + cars.checkVehiclePresentByBarcode("778687686868886"));
////
////        System.out.println("search for reg, present: " + cars.checkVehiclePresentByReg("thy7hj"));
////        System.out.println("search for reg, not present: " + cars.checkVehiclePresentByReg("thy7gyyjbhj"));
////
////        cars.deleteVehicles();
////
////        System.out.println("vehicle list is now: " );
////        cars.printVehicles();
//
//        //--------------check log file
//
//        carLog1.printFileToConsole();
//
//        Cars restoredCars = new Cars();
//
//
//        carLog1.populateHashFromFile(restoredCars);
//
//
//        //------------comparison check of restored records and previous records-------------
//        cars.printAll();
//        restoredCars.printAll();


//        //-------------Check Member's list--------------------------------------------------
//
//        Cars carMembers = new Cars();
//
//        MembersFile carMembersFile = new MembersFile("car_park_members.csv");
//
//        // carMembersFile.createLogFile();
//
//        carMembersFile.populateHashFromFile(carMembers);
//
//        carMembers.printAll();
//
//        System.out.println();
//        System.out.println(carMembers.getRegistrationByBarcode("493521756156")); // expect "kg09zdi"
//        System.out.println();
//        System.out.println(carMembers.getBarcodeFromVehicleReg("iw40fgr"));  //547981781688,iw40fgr