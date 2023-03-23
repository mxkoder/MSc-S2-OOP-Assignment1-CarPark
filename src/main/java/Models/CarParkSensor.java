package Models;

public class CarParkSensor extends Sensor {
    private boolean carIsPresent;
    @Override
    public void setSensor(boolean carIsPresent) {
        this.carIsPresent = carIsPresent;
    }

    @Override
    public boolean getCarIsPresent() {
        return carIsPresent;
    }

    //TODO update()?? what would it update?
}
