package ModelsTest;

import Models.IDReaderBarcode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class IDReaderBarcodeTest {
    @Test
    public void testReadIDWithCorrectFormat() {
        IDReaderBarcode reader = new IDReaderBarcode("");
        System.setIn(new ByteArrayInputStream("123456789012\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        reader.readID();
        String prompt = "\nPlease enter the 12 digit barcode of the parking pass: ";
        assertEquals(prompt + "The value of the barcode reader has been set to 123456789012\n", outContent.toString());
        assertEquals("123456789012", reader.getID());
    }

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

        reader.setID("abc");
        assertEquals("", reader.getID());
    }

    @Test
    public void testResetToDefault() {
        IDReaderBarcode reader = new IDReaderBarcode("123456789012");
        reader.resetToDefault();
        assertEquals("", reader.getID());
    }

    @Test
    public void testRecordBarcodeIfCorrectFormatWithValidInput() {
        IDReaderBarcode reader = new IDReaderBarcode("");

        assertTrue(reader.recordBarcodeIfCorrectFormat("123456789012"));
        assertTrue(reader.recordBarcodeIfCorrectFormat("123456789333"));
        assertTrue(reader.recordBarcodeIfCorrectFormat("123456789333"));
        assertTrue(reader.recordBarcodeIfCorrectFormat("593028672917"));
    }

    @Test
    public void testRecordBarcodeIfCorrectFormatWithInvalidInput() {
        IDReaderBarcode reader = new IDReaderBarcode("");

        assertFalse(reader.recordBarcodeIfCorrectFormat("chsdkljjrioeluu908"));
        assertFalse(reader.recordBarcodeIfCorrectFormat("123"));
        assertFalse(reader.recordBarcodeIfCorrectFormat("abc"));
        assertFalse(reader.recordBarcodeIfCorrectFormat("1257897896772679674696"));
    }
}
