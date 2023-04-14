package FileHandling;

import DataStorage.Cars;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;
import CarPark.CarPark;

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

    /**
     * Method to create a file which will be used for logging data
     * <P>The file name will be the 'carLogFileName' string which is assigned in the constructor. </P>
     * <p>If the file already exists, a message will be printed to the console informing the user.</p>
     */
    @Override
    public void createLogFile() {
        try {
            File myfile = new File(carLogFileName);

            if (myfile.createNewFile()) {
                System.out.println("A file was created to keep a log of cars in the car park, with filename: " + carLogFileName);
            } else {
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

    /**
     * Method to record the arrival of a vehicle at the car park in the data logging file.
     * <p>The method will record a comma seperated line in the file with the text 'IN' to indicate the vehicle is arriving, the vehicle barcode, registration,
     * and date timestamp</p>
     * <p>A confirmation message will be printed to the console indicating that the barcode - registration dataset pair was recorded in the logfile.</p>
     *
     * @param barcode      String - a 12 digit barcode value
     * @param registration String - a standard UK vehicle registration
     */
    public void recordArrival(String barcode, String registration) {

        Date date = new Date();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
            writer.append("IN" + "," + barcode + "," + registration + "," + new Timestamp(date.getTime()));
            writer.append("\n");
            writer.close();

            System.out.println("Arrival of vehicle was recorded in the log file with barcode: " + barcode + " and registration: " + registration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to record the departure of a vehicle from the car park
     * <p>The method will record a comma seperated line in the file with the text 'OUT' to indicate the vehicle is departing, the vehicle barcode, registration,
     * and date timestamp on one line</p>
     * <p>A confirmation message will be printed to the console indicating that the barcode - registration dataset pair was recorded in the logfile.</p>
     *
     * @param barcode      String - a 12 digit barcode value
     * @param registration String - a standard UK vehicle registration
     */
    public void recordDeparture(String barcode, String registration) {
        Date date = new Date();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
            writer.append("OUT" + "," + barcode + "," + registration + "," + new Timestamp(date.getTime()));
            writer.append("\n");
            writer.close();

            System.out.println("Departure of vehicle was recorded in the log file with barcode: " + barcode + " and registration: " + registration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to print the contents of the data logging file to the console
     */
    @Override
    public void printFileToConsole() {
        System.out.println("\n ---------Printout of car park log file with name: " + carLogFileName + "---------");

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to restore the dynamic data storage used by the car park from the information recorded in the data logging file
     * <p>The method will add any barcode and registration string pairs recorded in the data logging file as having arrived,
     * and remove any which are recorded as having departed. The result is that only vehicles still in the car park will be
     * added to the input parameter dataStorageToPopulate.</p>
     * <p>The method also updates the 'spacesAvailable' in the carPark, ensuring that after the hashtable has been restored
     * the number of spaces available in the car park is accurate and the car park can continue operating. An isFull exception will be thrown if the spaces available
     * becomes less than 0.</p>
     * <p>A confirmation message is printed to the console when the method is complete, in addition to the console message for each addition to the
     * hashtable from the 'add' method. </p
     *
     * @param dataStorageToPopulate - Instance of Cars, which contains the dynamic data storage which needs to be populated by the method
     * @param carPark               - Instance of CarPark, which is the relevant car park the data logging file is recording arrivals and departures for
     */
    public void populateHashFromFile(Cars dataStorageToPopulate, CarPark carPark) {

        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if (elements.length > 0) {

                    if (elements[0].equals("IN")) {
                        try {
                            dataStorageToPopulate.add(elements[1], elements[2]);
                            carPark.decrementSpacesAvailable();
                        } catch (RecordCannotBeAdded | IsFull e) {
                            e.printStackTrace();
                        }

                    } else if (elements[0].equals("OUT")) {
                        try {
                            dataStorageToPopulate.remove(elements[1], elements[2]);
                            carPark.incrementSpacesAvailable();
                        } catch (VehicleAtExitWasNotRecordedEntering e) {
                            e.printStackTrace();
                        }

                    } else {
                        System.out.println("Invalid record in log file.");
                    }
                }
            }
            System.out.println("The file " + carLogFileName + " was used to populate the hashtable, adding only unique barcodes and registration numbers.");

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is a variation of the populateHashFromFile, and includes an option for the user to clear the contents of the hashtable
     * before populating it with data from the log file
     * <p>This method can be used in the event of partial dynamic data loss, where the data in the log file may need to be used to replace any
     * data that exists in the dynamic data storage hashtable.</p>
     *
     * @param dataStorageToRestore - Instance of Cars, which contains the dynamic data storage which needs to be populated by the method from the logfile
     * @param carPark              - Instance of CarPark, which is the relevant car park the data logging file is recording arrivals and departures for
     */
    public void restoreDataFromFile(Cars dataStorageToRestore, CarPark carPark) {
        checkIfWantToClearCurrentDataBeforeRestoringFromFile(dataStorageToRestore);
        populateHashFromFile(dataStorageToRestore, carPark);
    }

    /**
     * Method to clear the contents of the logfile
     * <p>Method has console dialogue asking user to confirm deletion, then clears the contents of the log file if the user chooses that option</p>
     *
     * @return boolean - Returns 'true' if the user has opted to clear the contents of the logfile, and 'false' otherwise
     */
    public boolean clearFileContents() {
        String choice;

        System.out.printf("You are choosing to clear the contents of log file: " + carLogFileName + " would you like to proceed?\n");

        while (true) {
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch (choice) {
                case "y":
                    System.out.printf("You have chosen clear the contents of the log file. \n");

                    try {
                        File file = new File(carLogFileName);

                        FileWriter fileWriter = new FileWriter(file);

                        fileWriter.write("");
                        fileWriter.close();
                    } catch (IOException e) {
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

    /**
     * ONLY FOR TEST - method to clear contents of log file
     * Version of the clearFileContents method for testing purposes.
     */
    public void clearFileContentsOnlyForTest() {

        try {
            File file = new File(carLogFileName);

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
