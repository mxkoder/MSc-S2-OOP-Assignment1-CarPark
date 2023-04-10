package DataStorageTest;

import static org.junit.Assert.*;

import Exceptions.VehicleAtExitWasNotRecordedEntering;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import DataStorage.Cars;
import FileHandling.CarLogFile;
import Exceptions.RecordCannotBeAdded;
import CarPark.CarPark;

@RunWith(MockitoJUnitRunner.class)
public class CarsTest {

    @Mock
    private Cars mockCars;

    @Mock
    private CarPark mockCarPark;

    private CarLogFile carLogFile;

    private String carLogFileName = "testCarLogFile.csv";

    @Before
    public void setUp() {
        mockCars = new Cars();
    }

    @Test
    public void testAdd () throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";

        mockCars.add(barcode, registration);

        assertEquals(barcode, mockCars.getBarcodeFromVehicleReg(registration));
        assertEquals(barcode, mockCars.getBarcodeFromVehicleReg(registration));
        assertEquals(Integer.valueOf(1), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testAddMultiple () throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);

        assertEquals(Integer.valueOf(3), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testAddThrowsException () throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AB12ABC";

        mockCars.add(barcode, registration);

        assertThrows(RecordCannotBeAdded.class, ()-> {mockCars.add(barcode1, registration1);});
        assertEquals(Integer.valueOf(1), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testRemove () throws VehicleAtExitWasNotRecordedEntering, RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";

        mockCars.add(barcode, registration);
        mockCars.remove(barcode, registration);

        assertEquals(Integer.valueOf(0), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testRemoveMultiple () throws VehicleAtExitWasNotRecordedEntering, RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        mockCars.remove(barcode, registration);
        mockCars.remove(barcode1, registration1);
        mockCars.remove(barcode2, registration2);

        assertEquals(Integer.valueOf(1), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testRemoveThrowsException () throws VehicleAtExitWasNotRecordedEntering, RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";

        mockCars.add(barcode, registration);

        assertThrows(VehicleAtExitWasNotRecordedEntering.class, ()-> {mockCars.remove(barcode1, registration1);});
        assertEquals(Integer.valueOf(1), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testGetRegistrationByBarcode() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        assertEquals(barcode, mockCars.getBarcodeFromVehicleReg(registration));
        assertEquals(barcode1, mockCars.getBarcodeFromVehicleReg(registration1));
        assertEquals(barcode2, mockCars.getBarcodeFromVehicleReg(registration2));
        assertEquals(barcode3, mockCars.getBarcodeFromVehicleReg(registration3));
    }

    @Test
    public void testGetBarcodeByRegistration() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        assertEquals(registration, mockCars.getRegistrationByBarcode(barcode));
        assertEquals(registration1, mockCars.getRegistrationByBarcode(barcode1));
        assertEquals(registration2, mockCars.getRegistrationByBarcode(barcode2));
        assertEquals(registration3, mockCars.getRegistrationByBarcode(barcode3));
    }

    @Test
    public void testPrintAll() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        System.out.println("TEST----> print out of all vehicles in Cars dynamic data storage");
        mockCars.printAll();

        //Output to console as expected:
//        TEST----> print out of all vehicles in Cars dynamic data storage
//        Barcode: 123456782222, Registration: AB12AAA
//        Barcode: 123456784222, Registration: AB12BAA
//        Barcode: 111156789012, Registration: AA12ABC
//        Barcode: 123456789012, Registration: AB12ABC
    }

    @Test
    public void testNumberOfRecords() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        assertEquals(Integer.valueOf(1), Integer.valueOf(mockCars.numberOfRecords()));

        mockCars.add(barcode1, registration1);
        assertEquals(Integer.valueOf(2), Integer.valueOf(mockCars.numberOfRecords()));

        mockCars.add(barcode2, registration2);
        assertEquals(Integer.valueOf(3), Integer.valueOf(mockCars.numberOfRecords()));

        mockCars.add(barcode3, registration3);
        assertEquals(Integer.valueOf(4), Integer.valueOf(mockCars.numberOfRecords()));
    }

    @Test
    public void testVehicleIsFoundByBarcode() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        assertTrue(mockCars.vehicleIsFoundByBarcode(barcode));
        assertTrue(mockCars.vehicleIsFoundByBarcode(barcode1));
        assertTrue(mockCars.vehicleIsFoundByBarcode(barcode2));
        assertTrue(mockCars.vehicleIsFoundByBarcode(barcode3));
        assertFalse(mockCars.vehicleIsFoundByBarcode("111111111111"));
    }

    @Test
    public void testVehicleIsFoundByReg() throws RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        String barcode1 = "111156789012";
        String registration1 = "AA12ABC";
        String barcode2 = "123456782222";
        String registration2 = "AB12AAA";
        String barcode3 = "123456784222";
        String registration3 = "AB12BAA";

        mockCars.add(barcode, registration);
        mockCars.add(barcode1, registration1);
        mockCars.add(barcode2, registration2);
        mockCars.add(barcode3, registration3);

        assertTrue(mockCars.vehicleIsFoundByReg(registration));
        assertTrue(mockCars.vehicleIsFoundByReg(registration1));
        assertTrue(mockCars.vehicleIsFoundByReg(registration2));
        assertTrue(mockCars.vehicleIsFoundByReg(registration3));
        assertFalse(mockCars.vehicleIsFoundByReg("CC11CCC"));
    }
}
