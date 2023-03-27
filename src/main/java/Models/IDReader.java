package Models;

abstract class IDReader {
    abstract void readID ();

    abstract String getID ();

    abstract void setID (String ID);

    abstract void resetToDefault();
}
