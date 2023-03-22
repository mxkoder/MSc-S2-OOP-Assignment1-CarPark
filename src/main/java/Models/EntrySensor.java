package Models;

public class EntrySensor extends Sensor {
    private boolean carIsPresent;
    @Override
    public void setSensor(boolean carIsPresent) {
        this.carIsPresent = carIsPresent;
    }

    @Override
    public boolean getCarIsPresent() {
        return carIsPresent;
    }
}
