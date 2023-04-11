package DataStorage;

import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Cars implements Vehicles {

    private Hashtable <String, String> cars = new Hashtable<String, String>(); // barcode is key, registration number is value

    private static Scanner stdin = new Scanner(System.in);

    /**
     * Method to add a car to the data storage list
     * <p>The method ensures that the hashtable can only hold unique values to make sure that e.g. a vehicle cannot enter be added if it is already in the
     * car park, or already a member.</p>
     * <p>The input parameter barcode and registration number are checked against the values in the Cars data storage first, and only added if they are not already
     * in the dataset.</p>
     * @param vehicleBarcode String - a 12 digit barcode value
     * @param vehicleReg String - a standard UK vehicle registration
     * @throws RecordCannotBeAdded - Is thrown if the Cars data storage already contains a record matching either of the values the user is attempting to add.
     */
    @Override
    public void add(String vehicleBarcode, String vehicleReg) throws RecordCannotBeAdded {
        if (!vehicleIsFoundByBarcode(vehicleBarcode) && !vehicleIsFoundByReg(vehicleReg)) {
            cars.put(vehicleBarcode, vehicleReg);
            System.out.println("Car with barcode " + vehicleBarcode + " and registration " + vehicleReg + " has been added.");
        }
        else {
            throw new RecordCannotBeAdded("The vehicle cannot be added. Table already contains a vehicle with "
                    + vehicleBarcode + " or "
                    + vehicleReg + ".");
        }
    }

    /**
     Method to remove a car from the data storage list
     * <p>The method ensures that a data set can only be removed from the hashtable if it is already in the hashtable.</p>
     * <p>The input parameter barcode and registration number are checked against the values in the Cars data storage first, and only removed if they are already
     * in the hashtable, otherwise the VehicleAtExitWasNotRecordedEntering exception will be thrown. </p>
     * @param vehicleBarcode String - a 12 digit barcode value
     * @param vehicleReg String - a standard UK vehicle registration
     * @throws VehicleAtExitWasNotRecordedEntering - exception will be thrown if the barcode and registration are not found in the hashtable
     */
    @Override
    public void remove(String vehicleBarcode, String vehicleReg) throws VehicleAtExitWasNotRecordedEntering {
        if (vehicleIsFoundByBarcode(vehicleBarcode) && vehicleIsFoundByReg(vehicleReg)) {
            cars.remove(vehicleBarcode, vehicleReg);
            System.out.println("Car with barcode " + vehicleBarcode + " and registration " + vehicleReg + " has been removed.");
        }
        else {
            throw new VehicleAtExitWasNotRecordedEntering("We do not have a record of this vehicle entering the car park." +
                    "\n------Manual resolution of issue by attendant and parking payment needs to be taken.------");
        }

    }

    /**
     * Method to find the vehicle registration from the vehicle barcode.
     * <p>Finds the value (registration number) that matches the key (barcode) in the cars hashtable</p>
     * <p>The barcode and registration will be each be unique in the hashtable, because of restrictions it the 'add' method</p>
     * @param vehicleBarcode String - a 12 digit barcode value
     * @return String - a standard UK vehicle registration which is the value for the barcode key in the data storage hashtable
     */
    @Override
    public String getRegistrationByBarcode(String vehicleBarcode) {
        return cars.get(vehicleBarcode);
    }

    /**
     * Method to find the vehicle barcode from the vehicle registration.
     * <p>Finds the key (barcode) that matches the value (registration number) in the cars hashtable</p>
     * <p>The barcode and registration will be each be unique in the hashtable, because of restrictions it the 'add' method</p>
     * @param vehicleReg String - a standard UK vehicle registration
     * @return String - a 12 digit barcode which is the value for the barcode key in the data storage hashtable
     */
    @Override
    public String getBarcodeFromVehicleReg(String vehicleReg) {

        Enumeration e = cars.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = cars.get(key);

            if(value.equals(vehicleReg)) {
                return key;
            }
        }

        return "Vehicle registration not found";
    }

    /**
     * Method to print all key value pairs stored in the hashtable of the Cars class to the console
     * The method prints the barcode (key) and registration (value) of each vehicle in the hashtable
     * on one line in the console.
     */
    @Override
    public void printAll() {
        cars.keySet().forEach((String key) -> System.out.println("Barcode: " + key + ", Registration: " + cars.get(key)));
    }

    // TODO - add javadoc, would have auth  & authentication here

    /**
     * Method to delete all data sets in the 'cars' hashtable
     * <p>The method contains console dialogue to get confirmation from the user to delete the datasets</p>
     * @return - Boolean - a 'true' value will be returned if the datasets in the 'cars' hashtable are deleted, and a 'false' value will be returned otherwise.
     */
    @Override
    public boolean deleteAll() {
        String choice;

        System.out.printf("You are choosing to delete the live list of all the cars in the car park. \n" +
                "Would you like to proceed? \n");

        while(true){
            System.out.printf("Please enter y or n: \n");
            choice = stdin.nextLine().toLowerCase();

            switch(choice){
                case "y":
                    System.out.printf("You have chosen to delete the list of cars in the car park. \n");
                    cars.clear();
                    return true;
                case "n":
                    System.out.printf("You have chosen not to delete the list of cars in the car park. \n");
                    return false;
                default:
                    System.out.printf("Invalid input. \n");
            }
        }
    }

    @Override
    public Integer numberOfRecords() {
        return cars.size();
    }

    /**
     * Method to check if the cars hashtable contains the input parameter barcode
     * @param vehicleBarcode String - a 12 digit barcode value
     * @return boolean - Will return true if the barcode exists in the cars hashtable, and false otherwise
     */
    public boolean vehicleIsFoundByBarcode(String vehicleBarcode) {
        return cars.containsKey(vehicleBarcode);
    }

    /**
     * Method to check if the cars hashtable contains the input parameter vehicle registration
     * @param vehicleReg String - a standard UK vehicle registration
     * @return boolean - Will return true if the vehicle registration exists in the cars hashtable, and false otherwise
     */
    public boolean vehicleIsFoundByReg(String vehicleReg) {
        return cars.containsValue(vehicleReg);
    }
}
