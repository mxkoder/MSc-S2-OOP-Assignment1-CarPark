package CarParkTest;

import Exceptions.InvalidAvailabilityAndCapacityException;
import Exceptions.IsFull;
import CarPark.CarPark;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CarParkTest {

    @Mock
    private CarPark mockCarPark;

    @Before
    public void setUp() {
        mockCarPark = new CarPark(100);
    }


    @Test
    public void testCarParkConstructor() {
        assertEquals(100, mockCarPark.getCapacity().intValue());
    }

    @Test
    public void testSetSpacesAvailable() throws InvalidAvailabilityAndCapacityException {
        mockCarPark.setSpacesAvailable(10);
        assertEquals(10, mockCarPark.getSpacesAvailable());

        mockCarPark.setSpacesAvailable(50);
        assertEquals(50, mockCarPark.getSpacesAvailable());

        mockCarPark.setSpacesAvailable(99);
        assertEquals(99, mockCarPark.getSpacesAvailable());
    }

    @Test
    public void testSetSpacesAvailableThrowsException() {
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setSpacesAvailable(110);});
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setSpacesAvailable(150);});
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setSpacesAvailable(200);});
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setSpacesAvailable(1110);});
    }

    @Test
    public void testSetCapacity() throws InvalidAvailabilityAndCapacityException {
        mockCarPark.setSpacesAvailable(1);

        mockCarPark.setCapacity(100);
        assertEquals(100, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(1000);
        assertEquals(1000, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(150);
        assertEquals(150, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(1);
        assertEquals(1, mockCarPark.getCapacity().intValue());
    }

    @Test
    public void testSetCapacityThrowsException() throws InvalidAvailabilityAndCapacityException {
        mockCarPark.setSpacesAvailable(10);
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setCapacity(9);});

        mockCarPark.setSpacesAvailable(50);
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setCapacity(1);});

        mockCarPark.setSpacesAvailable(10);
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setCapacity(-9);});

        mockCarPark.setSpacesAvailable(99);
        assertThrows(InvalidAvailabilityAndCapacityException.class, ()-> {mockCarPark.setCapacity(98);});
    }

    public void testIncrementSpacesAvailable() throws InvalidAvailabilityAndCapacityException{
        mockCarPark.setSpacesAvailable(99);

        mockCarPark.incrementSpacesAvailable();
        assertEquals(51, mockCarPark.getSpacesAvailable());

        mockCarPark.incrementSpacesAvailable();
        assertEquals(52, mockCarPark.getSpacesAvailable());

        mockCarPark.incrementSpacesAvailable();
        assertEquals(53, mockCarPark.getSpacesAvailable());
    }

    public void testIncrementSpacesAvailableExceedsCapacity() throws InvalidAvailabilityAndCapacityException{

        mockCarPark.setSpacesAvailable(50);

        mockCarPark.incrementSpacesAvailable();
        assertEquals(100, mockCarPark.getSpacesAvailable());

        mockCarPark.incrementSpacesAvailable();
        assertEquals(100, mockCarPark.getSpacesAvailable());

        mockCarPark.incrementSpacesAvailable();
        assertEquals(100, mockCarPark.getSpacesAvailable());
    }

    public void testDecrementSpacesAvailable() throws InvalidAvailabilityAndCapacityException, IsFull{

        mockCarPark.setSpacesAvailable(50);

        mockCarPark.decrementSpacesAvailable();
        assertEquals(49, mockCarPark.getSpacesAvailable());

        mockCarPark.decrementSpacesAvailable();
        assertEquals(48, mockCarPark.getSpacesAvailable());

        mockCarPark.decrementSpacesAvailable();
        assertEquals(47, mockCarPark.getSpacesAvailable());

    }

    public void testDecrementSpacesAvailableThrowsIsFullException() throws InvalidAvailabilityAndCapacityException {

        mockCarPark.setSpacesAvailable(0);
        assertThrows(IsFull.class, ()-> {mockCarPark.decrementSpacesAvailable();});
    }

    public void testGetSpacesAvailable() throws InvalidAvailabilityAndCapacityException {

        mockCarPark.setSpacesAvailable(30);
        assertEquals(30, mockCarPark.getSpacesAvailable());

        mockCarPark.setSpacesAvailable(0);
        assertEquals(0, mockCarPark.getSpacesAvailable());

        mockCarPark.setSpacesAvailable(99);
        assertEquals(99, mockCarPark.getSpacesAvailable());

        mockCarPark.setSpacesAvailable(10);
        assertEquals(10, mockCarPark.getSpacesAvailable());
    }

    public void testGetCapacity() throws InvalidAvailabilityAndCapacityException {

        mockCarPark.setCapacity(0);
        assertEquals(0, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(100);
        assertEquals(100, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(7879);
        assertEquals(7879, mockCarPark.getCapacity().intValue());

        mockCarPark.setCapacity(422);
        assertEquals(422, mockCarPark.getCapacity().intValue());




    }
}
