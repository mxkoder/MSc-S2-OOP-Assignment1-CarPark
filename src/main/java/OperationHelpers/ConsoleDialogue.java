package OperationHelpers;

import DataStorage.Cars;
import FileHandling.CarLogFile;
import FileHandling.MembersFile;
import Models.*;

import java.util.Scanner;

import static OperationHelpers.CarParkOperation.update;

public class ConsoleDialogue {
    private static Scanner stdin = new Scanner(System.in);

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
