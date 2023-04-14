package FileHandling;

import DataStorage.Cars;
import Exceptions.RecordCannotBeAdded;

import java.io.*;
import java.util.Scanner;

public class MembersFile implements LogFile {

    private static Scanner stdin = new Scanner(System.in);

    private String membersFileName;

    public MembersFile(String membersFileName) {
        this.membersFileName = membersFileName;
    }

    /**
     * Method to create a file which will be used for logging data
     * <P>The file name will be the 'membersFileName' string which is assigned in the constructor. </P>
     * <p>If the file already exists, a message will be printed to the console informing the user.</p>
     */
    @Override
    public void createLogFile() {
        try {
            File myfile = new File(membersFileName);

            if (myfile.createNewFile()) {
                System.out.println("A file was created to keep track of car park members, with filename: " + membersFileName);
            } else {
                System.out.println("A car park members list file already exists with name " + membersFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName() {
        return this.membersFileName;
    }

    /**
     * Method to record the addition of a car park member in the data logging file.
     * <p>The method will record a comma seperated line in the file with the vehicle barcode and registration on one line.</p>
     * <p>A confirmation message will be printed to the console indicating that the barcode - registration dataset pair was recorded in the logfile.</p>
     *
     * @param barcode      String - a 12 digit barcode value
     * @param registration String - a standard UK vehicle registration
     */
    public void addMember(String barcode, String registration) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(membersFileName, true));
            writer.append(barcode + "," + registration);
            writer.append("\n");
            writer.close();

            System.out.println("New member was added to the Member's file with barcode: " + barcode + " and registration: " + registration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to print the contents of the data logging file to the console
     */
    @Override
    public void printFileToConsole() {
        System.out.println("\n ---------Printout of car park members list file with name: " + membersFileName + "---------");

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(membersFileName));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to restore the dynamic data storage used for car park members from the information recorded in the data logging file
     * <p>The method will add any barcode and registration string pairs recorded in the data logging file to the hashtable of car park
     * member in the dataStorageToPopulate input parameter.</p>
     * <p>The vehicle registration is mapped to uppercase when it is added to the car park members hashtable</p>
     * <p>A confirmation message is printed to the console when the method is complete, in addition to the console message for each addition to the
     * hashtable from the 'add' method. </p>
     *
     * @param dataStorageToPopulate - Instance of Cars, which contains the dynamic data storage of car park members which needs to be populated by the method
     */
    public void populateHashFromFile(Cars dataStorageToPopulate) {

        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(membersFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if (elements.length >= 2) {
                    try {
                        dataStorageToPopulate.add(elements[0], elements[1].toUpperCase());
                    } catch (RecordCannotBeAdded e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("The file " + membersFileName + " was used to populate the hashtable. Only members not already in the live list of members will be added.");

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
     * @param dataStorageToRestore - Instance of Cars, which contains the dynamic data storage of car park members which needs to be populated by the method from the logfile
     */
    public void restoreDataFromFile(Cars dataStorageToRestore) {
        checkIfWantToClearCurrentDataBeforeRestoringFromFile(dataStorageToRestore);
        populateHashFromFile(dataStorageToRestore);
    }

    /**
     * Method to be used within restoreDataFromFile to check if the user wants to delete the contents of the input parameter hashtable,
     * and deletes the contents of the hashtable if they choose that option
     *
     * @param dataStorageToRestore - Instance of Cars, which contains the dynamic data storage to be cleared
     */
    public static boolean checkIfWantToClearCurrentDataBeforeRestoringFromFile(Cars dataStorageToRestore) {
        String choice;

        System.out.printf("Would you like to clear current data in the live record before restoring from the backup file? \n");

        while (true) {
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch (choice) {
                case "y":
                    System.out.printf("You have chosen to clear the current live record before restoring from the back up file. \n");
                    dataStorageToRestore.deleteAll();
                    return true;
                case "n":
                    System.out.printf("You have chosen not clear the current live record of data before restoring from the back up file. \n");
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
            File file = new File(membersFileName);

            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
