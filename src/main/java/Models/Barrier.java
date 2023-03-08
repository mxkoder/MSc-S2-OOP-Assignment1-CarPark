package Models;

abstract class Barrier {

    abstract void raise();
    abstract void lower();
    abstract boolean getStatus();

    //abstract void update();
}
