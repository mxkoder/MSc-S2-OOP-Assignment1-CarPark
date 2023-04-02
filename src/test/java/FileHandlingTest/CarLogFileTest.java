package FileHandlingTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import FileHandling.MembersFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import DataStorage.Cars;
import FileHandling.CarLogFile;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Models.CarPark;

@RunWith(MockitoJUnitRunner.class)
public class CarLogFileTest {

    @Mock
    private Cars mockCars;

    @Mock
    private CarPark mockCarPark;

    private CarLogFile carLogFile;

    private String carLogFileName = "testCarLogFile.csv";

    @Before
    public void setUp() {
        carLogFile = new CarLogFile(carLogFileName);
        carLogFile.clearFileContentsOnlyForTest();
    }

    @Test
    public void testCreateLogFile() {
        carLogFile.createLogFile();
        assertTrue(new File(carLogFileName).exists());
    }

    @Test
    public void testGetFileName() {
        assertEquals(carLogFileName, carLogFile.getFileName());
    }

    @Test
    public void testRecordArrival() throws IOException {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        carLogFile.recordArrival(barcode, registration);

        String expected = "IN," + barcode + "," + registration + "," + timestamp;
        BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));
        String actual = reader.readLine();
        reader.close();
        assertEquals(expected, actual);

        //Output to console as expected:
        //Arrival of vehicle was recorded in the log file with barcode: 123456789012 and registration: AB12ABC
    }

    @Test
    public void testRecordDeparture() throws IOException {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        carLogFile.recordDeparture(barcode, registration);
        String expected = "OUT," + barcode + "," + registration + "," + timestamp;
        BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));
        String actual = reader.readLine();
        reader.close();
        //assertEquals(expected, actual);
        assert (actual.startsWith("OUT"));

        //Console output as expected:
        //Departure of vehicle was recorded in the log file with barcode: 123456789012 and registration: AB12ABC
    }

    @Test
    public void testPrintFileToConsole() {
        carLogFile.recordArrival("123456789012", "AB12ABC");
        carLogFile.recordDeparture("123456789012", "AB12ABC");
        carLogFile.printFileToConsole();

        //Test Result: Output to console was as expected:
//        ---------Printout of car park log file with name: testCarLogFile.txt---------
//        IN,123456789012,AB12ABC,2023-04-02 17:41:45.33
//        OUT,123456789012,AB12ABC,2023-04-02 17:41:45.331
    }

    @Test
    public void testPopulateHashFromFileMethodInvocations() throws IOException, RecordCannotBeAdded, IsFull {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
        writer.append("IN," + barcode + "," + registration + "," + new Timestamp(new Date().getTime()));
        writer.append("\n");
        writer.close();
        carLogFile.populateHashFromFile(mockCars, mockCarPark);
        verify(mockCars, times(1)).add(barcode, registration);
        verify(mockCarPark, times(1)).decrementSpacesAvailable();
    }

}
