package Models;

import Exceptions.IsFull;
import Exceptions.InvalidAvailabilityAndCapacityException;

public class CarPark implements Premises {

    private Integer capacity;
    private Integer spacesAvailable;

    public CarPark (Integer capacity) {
        this.capacity = capacity;
    }

    public void setSpacesAvailable (Integer spacesAvailable) throws InvalidAvailabilityAndCapacityException {
        if(spacesAvailable > this.capacity) {
            throw new InvalidAvailabilityAndCapacityException("Spaces available in car park cannot be greater than the ca park capacity.");
        }
        else {
            this.spacesAvailable = spacesAvailable;
        }
    }

    public void setCapacity(Integer capacity) throws InvalidAvailabilityAndCapacityException {
        if(this.spacesAvailable > capacity) {
            throw new InvalidAvailabilityAndCapacityException("Spaces available in car park cannot be greater than the ca park capacity.");
        }
        else {
            this.capacity = capacity;
        }
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
    public void decrementSpacesAvailable() throws IsFull {
        if ( spacesAvailable >= 1 ) {
            this.spacesAvailable -= 1;
        }
        else {
            throw new IsFull("Car Park is FULL. Record cannot be added.");
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




}
