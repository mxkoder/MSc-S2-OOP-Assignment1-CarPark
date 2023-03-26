package Models;

abstract class Sensor {
    abstract void setSensor (boolean carIsPresent);

    abstract boolean getCarIsPresent();

    abstract String getSensorLocation();
}
