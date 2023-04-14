package DataStorage;

import Exceptions.RecordCannotBeAdded;
import Exceptions.VehicleAtExitWasNotRecordedEntering;

interface Vehicles {

    void add(String vehicleReg, String vehicleBarcode) throws RecordCannotBeAdded;

    void remove(String vehicleReg, String vehicleBarcode) throws VehicleAtExitWasNotRecordedEntering;

    String getRegistrationByBarcode(String vehicleBarcode);

    String getBarcodeFromVehicleReg(String vehicleReg);

    void printAll();

    boolean deleteAll();

    Integer numberOfRecords();
}
