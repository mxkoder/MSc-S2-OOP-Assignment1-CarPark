package FileHandling;

import DataStorage.Cars;

public interface LogFile {

    void createLogFile ();

    void addRecord (String barcode, String registration);

    void removeRecord (String barcode, String registration);

    void printFileToConsole ();

    void createCarHashFromFile (Cars carHashTableFromFile);

}
