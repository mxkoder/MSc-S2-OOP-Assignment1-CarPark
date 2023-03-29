package FileHandling;

import DataStorage.Cars;

public interface LogFile {

    void createLogFile ();

    String getFileName ();

    void printFileToConsole ();

    void populateHashFromFile (Cars hashTableFromFile);

}
