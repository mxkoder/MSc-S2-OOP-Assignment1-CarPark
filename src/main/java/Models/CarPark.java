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

    // TODO optional can add method to increment/dec by more than one

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void update(CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, BarrierEntry entryBarrier, BarrierExit exitBarrier, FullSign fullSign, CarLogFile carLogFile) {

        //TODO - in update method, check only increment if barrier is raised

        boolean carDetectedAtEntrance = pollCarParkSensor(entrySensor);

        if (carDetectedAtEntrance) {

            //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

            if(spacesAvailable > 0) {

                try {
                    carsInCarPark.add(barcodeReader.getID(), regReader.getID());
                    carLogFile.recordArrival(barcodeReader.getID(), regReader.getID());

                    entryBarrier.raise();
                    decrementSpacesAvailable();
                    fullSign.update(spacesAvailable);

                    System.out.println("Vehicle enters car park.");

                    entryBarrier.lower();
                } catch (RecordCannotBeAdded e) {
                    e.printStackTrace();
                }

            }
            else {
                fullSign.update(spacesAvailable);
                System.out.println("Car park is full, please come back later.");
            }
        }
        else {
            System.out.println("No car was detected at the " + entrySensor.getSensorLocation() + " barrier.");
        }

        // TODO reset ID readers, sensor, and barriers (if needed) to default - try a lambda?? or refactor and pull out as method?
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();

        boolean carDetectedAtExit = pollCarParkSensor(exitSensor);

        if (carDetectedAtExit) {
            // All exiting cars should already be members of the car park
            //TODO optional- could add barrier as input for IDReader menu - if sensor type is exit: problem if not member
            while(IDReaderMenu(barcodeReader, regReader, carMembers));

            if(carsInCarPark.vehicleIsFoundByBarcode(barcodeReader.getID()) && carsInCarPark.vehicleIsFoundByReg(regReader.getID())) {

                exitBarrier.raise();

                carsInCarPark.remove(barcodeReader.getID(), regReader.getID());
                carLogFile.recordDeparture(barcodeReader.getID(), regReader.getID());
                incrementSpacesAvailable();
                fullSign.update(spacesAvailable);

                System.out.println("Vehicle leaves car park.");

                exitBarrier.lower();
            }
            else
            {
                System.out.println("We do not have a record of this vehicle entering the car park. \n"
                        + "<Manual resolution of issue by attendant and parking payment needs to be taken.>");
            }
        }
        else {
            System.out.println("No car was detected at the " + exitSensor.getSensorLocation() + " barrier.");
        }

        // TODO reset ID readers, sensor, and barriers (if needed) to default - try a lambda??
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();
        fullSign.update(spacesAvailable);

        //TODO or have this as a 'to string' method??
        System.out.println("At the end of the update cycle, the car park status is: "
                + "Number of vehicles in car park: " + carsInCarPark.numberOfRecords()
                + "Car park spaces available: " + spacesAvailable
                + "Car park capacity: " + capacity
                + "Number of car park members: " + carMembers.numberOfRecords());
    };
}
