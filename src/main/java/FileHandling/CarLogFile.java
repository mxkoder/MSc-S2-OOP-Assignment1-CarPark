package FileHandling;

import DataStorage.Cars;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;
import Models.CarPark;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import static FileHandling.MembersFile.checkIfWantToClearCurrentDataBeforeRestoringFromFile;

public class CarLogFile implements LogFile {

    private static Scanner stdin = new Scanner(System.in);

    private String carLogFileName;

    public CarLogFile(String carLogFileName) {
        this.carLogFileName = carLogFileName;
    }

    @Override
    public void createLogFile() {
        try {
            File myfile = new File(carLogFileName);

            if(myfile.createNewFile())
            {
                System.out.println("A file was created to keep a log of cars in the car park, with filename: " + carLogFileName);
            }
            else {
                System.out.println("A log file for cars in the car park already exists with name " + carLogFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName() {
        return this.carLogFileName;
    }

    //TODO - make sure it can write to a CSV file
    // TODO - add time stamps
    public void recordArrival(String barcode, String registration) {

        // TODO create file if not exist - or leave - will be caught by IO exception
        Date date = new Date();

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
            writer.append("IN" + "," + barcode + "," + registration + "," + new Timestamp(date.getTime()));
            writer.append("\n");
            writer.close();

            System.out.println("Arrival of vehicle was recorded in the log file with barcode: " + barcode + " and registration: " + registration);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO optional refactor combine the similar add and remove methods
    public void recordDeparture(String barcode, String registration) {
        Date date = new Date();

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
            writer.append("OUT" + "," + barcode + "," + registration + "," + new Timestamp(date.getTime()));
            writer.append("\n");
            writer.close();

            System.out.println("Departure of vehicle was recorded in the log file with barcode: " + barcode + " and registration: " + registration);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printFileToConsole() {
        System.out.println("\n ---------Printout of car park log file with name: " + carLogFileName + "---------");

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO add docs - in, out - will end up with a restored live record, cars that have entered & then left not included, add that updates car park spaces available
    public void populateHashFromFile (Cars dataStorageToPopulate, CarPark carPark) {

        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if(elements.length > 0) {

                    if(elements[0].equals("IN")) {
                        try {
                            dataStorageToPopulate.add(elements[1], elements[2]);
                            carPark.decrementSpacesAvailable();
                        }
                        catch (RecordCannotBeAdded | IsFull e) {
                            e.printStackTrace();
                        }

                    }
                    else if (elements[0].equals("OUT")) {
                        try {
                            dataStorageToPopulate.remove(elements[1], elements[2]);
                            carPark.incrementSpacesAvailable();
                        }
                        catch (VehicleAtExitWasNotRecordedEntering e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        System.out.println("Invalid record in log file.");
                    }
                }
            }
            System.out.println("The file " + carLogFileName + " was used to populate the hashtable, adding only unique barcodes and registration numbers.");

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreDataFromFile(Cars dataStorageToRestore, CarPark carPark) {
        checkIfWantToClearCurrentDataBeforeRestoringFromFile(dataStorageToRestore);
        populateHashFromFile(dataStorageToRestore, carPark);
    }

    public boolean clearFileContents() {
        String choice;

        System.out.printf("You are choosing to clear the contents of log file: " + carLogFileName + " would you like to proceed?\n");

        while(true){
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch(choice){
                case "y":
                    System.out.printf("You have chosen clear the contents of the log file. \n");

                    try {
                        File file = new File(carLogFileName);

                        FileWriter fileWriter = new FileWriter(file);

                        fileWriter.write("");
                        fileWriter.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                case "n":
                    System.out.printf("You have chosen not to clear the contents of the log file. \n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }
        }
    }

}
