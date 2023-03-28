package Models;

import DataStorage.Cars;
import Exceptions.RecordCannotBeAdded;
import FileHandling.CarLogFile;
import InputOutput.ConsoleDialogue;
import NavigationMenus.CarIDReaderMenu;

import java.io.IOException;

import static InputOutput.ConsoleDialogue.pollCarParkSensor;
import static NavigationMenus.CarIDReaderMenu.IDReaderMenu;

public class CarPark implements Premises {

    private Integer capacity;
    private Integer spacesAvailable;

    // TODO throw exception if capacity < spacesAvailable
    public CarPark(Integer capacity, Integer spacesAvailable) {
        this.capacity = capacity;
        this.spacesAvailable = spacesAvailable;
    }

    @Override
    public void incrementSpacesAvailable() {
        if (spacesAvailable < capacity) {
            this.spacesAvailable += 1;
        }
        else {
            System.out.println("The car park is empty and the maximum number of spaces is already available");
        }
    }

    @Override
    public void decrementSpacesAvailable() {
        if ( spacesAvailable >= 1 ) {
            this.spacesAvailable -= 1;
        }
        else {
            System.out.println("The car park is already full.");
        }
    }

    @Override
    public int getSpacesAvailable() {
        return this.spacesAvailable;
    };

    // TODO optional can add method to increment/dec by more than one

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }


}
