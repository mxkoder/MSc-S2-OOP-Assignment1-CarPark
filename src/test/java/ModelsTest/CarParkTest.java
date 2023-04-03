package ModelsTest;

import DataStorage.Cars;
import Exceptions.InvalidAvailabilityAndCapacityException;
import Models.CarPark;
import Models.IDReaderRegistration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CarParkTest {

    @Mock
    private CarPark mockCarPark;

    @Mock
    private Cars mockCars;

    @Before
    public void setUp() {
        mockCarPark = new CarPark(100);
    }

    @Test
    public void testSetSpacesAvailable() throws InvalidAvailabilityAndCapacityException {}

    @Test
    public void testSetSpacesAvailableThrowsException() throws InvalidAvailabilityAndCapacityException {}

    @Test
    public void testSetCapacity() throws InvalidAvailabilityAndCapacityException {}

    @Test
    public void testSetCapacityThrowsException() throws InvalidAvailabilityAndCapacityException {}
}
