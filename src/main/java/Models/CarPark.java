package Models;

import InputOutput.ConsoleDialogue;

import static InputOutput.ConsoleDialogue.pollCarParkSensor;

public class CarPark implements Premises {

    private Integer capacity;
    private Integer spacesAvailable;

    public CarPark(Integer capacity) {
        this.capacity = capacity;
    }

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

    // TODO can add method to increment/dec by more than one

    // TODO add update method
    //todo add to premises? list of args is specific
    public void update(CarParkSensor entrySensor, CarParkSensor exitSensor) {

        //ConsoleDialogue input = new ConsoleDialogue();

        //TODO - in update method, check only increment if barrier is raised


        //Place your carpark update logic here.
//            //The pseudo code illustrates some of the actions required
//            // in conjunction with other classes and will need further refinement.
//            //1.  poll car park components (sensor/etc).
//            //************************************************
        if (pollCarParkSensor(entrySensor)) {
            // dialogue methods that asks to read in barcode or registration
                // make sure use IDReader classes

            // check if exists in members list (need to make the 'extra' bullet points)
            // return barcode or reg from this, whichever is missing, or that need to join
                //if needed: join and create bar code for car/member
                    //add to members list

            // if car park not full->
                // raise barier and let car pass
                // add car to carpark
                // record in log
                // decrement car park spaces
        }
        else {
            System.out.println("No car was detected at the " + entrySensor.getSensorLocation() + " barrier.");
        }
//            //3.  if car present at entrance then
//            //4.    if ID valid && car park not full then
//            //5.      raise barrier and let car pass
//            //6.	  endif
//            //7.  endif
//            //************************************************
        if (pollCarParkSensor(exitSensor)) {
            // dialogue methods that asks to read in barcode & registration
                // make sure use IDReader classes

            // check if exists in car park, if not throw exception or print warning
            // raise exit barrier
            // remove car from car park
                // record in log
                // increment car park spaces

        }
        else {
            System.out.println("No car was detected at the " + exitSensor.getSensorLocation() + " barrier.");
        }
//            //9.  if car present at exit then
//            //10.   raise barrier and let car pass
//            //11.   update records and increment spaces
//            //12. endif
//            //************************************************
//            //14. if carpark full then
//            //15.   turn on full sign
//            //16: else
//            //17.   turn off full sign
//            //18. endif
//            //************************************************
//            //...and so on

        // reset sensors and barriers

        //line out car park status - no spaces left print out
    };

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
