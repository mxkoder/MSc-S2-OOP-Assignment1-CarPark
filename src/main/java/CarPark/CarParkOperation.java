package CarPark;

import Barriers.CarParkBarrier;
import Sensors.CarParkSensor;
import DataStorage.Cars;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;
import FileHandling.CarLogFile;
import FileHandling.MembersFile;
import IDReaders.IDReaderBarcode;
import IDReaders.IDReaderRegistration;

import static IDReaders.CarIDReaderMenu.IDReaderMenu;
import static OperationHelpers.ConsoleDialogue.pollCarParkSensor;

public class CarParkOperation {
    public static void update(CarPark carPark, CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, Cars carMembers, Cars carsInCarPark, CarParkBarrier entryBarrier, CarParkBarrier exitBarrier, FullSign fullSign, CarLogFile carParkLogFile, MembersFile carMembersFile) {

        //---------------Poll entrance and update car park--------------------------------------
        boolean carDetectedAtEntrance = pollCarParkSensor(entrySensor);

        if (carDetectedAtEntrance) {

            // The IDReaderMenu takes an input of the registration number or barcode using the Scanner class
            // The car park members list is checked, and a matching barcode or reg number is found if it exists, otherwise that value is read in through the Scanner class
            // The registration number and barcode are recorded in the IDReaderBarcode and IDReaderRegistration classes.
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

        resetAndUpdateCarParkDevices(entrySensor, exitSensor, barcodeReader, regReader, fullSign, carPark);

        //---------------Poll exit and update car park--------------------------------------

        boolean carDetectedAtExit = pollCarParkSensor(exitSensor);

        if (carDetectedAtExit) {

            // The IDReaderMenu takes an input of the registration number or barcode using the Scanner class
            // The car park members list is checked, and a matching barcode or reg number is found if it exists, otherwise that value is read in through the Scanner class
            // The registration number and barcode are recorded in the IDReaderBarcode and IDReaderRegistration classes.
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

        resetAndUpdateCarParkDevices(entrySensor, exitSensor, barcodeReader, regReader, fullSign, carPark);

        printCarParkStatus(carsInCarPark, carPark, carMembers);
    };

    /**
     * Method to reset the car park devices to their default settings and to update the full sign
     * <p>Barriers will be lowered, sensors will be set not detecting a vehicle, and IDReaders are set to default empty string starting values</p>
     * <p>The full sign is updated to ON or OFF using the number of spaces available from the carPark input parameter.</p>
     * @param entrySensor
     * @param exitSensor
     * @param barcodeReader
     * @param regReader
     * @param fullSign
     * @param carPark
     */
    public static void resetAndUpdateCarParkDevices(CarParkSensor entrySensor, CarParkSensor exitSensor, IDReaderBarcode barcodeReader, IDReaderRegistration regReader, FullSign fullSign, CarPark carPark) {
        System.out.println("\n----------Resetting car park devices----------");
        entrySensor.setSensor(false);
        exitSensor.setSensor(false);
        barcodeReader.resetToDefault();
        regReader.resetToDefault();
        fullSign.update(carPark.getSpacesAvailable());
        System.out.println("\n-----------------------------------------------");
    }

    /**
     * Method to print the status of the car park to the console.
     * <p>The no. vehicles in the car park, spaces available, capacity, and number of car park members will be printed to the console. </p>
     * @param carsInCarPark
     * @param carPark
     * @param carMembers
     */
    public static void printCarParkStatus(Cars carsInCarPark, CarPark carPark, Cars carMembers) {
        System.out.println("\nAt the end of the update cycle, the car park status is: "
                + "\n- Number of vehicles in car park: " + carsInCarPark.numberOfRecords()
                + "\n- Car park spaces available: " + carPark.getSpacesAvailable()
                + "\n- Car park capacity: " + carPark.getCapacity()
                + "\n- Number of car park members: " + carMembers.numberOfRecords());
    }
}
