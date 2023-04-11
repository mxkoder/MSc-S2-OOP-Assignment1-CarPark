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

    /**
     * Method to update the car park with cars arriving and departing, based on information from sensors and IDReaders
     * <p>The sensor and IDReader inputs are read in using keyboard input using the Scanner class as specified in the project brief</p>
     *
     * <h2>Car entering car park</h2>
     * <p>The entrance sensor is polled, if no car is detected the method moves on to poll the exit sensors</p>
     * <p> If a car is detected at the entrance, the barcode or registration are read in the IDReaderMenu. In the IDReaderMenu, the vehicle is checked
     * against a list of car park members, using which a barcode can be found from registration number and visa versa.</p>
     * <p>Next, if there are spaces available in the car park, an attempt is made to record the vehicle entering the car park. This is successful if the ID readers have
     * recorded a registration and barcode numbers, and if the carsInCarPark data storage does not already have a vehicle with those details in the cars park.
     * The barcode and registration are then also recorded in the logfile.
     * The entry barrier is raised, the spaces available in the car park is decremented, the vehicle enters the car park, and the entry barrier is lowered</p>
     * <p>If the car park if full or the if the RecordCannotBeAdded exception is thrown (likely due to there already being a vehicle with that barcode or registration
     * in the car park), an error message is printed and the vehicle is not admitted to the car park</p>
     * <p>The sensors, barriers, ID readers are reset and the full sign is updated</p>
     *
     * <h2>Vehicle exiting the car park</h2>
     * <p>Similar process to a vehicle entering the car park, but using the exitSensor and exitBarrier instead.</p>
     * <p>A difference is that when the attempt is made to remove the vehicle from the data storage of vehicles in the car park (carsInCarPark), a
     * VehicleAtExitWasNotRecordedEntering exception is thrown if that vehicle is not found in the carsInCarPark data storage. </p>
     * <p>Also, if the vehicle is successfully removed from carsInCarPark, the spaces available in the car park is incremented.</p>
     *
     * <h2>Status print at end</h2>
     * <p>At the end of the entrance and exit update cycles, the status of the car park and it's devices is printed to the console.</p>
     * @param carPark - Instance of CarPark with capacity and number of spaces available
     * @param entrySensor
     * @param exitSensor
     * @param barcodeReader
     * @param regReader
     * @param carMembers - Instance of Cars: dynamic data storage containing a hashtable of car park members with their barcodes and registration numbers.
     * @param carsInCarPark - Instance of Cars: dynamic data storage containing a hashtable of cars currently in the car park with their barcodes and registration numbers.
     * @param entryBarrier
     * @param exitBarrier
     * @param fullSign
     * @param carParkLogFile - Instance of CarLogFile - static data storage acting as a back-up log of the dynamic data storage of carsInCarPark.
     * @param carMembersFile - Instance of MembersFile - static data storage acting as a back-up log of the dynamic data storage of carMembers.
     */
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
