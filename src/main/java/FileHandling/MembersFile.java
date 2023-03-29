package FileHandling;

import DataStorage.Cars;
import Exceptions.RecordCannotBeAdded;

import java.io.*;
import java.sql.Timestamp;
import java.util.Scanner;

public class MembersFile implements LogFile {

    private static Scanner stdin = new Scanner(System.in);

    private String membersFileName;

    public MembersFile(String membersFileName) {
        this.membersFileName = membersFileName;
    }

    @Override
    public void createLogFile() {
        try {
            File myfile = new File(membersFileName);

            if(myfile.createNewFile())
            {
                System.out.println("A file was created to keep track of car park members, with filename: " + membersFileName);
            }
            else {
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

    public void addMember(String barcode, String registration) {

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(membersFileName, true));
            writer.append(barcode + "," + registration);
            writer.append("\n");
            writer.close();

            System.out.println("New member was added to the Member's file with barcode: " + barcode + " and registration: " + registration);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void printFileToConsole() {
        System.out.println("\n ---------Printout of car park members list file with name: " + membersFileName + "---------");

        //TODO - section below is repeated, refactor duplication?
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(membersFileName));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void populateHashFromFile(Cars dataStorageToRestore) {

        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(membersFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if(elements.length >= 2) {
                    try {
                        dataStorageToRestore.add(elements[0], elements[1].toUpperCase());
                    }
                    catch (RecordCannotBeAdded e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("The file " + membersFileName + " was used to populate the hashtable. Only members not already in the live list of members will be added.");

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreDataFromFile(Cars dataStorageToRestore) {
        checkIfWantToClearCurrentDataBeforeRestoringFromFile(dataStorageToRestore);
        populateHashFromFile(dataStorageToRestore);
    }

    public static boolean checkIfWantToClearCurrentDataBeforeRestoringFromFile (Cars dataStorage) {
        String choice;

        System.out.printf("Would you like to clear current data in the live record before restoring from the backup file? \n");

        while(true){
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch(choice){
                case "y":
                    System.out.printf("You have chosen to clear the current live record before restoring from the back up file. \n");
                    dataStorage.deleteAll();
                    return true;
                case "n":
                    System.out.printf("You have chosen not clear the current live record of data before restoring from the back up file. \n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }
        }
    }
}
