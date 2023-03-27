package FileHandling;

import DataStorage.Cars;

import java.io.*;
import java.sql.Timestamp;

public class MembersFile implements LogFile {

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

    @Override
    public void populateHashFromFile(Cars carHashTableFromFile) {
        try {
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(membersFileName));

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(",");

                if(elements.length > 0) {

                    carHashTableFromFile.add(elements[0], elements[1]);
                }
            }
            System.out.println("The file " + membersFileName + " was used to populate the hashtable.");

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
