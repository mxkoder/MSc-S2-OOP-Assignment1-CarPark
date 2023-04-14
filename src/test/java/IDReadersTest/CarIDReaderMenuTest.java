package IDReadersTest;

import DataStorage.Cars;
import IDReaders.IDReaderBarcode;
import IDReaders.IDReaderRegistration;
import IDReaders.CarIDReaderMenu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Before
    public void setUp() {
        mockBarcodeReader = new IDReaderBarcode("");
        mockRegReader = new IDReaderRegistration("");
    }

    @Test
    public void testCheckIfIsCarParkMemberUsingBarcode() {
        Cars mockCarMembers1 = mock(Cars.class);
        when(mockCarMembers1.vehicleIsFoundByReg(anyString())).thenReturn(true);
        assertTrue(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers1));
    }

    @Test
    public void testCheckIfIsCarParkMemberUsingReg() {
        Cars mockCarMembers = mock(Cars.class);
        when(mockCarMembers.vehicleIsFoundByBarcode(anyString())).thenReturn(true);
        assertTrue(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers));
    }

    @Test
    public void testCheckIfIsCarParkMemberNotMember() {
        Cars mockCarMembers = mock(Cars.class);
        when(mockCarMembers.vehicleIsFoundByBarcode(anyString())).thenReturn(false);
        when(mockCarMembers.vehicleIsFoundByReg(anyString())).thenReturn(false);
        assertFalse(CarIDReaderMenu.checkIfIsCarParkMember(mockBarcodeReader, mockRegReader, mockCarMembers));
    }
}
