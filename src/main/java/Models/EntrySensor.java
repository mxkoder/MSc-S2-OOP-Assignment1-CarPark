package Models;

public class EntrySensor extends Sensor {
    private boolean carIsPresent;
    @Override
    void setSensor(boolean carIsPresent) {
        this.carIsPresent = carIsPresent;
    }

    @Override
    boolean getCarIsPresent() {
        return carIsPresent;
    }
}
