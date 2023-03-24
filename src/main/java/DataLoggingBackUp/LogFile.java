package DataLoggingBackUp;

public interface LogFile {

    void createLogFile ();

    void addRecord (String barcode, String registration);

    void printFileToConsole ();


}
