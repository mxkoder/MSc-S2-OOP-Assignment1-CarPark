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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import DataStorage.Cars;
import FileHandling.CarLogFile;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;
import Models.CarPark;

@RunWith(MockitoJUnitRunner.class)
public class CarLogFileTest {

    @Mock
    private Cars mockCars;

    @Mock
    private CarPark mockCarPark;

    private CarLogFile carLogFile;

    private String carLogFileName = "testCarLogFile.txt";

    @Before
    public void setUp() {
        carLogFile = new CarLogFile(carLogFileName);
        carLogFile.clearFileContents();
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
        String barcode = "123456";
        String registration = "AB12CD";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        carLogFile.recordArrival(barcode, registration);
        String expected = "IN," + barcode + "," + registration + "," + timestamp + "\n";
        BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));
        String actual = reader.readLine();
        reader.close();
        assertEquals(expected, actual);
    }

    @Test
    public void testRecordDeparture() throws IOException {
        String barcode = "123456";
        String registration = "AB12CD";
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        carLogFile.recordDeparture(barcode, registration);
        String expected = "OUT," + barcode + "," + registration + "," + timestamp + "\n";
        BufferedReader reader = new BufferedReader(new FileReader(carLogFileName));
        String actual = reader.readLine();
        reader.close();
        assertEquals(expected, actual);
    }

    @Test
    public void testPrintFileToConsole() {
        carLogFile.recordArrival("123456", "AB12CD");
        carLogFile.recordDeparture("123456", "AB12CD");
        carLogFile.printFileToConsole();
        // TODO: Use output capture to test printed output
    }

    @Test
    public void testPopulateHashFromFile() throws IOException, RecordCannotBeAdded, IsFull, VehicleAtExitWasNotRecordedEntering {
        String barcode = "123456";
        String registration = "AB12CD";
        BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
        writer.append("IN," + barcode + "," + registration + "," + new Timestamp(new Date().getTime()));
        writer.append("\n");
        writer.close();
        carLogFile.populateHashFromFile(mockCars, mockCarPark);
        verify(mockCars, times(1)).add(barcode, registration);
        verify(mockCarPark, times(1)).decrementSpacesAvailable();
    }

//    //TODO edit
//    @Test
//    public void testRestoreDataFromFile() throws IOException, RecordCannotBeAdded, IsFull, VehicleAtExitWasNotRecordedEntering {
//        String barcode = "123456";
//        String registration = "AB12CD";
//        BufferedWriter writer = new BufferedWriter(new FileWriter(carLogFileName, true));
//        assertEquals(2, mockCars.size());
}
