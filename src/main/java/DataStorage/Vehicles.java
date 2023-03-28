package DataStorage;

import Exceptions.RecordCannotBeAdded;

interface Vehicles {

    void add (String vehicleReg, String vehicleBarcode) throws RecordCannotBeAdded;

    void remove(String vehicleReg, String vehicleBarcode);

    String getRegistrationByBarcode (String vehicleBarcode);

    String getBarcodeFromVehicleReg (String vehicleReg);

    void printAll ();

    boolean deleteAllVehicles ();

    Integer numberOfRecords ();
}
