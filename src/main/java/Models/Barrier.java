package Models;

abstract class Barrier {

    abstract void raise();
    abstract void lower();
    abstract boolean barrierIsRaised();

    //abstract void update();
}
