package Models;

abstract class Barrier {

    abstract void raise();
    abstract void lower();

    abstract void printStatus();

    abstract String getLocation();

    abstract Integer getStatus();
}
