package OperationHelpersTest;

import Models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static OperationHelpers.CarParkOperation.resetAndUpdateCarParkDevices;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarParkOperationTest {

    @Test
    public void testResetAndUpdateCarParkDevicesWithCarPresent() {
        CarParkSensor entrySensor = new CarParkSensor(true, "entry");
        CarParkSensor exitSensor = new CarParkSensor(true, "entry");
        IDReaderBarcode barcodeReader = new IDReaderBarcode("123456789012");
        IDReaderRegistration regReader = new IDReaderRegistration("AB12ABC");
        FullSign fullSign = new FullSign(true);

        CarPark mockCarPark = mock(CarPark.class);
        when(mockCarPark.getSpacesAvailable()).thenReturn(100);

        resetAndUpdateCarParkDevices(entrySensor, exitSensor, barcodeReader, regReader, fullSign, mockCarPark);

        assertFalse(entrySensor.getCarIsPresent());
        assertFalse(exitSensor.getCarIsPresent());
        assertEquals("", barcodeReader.getID());
        assertEquals("", regReader.getID());
        assertFalse(fullSign.getStatus());
    }

    @Test
    public void testResetAndUpdateCarParkDevicesNoCar() {
        CarParkSensor entrySensor = new CarParkSensor(false, "entry");
        CarParkSensor exitSensor = new CarParkSensor(false, "entry");
        IDReaderBarcode barcodeReader = new IDReaderBarcode("123456789012");
        IDReaderRegistration regReader = new IDReaderRegistration("AB12ABC");
        FullSign fullSign = new FullSign(true);

        CarPark mockCarPark = mock(CarPark.class);
        when(mockCarPark.getSpacesAvailable()).thenReturn(100);

        resetAndUpdateCarParkDevices(entrySensor, exitSensor, barcodeReader, regReader, fullSign, mockCarPark);

        assertFalse(entrySensor.getCarIsPresent());
        assertFalse(exitSensor.getCarIsPresent());
        assertEquals("", barcodeReader.getID());
        assertEquals("", regReader.getID());
        assertFalse(fullSign.getStatus());
    }

    @Test
    public void testResetAndUpdateCarParkDevicesIsFull() {
        CarParkSensor entrySensor = new CarParkSensor(false, "entry");
        CarParkSensor exitSensor = new CarParkSensor(false, "entry");
        IDReaderBarcode barcodeReader = new IDReaderBarcode("123456789012");
        IDReaderRegistration regReader = new IDReaderRegistration("AB12ABC");
        FullSign fullSign = new FullSign(true);

        CarPark mockCarPark = mock(CarPark.class);
        when(mockCarPark.getSpacesAvailable()).thenReturn(0);

        resetAndUpdateCarParkDevices(entrySensor, exitSensor, barcodeReader, regReader, fullSign, mockCarPark);

        assertFalse(entrySensor.getCarIsPresent());
        assertFalse(exitSensor.getCarIsPresent());
        assertEquals("", barcodeReader.getID());
        assertEquals("", regReader.getID());
        assertTrue(fullSign.getStatus());
    }
}
