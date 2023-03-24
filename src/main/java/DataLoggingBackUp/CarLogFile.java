package DataLoggingBackUp;

import DataStorage.Cars;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    @Override
    public void addRecord(String barcode, String registration) {

        // TODO create file if not exist
        Date date = new Date();

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
            writer.append(barcode + "," + registration + "," + new Timestamp(date.getTime()) + "," + "IN");
            writer.append("\n");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printFileToConsole() {

    }

    //TODO
//    public Cars restoreCarHashFromFile (String filename) {
//
//    }
}
