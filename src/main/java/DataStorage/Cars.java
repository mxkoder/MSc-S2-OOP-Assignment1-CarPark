package DataStorage;

import java.util.Hashtable;
import java.util.Scanner;

public class Cars implements Vehicles {

    private Hashtable <String, String> cars = new Hashtable<String, String>(); // barcode is key, reg plate is value

    private static Scanner stdin = new Scanner(System.in);

    //TODO - optional - reformat and verify barcode and rg format within these methods as well?
    //TODO - or can set it up to methods throw exception, eg if car already present and trying to add
    @Override
    public void add(String vehicleBarcode, String vehicleReg) {
        if (!checkVehiclePresentByBarcode(vehicleBarcode) && !checkVehiclePresentByBarcode(vehicleReg)) {
            cars.put(vehicleBarcode, vehicleReg);
            System.out.println("Car with barcode " + vehicleBarcode + " and registration " + vehicleReg + " has been added to the car park.");
        }
        else {
            System.out.println("The car cannot be added to the car park. We already have a vehicle with this ID on the premises.");
        }
    }

    @Override
    public void remove(String vehicleBarcode, String vehicleReg) {
        if (checkVehiclePresentByBarcode(vehicleBarcode) && checkVehiclePresentByBarcode(vehicleReg)) {
            cars.remove(vehicleBarcode);
            System.out.println("Car with barcode " + vehicleBarcode + " and registration " + vehicleReg + " has been left the car park.");
        }
        else {
            System.out.println("Invalid vehicle details provided. We do not have a record of this vehicle in the car park.");
        }

    }

    @Override
    public boolean checkVehiclePresentByBarcode(String vehicleBarcode) {
        return cars.containsKey(vehicleBarcode);
    }

    @Override
    public boolean checkVehiclePresentByReg(String vehicleReg) {
        return cars.containsValue(vehicleReg);
    }

    @Override
    public void printAllVehicles() {
        System.out.println(cars);
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

    //TODO - methods to link in w file handling?? or add to existing methods to add to log??
}
