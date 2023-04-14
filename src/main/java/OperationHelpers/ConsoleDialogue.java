package OperationHelpers;

import CarPark.CarPark;
import Barriers.CarParkBarrier;
import Sensors.CarParkSensor;
import DataStorage.Cars;
import FileHandling.CarLogFile;
import FileHandling.MembersFile;
import IDReaders.IDReaderBarcode;
import IDReaders.IDReaderRegistration;
import CarPark.FullSign;

import java.util.Scanner;

import static CarPark.CarParkOperation.update;

public class ConsoleDialogue {
    private static Scanner stdin = new Scanner(System.in);

    /**
     * Method to provide console dialogue with the user to operate the car park (polling at entrance and exit) until the user chooses to end the session
     * <p>The user is asked to enter y or n to choose to continue operating the car park. If they choose to continue to operate the car park,
     * the CarPark.CarParkOperation.update is called which polls cars at the entrance and exit and updates the car park, dynamic data storage, and log files accordingly</p>
     * <p>The method is designed to be called in a while loop, so if the user chooses the 'n' option the method will be exited.</p>
     *
     * @param carPark        - Instance of CarPark with capacity and number of spaces available
     * @param entrySensor
     * @param exitSensor
     * @param barcodeReader
     * @param regReader
     * @param carMembers     - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @param carsInCarPark  - Instance of Cars: dynamic data storage containing a hashtable of cars currently in the car park with their barcodes and registration numbers.
     * @param entryBarrier
     * @param exitBarrier
     * @param fullSign
     * @param carParkLogFile - Instance of CarLogFile - static data storage acting as a back-up log of the dynamic data storage of carsInCarPark.
     * @param carMembersFile - Instance of MembersFile - static data storage acting as a back-up log of the dynamic data storage of carMembers.
     * @return boolean - returns false when the user choose to end the session and stop updating the car park.
     */
    public static boolean operateCarParkUntilChooseSessionEnd(CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, CarParkBarrier entryBarrier, CarParkBarrier exitBarrier, FullSign fullSign, CarLogFile carParkLogFile, MembersFile carMembersFile) {
        String choice;

        System.out.printf("\nWould you like to continue to operate the car park and poll the exit and entrance? \n");

        while (true) {
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch (choice) {
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

    /**
     * Method to check car park sensors taking user input using the Scanner class
     * <p>The method asks the user to enter a 'y' or 'n' response depending on if a vehicle has been detected by the sensor.
     * The sensor will be set to according to the user response, and a 'true' return from the method will indicate that a vehicle has been detected </p>
     * <p>The dialogue will stay in the cycle until the user enters a valid 'y' or 'n' response.</p>
     *
     * @param sensor - Instance of CarParkSensor. Can be an entrance or an exit sensor, or another location.
     * @return boolean - returns true if a vehicle has been detected at the sensor, and false if no vehicle has been detected.
     */
    public static boolean pollCarParkSensor(CarParkSensor sensor) {
        String choice;

        System.out.printf("\nIs there a car at the " + sensor.getSensorLocation() + " barrier? \n");

        while (true) {
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch (choice) {
                case "y":
                    sensor.setSensor(true);
                    System.out.printf("The sensor at the " + sensor.getSensorLocation() + " barrier has been updated as detecting a car.\n");
                    return true;
                case "n":
                    sensor.setSensor(false);
                    System.out.printf("The sensor at the " + sensor.getSensorLocation() + " barrier has been updated as not detecting a car.\n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }

        }
    }
}
