package InputOutput;

import Models.CarPark;
import Models.CarParkSensor;

import java.util.Scanner;

public class ConsoleDialogue {
    private static Scanner stdin = new Scanner(System.in);



    public static boolean pollCarParkSensor(CarParkSensor sensor) {
        String choice;

        System.out.printf("Is there a car at the " + sensor.getSensorLocation() + " barrier? \n");

        while (true) {
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch (choice) {
                case "y":
                    sensor.setSensor(true);
                    System.out.printf("The sensor at the " + sensor.getSensorLocation() + " barrier has been updated detecting a car.\n");
                    return true;
                case "n":
                    sensor.setSensor(false);
                    System.out.printf("The sensor at the " + sensor.getSensorLocation() + " barrier has been updated as not detecting a car.\\n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }

        }
    }
}
