package ModelsTest;

import Models.CarParkSensor;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CarParkSensorTest {
    @Test
    public void testSetCarIsPresent() {
        CarParkSensor sensor = new CarParkSensor(false, "Entrance");
        assertFalse(sensor.getCarIsPresent());

        CarParkSensor sensor1 = new CarParkSensor(true, "Entrance");
        assertTrue(sensor1.getCarIsPresent());
    }

    @Test
    public void testSetSensor() {
        CarParkSensor sensor = new CarParkSensor(false, "Entrance");

        sensor.setSensor(true);
        assertTrue(sensor.getCarIsPresent());

        sensor.setSensor(false);
        assertFalse(sensor.getCarIsPresent());
    }

    @Test
    public void testGetSensorLocation() {
        CarParkSensor sensorEntrance = new CarParkSensor(false, "Entrance");
        CarParkSensor sensorExit = new CarParkSensor(false, "Exit");

        assertEquals("Entrance", sensorEntrance.getSensorLocation());
        assertEquals("Exit", sensorExit.getSensorLocation());
    }
}
