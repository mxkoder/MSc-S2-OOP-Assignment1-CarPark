package OperationHelpersTest;

import DataStorage.Cars;
import Exceptions.InvalidAvailabilityAndCapacityException;
import Exceptions.IsFull;
import FileHandling.MembersFile;
import Models.CarPark;
import Models.IDReaderBarcode;
import Models.IDReaderRegistration;
import OperationHelpers.CarIDReaderMenu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarIDReaderMenuTest {

    @Mock
    private IDReaderBarcode mockBarcodeReader;

    @Mock
    private IDReaderRegistration mockRegReader;
//
//    @Mock
//    private Cars mockCarMembers;
//
//    @Mock
//    private MembersFile mockCarMembersFile;
//
////    @Mock
////    private CarIDReaderMenu mockCarIDReaderMenu;
//
    @Before
    public void setUp() {
        mockBarcodeReader = new IDReaderBarcode("");
        mockRegReader = new IDReaderRegistration("");
//        mockCarMembers = new Cars();
//        mockCarMembersFile = new MembersFile("test_CarIDReaderMenuFile");
        //mockCarIDReaderMenu = new CarIDReaderMenu();
    }

    @Test
    public void testCheckIfIsCarParkMemberUsingBarcode () {
        Cars mockCarMembers1 = mock(Cars.class);
        when(mockCarMembers1.vehicleIsFoundByReg(anyString())).thenReturn(true);
        assertTrue(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers1));
    }

    @Test
    public void testCheckIfIsCarParkMemberUsingReg () {
        Cars mockCarMembers1 = mock(Cars.class);
        when(mockCarMembers1.vehicleIsFoundByBarcode(anyString())).thenReturn(true);
        assertTrue(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers1));
    }

    @Test
    public void testCheckIfIsCarParkMemberNotMember () {
        Cars mockCarMembers1 = mock(Cars.class);
        when(mockCarMembers1.vehicleIsFoundByBarcode(anyString())).thenReturn(false);
        when(mockCarMembers1.vehicleIsFoundByReg(anyString())).thenReturn(false);
        assertFalse(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers1));
    }

}
