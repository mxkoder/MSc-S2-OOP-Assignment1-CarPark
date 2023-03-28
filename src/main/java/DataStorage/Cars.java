package DataStorage;

import Exceptions.RecordCannotBeAdded;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Cars implements Vehicles {

    private Hashtable <String, String> cars = new Hashtable<String, String>(); // barcode is key, reg plate is value

    private static Scanner stdin = new Scanner(System.in);

    //TODO - optional - reformat and verify barcode and rg format within these methods as well?
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

    @Override
    public void remove(String vehicleBarcode, String vehicleReg) {
        if (vehicleIsFoundByBarcode(vehicleBarcode) && vehicleIsFoundByReg(vehicleReg)) {
            cars.remove(vehicleBarcode, vehicleReg);
            System.out.println("Car with barcode " + vehicleBarcode + " and registration " + vehicleReg + " has been removed.");
        }
        else {
            System.out.println("Invalid vehicle details provided. We do not have a record of this vehicle.");
        }

    }

    @Override
    public String getRegistrationByBarcode(String vehicleBarcode) {
        return cars.get(vehicleBarcode);
    }

    //TODO add java doc, explain value will be unique because of encapsulation and restiction in add method
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

    @Override
    public void printAll() {
        for (String key : cars.keySet()) {
            System.out.println(key + ", " + cars.get(key));
        }
    }

    // TODO - add javadoc, would have auth  & authenitcaiton here
    @Override
    public boolean deleteAllVehicles() {
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

    public boolean vehicleIsFoundByBarcode(String vehicleBarcode) {
        return cars.containsKey(vehicleBarcode);
    }

    public boolean vehicleIsFoundByReg(String vehicleReg) {
        return cars.containsValue(vehicleReg);
    }

    //TODO - methods to link in w file handling?? or add to existing methods to add to log??
}
