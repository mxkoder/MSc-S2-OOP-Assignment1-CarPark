package CarPark;

import Exceptions.IsFull;
import Exceptions.InvalidAvailabilityAndCapacityException;

public class CarPark implements Premises {

    private Integer capacity;
    private Integer spacesAvailable;

    public CarPark(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the number of available car parking spaces in the car park
     * <p>If an attempt of made to set the spaces available to a number lower than the car park capacity, and exception will be thrown</p>
     *
     * @param spacesAvailable Integer value for the number of spaces currently available in the car park.
     * @throws InvalidAvailabilityAndCapacityException
     */
    public void setSpacesAvailable(Integer spacesAvailable) throws InvalidAvailabilityAndCapacityException {
        if (spacesAvailable > this.capacity) {
            throw new InvalidAvailabilityAndCapacityException("Spaces available in car park cannot be greater than the ca park capacity.");
        } else {
            this.spacesAvailable = spacesAvailable;
        }
    }

    /**
     * Sets the capacity of the car park, which the maximum number of car parking spaces available.
     * <P>If an attampt is made to set the capacity to a value lower than the spacesAvailable, an exception will  be thrown</P>
     *
     * @param capacity
     * @throws InvalidAvailabilityAndCapacityException
     */
    public void setCapacity(Integer capacity) throws InvalidAvailabilityAndCapacityException {
        if (this.spacesAvailable > capacity) {
            throw new InvalidAvailabilityAndCapacityException("Spaces available in car park cannot be greater than the ca park capacity.");
        } else {
            this.capacity = capacity;
        }
    }

    /**
     * Method to increment the number of spaces available in the car park
     * <p>If the number of spaces available reaches the capacity, the method will not increment the number of spaces available,
     * and will instead print a message to the console.</p>
     */
    @Override
    public void incrementSpacesAvailable() {
        if (spacesAvailable < capacity) {
            this.spacesAvailable += 1;
        } else {
            System.out.println("The car park is empty and the maximum number of spaces is already available");
        }
    }

    /**
     * Method to decrement the spaces available in the car park.
     * <p>If there are no spaces available, the IsFull exception will be thrown and spacesAvailable will not be decremented.</p>
     *
     * @throws IsFull
     */
    @Override
    public void decrementSpacesAvailable() throws IsFull {
        if (spacesAvailable >= 1) {
            this.spacesAvailable -= 1;
        } else {
            throw new IsFull("Car Park is FULL. Record cannot be added.");
        }
    }

    @Override
    public int getSpacesAvailable() {
        return this.spacesAvailable;
    }

    ;

    public Integer getCapacity() {
        return capacity;
    }
}
