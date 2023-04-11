import CarPark.CarPark;
import Barriers.CarParkBarrier;
import Exceptions.InvalidAvailabilityAndCapacityException;
import Sensors.CarParkSensor;
import FileHandling.CarLogFile;
import DataStorage.Cars;
import FileHandling.MembersFile;
import IDReaders.IDReaderBarcode;
import IDReaders.IDReaderRegistration;
import CarPark.FullSign;

import java.util.Scanner;

import static CarPark.CarParkOperation.printCarParkStatus;
import static OperationHelpers.NumericInputFromConsole.readIntFromConsoleWithPrompt;
import static OperationHelpers.ConsoleDialogue.operateCarParkUntilChooseSessionEnd;

public class MainCarParkDemo {

    private static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args) {

        //************************************************************************************
        //-------Car Park Setup---------------------------------------------------------------

        //-------Car park

        //Option 1: Start with an empty car park
        CarPark carPark = new CarPark(100);

        try {
            carPark.setSpacesAvailable(100);
        }
        catch (InvalidAvailabilityAndCapacityException e) {
            e.printStackTrace();
        }

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
        ////Optional - uncomment line below to run Car Park with a starting populated hashtable of carsInCarPark
        ////Note: will also need to have some records in the carLogFile.csv to populate the carsInCarPark with.
        //carParkLogFile.populateHashFromFile(carsInCarPark, carPark);

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

        /**
         * Main Car Park Menu
         * <p>Gives users the options to perform standard car park operation (polling entrance and exit sensors and recording vehicle arrivals and departures),
         * or additional functions</p>
         * <p>The additional options include printing dynamic and static data storage contents to the console and printing the car park status to the console</p>
         * <p>There is also the option to restore dynamic data storage of vehicles in the car park or car park members from the static data storage in the log files</p>
         */
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
}