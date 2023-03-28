package FileHandling;

import DataStorage.Cars;
import Exceptions.RecordCannotBeAdded;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class CarLogFile implements LogFile {

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

    //TODO add docs - in, out - will end up with a restored live record, cars that have entered & then left not included
    public void populateHashFromFile (Cars hashTableFromFile) {

        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if(elements.length > 0) {

                    if(elements[0].equals("IN")) {
                        try {
                            hashTableFromFile.add(elements[1], elements[2]);
                        }
                        catch (RecordCannotBeAdded e) {
                            e.printStackTrace();
                        }

                    }
                    else if (elements[0].equals("OUT")) {
                        hashTableFromFile.remove(elements[1], elements[2]);
                    }
                    else {
                        System.out.println("Invalid record in log file.");
                    }
                }
            }
            System.out.println("The file " + carLogFileName + " was used to populate the hashtable.");

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
