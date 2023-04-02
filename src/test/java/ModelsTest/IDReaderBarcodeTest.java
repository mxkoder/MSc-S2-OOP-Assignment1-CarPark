package ModelsTest;

import static org.junit.jupiter.api.Assertions.*;

import Models.IDReaderBarcode;
import org.junit.jupiter.api.Test;

public class IDReaderBarcodeTest {

    //todo - console input test??
    //mock enterValueForStringWithPrompt
    // similar to: when(mockSpacesAvailable.intValue()).thenReturn(0);
//
//    @Test
//    public void testReadID() {
//        IDReaderBarcode reader = spy(new IDReaderBarcode(""));
//        doReturn("123456789012").when(reader).enterValueForStringWithPrompt(anyString());
//        reader.readID();
//        assertEquals("123456789012", reader.getID());
//    }

    @Test
    public void testGetID() {
        IDReaderBarcode reader = new IDReaderBarcode("123456789012");
        assertEquals("123456789012", reader.getID());
    }

    @Test
    public void testSetIDWithValidInput() {
        IDReaderBarcode reader = new IDReaderBarcode("");
        reader.setID("123456789012");
        assertEquals("123456789012", reader.getID());
    }

    @Test
    public void testSetIDWithInvalidInput() {
        IDReaderBarcode reader = new IDReaderBarcode("");

        reader.setID("123");
        assertEquals("", reader.getID());

        reader.setID("1234567890123");
        assertEquals("", reader.getID());

        reader.setID("AGFHGJDJ");
        assertEquals("", reader.getID());
    }

    @Test
    public void testResetToDefault() {
        IDReaderBarcode reader = new IDReaderBarcode("123456789012");
        reader.resetToDefault();
        assertEquals("", reader.getID());
    }

    //TODO check tests below---------------
//    @Test
//    public void testRecordBarcodeIfCorrectFormatWithValidInput() {
//        IDReaderBarcode reader = new IDReaderBarcode("");
//        boolean result = reader.recordBarcodeIfCorrectFormat("123456789012");
//        assertTrue(result);
//        assertEquals("123456789012", reader.getID());
//    }
//
//    @Test
//    public void testRecordBarcodeIfCorrectFormatWithInvalidInput() {
//        IDReaderBarcode reader = new IDReaderBarcode("");
//        boolean result = reader.recordBarcodeIfCorrectFormat("123");
//        assertFalse(result);
//        assertEquals("", reader.getID());
//        result = reader.recordBarcodeIfCorrectFormat("1234567890123");
//        assertFalse(result);
//        assertEquals("", reader.getID());
//    }
}
