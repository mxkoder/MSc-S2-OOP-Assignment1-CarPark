package DataStorage;

interface Vehicles {

    void add(String vehicleReg, String vehicleBarcode);

    void remove(String vehicleReg, String vehicleBarcode);

    boolean checkVehiclePresentByBarcode (String vehicleBarcode);

    boolean checkVehiclePresentByReg (String vehicleReg);

    void printAllVehicles ();

    boolean deleteAllVehicles ();

    Integer numberOfRecords ();
}
