package Models;

public class CarParkSensor extends Sensor {
    private boolean carIsPresent;
    private String sensorLocation;

    public CarParkSensor(boolean carIsPresent, String sensorLocation) {
        this.carIsPresent = carIsPresent;
        this.sensorLocation = sensorLocation;
    }

    @Override
    public void setSensor(boolean carIsPresent) {
        this.carIsPresent = carIsPresent;
    }

    @Override
    public boolean getCarIsPresent() {
        return carIsPresent;
    }

    public String getSensorLocation() {
        return sensorLocation;
    }
}
