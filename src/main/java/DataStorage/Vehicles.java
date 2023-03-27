package DataStorage;

interface Vehicles {

    void add(String vehicleReg, String vehicleBarcode);

    void remove(String vehicleReg, String vehicleBarcode);

    String getRegistrationByBarcode (String vehicleBarcode);

    String getBarcodeFromVehicleReg (String vehicleReg);

    void printAll ();

    boolean deleteAllVehicles ();

    Integer numberOfRecords ();
}
