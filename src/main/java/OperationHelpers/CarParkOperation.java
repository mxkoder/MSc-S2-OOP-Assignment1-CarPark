package OperationHelpers;

import DataStorage.Cars;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;
import FileHandling.CarLogFile;
import FileHandling.MembersFile;
import Models.*;

import static OperationHelpers.CarIDReaderMenu.IDReaderMenu;
import static OperationHelpers.ConsoleDialogue.pollCarParkSensor;

public class CarParkOperation {
    public static void update(CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, BarrierEntry entryBarrier, BarrierExit exitBarrier, FullSign fullSign, CarLogFile carParkLogFile, MembersFile carMembersFile) {

        //---------------Poll entrance and update car park--------------------------------------
        boolean carDetectedAtEntrance = pollCarParkSensor(entrySensor);

        if (carDetectedAtEntrance) {

            //TODO add javadoc for this: reads barcode or reg number from console. Checks against members list. if present, gets matching ID. If not, reads in additional ID to sign up.
            while(IDReaderMenu(barcodeReader, regReader, carMembers, carMembersFile));

            if(carPark.getSpacesAvailable() > 0) {

                try {
                    System.out.println("\nCar park " + entrySensor.getSensorLocation() + " update:");
                    carsInCarPark.add(barcodeReader.getID(), regReader.getID());
                    carParkLogFile.recordArrival(barcodeReader.getID(), regReader.getID());

                    entryBarrier.raise();
                    carPark.decrementSpacesAvailable();
                    System.out.println("Vehicle enters car park.");
                    entryBarrier.lower();

                } catch (RecordCannotBeAdded | IsFull e) {
                    e.printStackTrace();
                }

            }
            else {
                fullSign.update(carPark.getSpacesAvailable());
                System.out.println("Could not admit vehicle to car park" + "----->>CAR PARK IS FULL, please come back later.");
            }
        }

        resetAndUpdateCarParkDevices(entrySensor, barcodeReader, regReader, fullSign, carPark);

        //---------------Poll exit and update car park--------------------------------------

        boolean carDetectedAtExit = pollCarParkSensor(exitSensor);

        if (carDetectedAtExit) {

            while(IDReaderMenu(barcodeReader, regReader, carMembers, carMembersFile));

            try {
                System.out.println("\nCar park " + exitSensor.getSensorLocation() + " update:");
                carsInCarPark.remove(barcodeReader.getID(), regReader.getID());
                carParkLogFile.recordDeparture(barcodeReader.getID(), regReader.getID());

                exitBarrier.raise();
                carPark.incrementSpacesAvailable();
                System.out.println("Vehicle leaves car park.");
                exitBarrier.lower();
            }
            catch (VehicleAtExitWasNotRecordedEntering e) {
                e.printStackTrace();
            }
        }

        resetAndUpdateCarParkDevices(entrySensor, barcodeReader, regReader, fullSign, carPark);

        printCarParkStatus(carsInCarPark, carPark, carMembers);
    };

    public static void resetAndUpdateCarParkDevices(CarParkSensor entrySensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, FullSign fullSign, CarPark carPark) {
        System.out.println("\n----------Resetting car park devices----------");
        entrySensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();
        fullSign.update(carPark.getSpacesAvailable());
        System.out.println("\n-----------------------------------------------");
    }

    public static void printCarParkStatus(Cars carsInCarPark, CarPark carPark, Cars carMembers) {
        System.out.println("\nAt the end of the update cycle, the car park status is: "
                + "\n- Number of vehicles in car park: " + carsInCarPark.numberOfRecords()
                + "\n- Car park spaces available: " + carPark.getSpacesAvailable()
                + "\n- Car park capacity: " + carPark.getCapacity()
                + "\n- Number of car park members: " + carMembers.numberOfRecords());
    }
}
