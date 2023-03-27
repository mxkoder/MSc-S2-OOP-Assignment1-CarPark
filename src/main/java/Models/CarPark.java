package Models;

import DataStorage.Cars;
import FileHandling.CarLogFile;
import InputOutput.ConsoleDialogue;
import NavigationMenus.CarIDReaderMenu;

import static InputOutput.ConsoleDialogue.pollCarParkSensor;
import static NavigationMenus.CarIDReaderMenu.IDReaderMenu;

public class CarPark implements Premises {

    private Integer capacity;
    private Integer spacesAvailable;

    public CarPark(Integer capacity) {
        this.capacity = capacity;
    }

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

    // TODO can add method to increment/dec by more than one

    // TODO add update method
    //todo add to premises? list of args is specific
    public void update(CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, BarrierEntry entryBarrier, BarrierExit exitBarrier, FullSign fullSign, CarLogFile carLogFile) {

        //TODO - in update method, check only increment if barrier is raised

        boolean carDetectedAtEntrance = pollCarParkSensor(entrySensor);

        if (carDetectedAtEntrance) {

            //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

            if(spacesAvailable > 0) {
                entryBarrier.raise();

                //TODO make sure throw exception if invalid add.
                carsInCarPark.add(barcodeReader.getID(), regReader.getID());
                carLogFile.recordArrival(barcodeReader.getID(), regReader.getID());
                incrementSpacesAvailable();
                fullSign.update(spacesAvailable);

                System.out.println("Car enters car park.");

                entryBarrier.lower();
            }
            else {
                fullSign.update(spacesAvailable);
                System.out.println("Car park is full, please come back later.");
            }



        }
        else {
            System.out.println("No car was detected at the " + entrySensor.getSensorLocation() + " barrier.");
        }

        // TODO reset ID readers, sensor, and barriers (if needed) to default - try a lambda??
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();

        boolean carDetectedAtExit = pollCarParkSensor(exitSensor);

        if (carDetectedAtExit) {
            // All exiting cars should already be members of the car park
            //TODO optional- could add barrier as input for IDReader menu - if barier type is exit: problem if not member
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

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



        // TODO reset ID readers, sensor, and barriers (if needed) to default - try a lambda??
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();

        //TODO - print car park status - line out car park status - no spaces left print out
    };

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
