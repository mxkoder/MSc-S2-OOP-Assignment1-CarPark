import Exceptions.RecordCannotBeAdded;
import FileHandling.CarLogFile;
import DataStorage.Cars;
import FileHandling.MembersFile;
import Models.*;

import java.util.Scanner;

import static InputOutput.ConsoleDialogue.pollCarParkSensor;
import static InputOutput.NumericInputFromConsole.readIntFromConsoleWithPrompt;
import static NavigationMenus.CarIDReaderMenu.IDReaderMenu;

public class MainCarParkDemo {

    private static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args) {

        //************************************************************************************
        //-------Car Park Setup---------------------------------------------------------------

        //-------Car park
        CarPark carPark = new CarPark(5, 5);

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
        BarrierEntry entryBarrier = new BarrierEntry();
        BarrierExit exitBarrier = new BarrierExit();
        FullSign fullSign = new FullSign(false);


        //************************************************************************************
        //-------Car Park Operation and Main Menu---------------------------------------------------------------

        boolean runMainMenu = true;
        while(runMainMenu) {
            int menuOption = readIntFromConsoleWithPrompt("\n----------Main Car Park Menu----------" +1
                    "\nPlease choose an option: " +
                    "\n\nPrimary project function:" +
                    "\n1) Operate car park checking for cars arriving and departing" +
                    "\nAdditional options:" +
                    "\n2) Print list of cars currently in Car Park to console" +
                    "\n3) Print list of car park members to console" +
                    "\n4) Print car park log file to console" +
                    "\n5) Restore live list of cars in Car Park from log file: " + carParkLogFile.getFileName() +
                    "\n6) Restore live list of Car Park members from members file: " + carMembersFile.getFileName() +
                    "\n7) Exit program" +
                    "\n Please select an option: ");

            switch (menuOption) {
                case 1:
                    // Core method which updates the car park, taking in input for the sensors and ID readers from the console
                    while(operateCarParkUntilChooseSessionEnd(carPark, entrySensor, exitSensor, barcodeReader, regReader, carMembers, carsInCarPark, entryBarrier, exitBarrier, fullSign, carParkLogFile));

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
                    carParkLogFile.populateHashFromFile(carsInCarPark);
                    break;
                case 6:
                    carMembersFile.populateHashFromFile(carMembers);
                    break;
                case 7:
                    System.out.printf("You are exiting the program.\n");
                    runMainMenu = false;
                    break;
                default:
                    System.out.printf("No valid option selected.\n");
                    break;
            }
        }
    }

    public static boolean operateCarParkUntilChooseSessionEnd (CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, BarrierEntry entryBarrier, BarrierExit exitBarrier, FullSign fullSign, CarLogFile carParkLogFile) {
        String choice;

        System.out.printf("\nWould you like to continue to operate the car park and poll the exit and entrance? \n");

        while(true){
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch(choice){
                case "y":
                    System.out.printf("You have chosen to continue to operate the car park. \n");
                    update(carPark, entrySensor, exitSensor, barcodeReader, regReader, carMembers, carsInCarPark, entryBarrier, exitBarrier, fullSign, carParkLogFile);
                    return true;
                case "n":
                    System.out.printf("You have chosen exit back to the main menu. \n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }
        }
    }

    public static void update(CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, BarrierEntry entryBarrier, BarrierExit exitBarrier, FullSign fullSign, CarLogFile carParkLogFile) {

        //TODO - in update method, check only increment if barrier is raised

        //---------------Poll entrance and update car park--------------------------------------
        boolean carDetectedAtEntrance = pollCarParkSensor(entrySensor);

        if (carDetectedAtEntrance) {

            //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

            //TODO optional add if have reg and barcode, then.. otherwise: incomplete details -> go back to id reader menu
            if(carPark.getSpacesAvailable() > 0) {

                try {
                    System.out.println("\nCar park " + entrySensor.getSensorLocation() + " update:");
                    carsInCarPark.add(barcodeReader.getID(), regReader.getID());
                    carParkLogFile.recordArrival(barcodeReader.getID(), regReader.getID());

                    entryBarrier.raise();
                    carPark.decrementSpacesAvailable();
                    System.out.println("Vehicle enters car park.");
                    entryBarrier.lower();

                } catch (RecordCannotBeAdded e) {
                    e.printStackTrace();
                }

            }
            else {
                fullSign.update(carPark.getSpacesAvailable());
                System.out.println("Car park is full, please come back later.");
            }
        }

        resetAndUpdateCarParkDevices(entrySensor, barcodeReader, regReader, fullSign, carPark);

        //---------------Poll exit and update car park--------------------------------------
        boolean carDetectedAtExit = pollCarParkSensor(exitSensor);

        if (carDetectedAtExit) {
            // All exiting cars should already be members of the car park
            //TODO optional- could add barrier as input for IDReader menu - if sensor type is exit: problem if not member
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

            if(carsInCarPark.vehicleIsFoundByBarcode(barcodeReader.getID()) && carsInCarPark.vehicleIsFoundByReg(regReader.getID())) {

                System.out.println("\nCar park " + exitSensor.getSensorLocation() + " update:");
                carsInCarPark.remove(barcodeReader.getID(), regReader.getID());
                carParkLogFile.recordDeparture(barcodeReader.getID(), regReader.getID());

                exitBarrier.raise();
                carPark.incrementSpacesAvailable();
                System.out.println("Vehicle leaves car park.");
                exitBarrier.lower();
            }
            else
            {
                System.out.println("We do not have a record of this vehicle entering the car park. \n"
                        + "<Manual resolution of issue by attendant and parking payment needs to be taken.>");
            }
        }

        resetAndUpdateCarParkDevices(entrySensor, barcodeReader, regReader, fullSign, carPark);

        //TODO or have this as a 'to string' method??
        System.out.println("\nAt the end of the update cycle, the car park status is: "
                + "\n- Number of vehicles in car park: " + carsInCarPark.numberOfRecords()
                + "\n- Car park spaces available: " + carPark.getSpacesAvailable()
                + "\n- Car park capacity: " + carPark.getCapacity()
                + "\n- Number of car park members: " + carMembers.numberOfRecords());
        //TODO - optional add counter: x cars have entered and x cars have left the car park during this session
    };

    public static void resetAndUpdateCarParkDevices (CarParkSensor entrySensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, FullSign fullSign, CarPark carPark) {
        System.out.println("\n----------Resetting car park devices----------");
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();
        fullSign.update(carPark.getSpacesAvailable());
        System.out.println("\n-----------------------------------------------");
    }


}

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