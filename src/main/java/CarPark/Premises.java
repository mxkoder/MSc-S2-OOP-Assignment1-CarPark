package CarPark;

import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;

interface Premises {

    void incrementSpacesAvailable();

    void decrementSpacesAvailable() throws IsFull;

    int getSpacesAvailable();
}
