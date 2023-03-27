package FileHandling;

import DataStorage.Cars;

public interface LogFile {

    void createLogFile ();

    void printFileToConsole ();

    void populateHashFromFile (Cars hashTableFromFile);
}
