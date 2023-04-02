package ModelsTest;

import Models.IDReaderRegistration;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class IDReaderRegistrationTest {

    @Test
    public void testReadIDWithCorrectFormat() {
        IDReaderRegistration reader = new IDReaderRegistration("");
        System.setIn(new ByteArrayInputStream("AB12 CDE\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        reader.readID();
        String prompt = "\nPlease enter a valid UK car registration number: ";
        assertEquals(prompt + "The value of the barcode reader has been set to AB12CDE\n", outContent.toString());
        assertEquals("AB12CDE", reader.getID());
    }

//    @Test
//    public void testReadIDWithIncorrectFormat() {
//        IDReaderRegistration reader = new IDReaderRegistration("");
//        System.setIn(new ByteArrayInputStream("123ABC\nABCD\n".getBytes()));
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        reader.readID();
//        assertEquals("Invalid registration format, please make sure it is in the standard UK for of @@##@@@\n" +
//                "\n" +
//                "Invalid registration format, please make sure it is in the standard UK for of @@##@@@\n" +
//                "\n", outContent.toString());
//        assertEquals("", reader.getID());
//    }
//
//    @Test
//    public void testGetID() {
//        IDReaderRegistration reader = new IDReaderRegistration("AB12CDE");
//        assertEquals("AB12CDE", reader.getID());
//    }
//
//    @Test
//    public void testSetIDWithCorrectFormat() {
//        IDReaderRegistration reader = new IDReaderRegistration("");
//        reader.setID("AB12CDE");
//        assertEquals("AB12CDE", reader.getID());
//    }
//
//    @Test
//    public void testSetIDWithIncorrectFormat() {
//        IDReaderRegistration reader = new IDReaderRegistration("AB12CDE");
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        reader.setID("123ABC");
//        assertEquals("Invalid registration format, please make sure it is in the standard UK for of @@##@@@\n", outContent.toString());
//        assertEquals("AB12CDE", reader.getID());
//    }
//
//    @Test
//    public void testResetToDefault() {
//        IDReaderRegistration reader = new IDReaderRegistration("AB12CDE");
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        reader.resetToDefault();
//        assertEquals("The registration reader has been reset to default and any previous recorded id has been cleared.\n", outContent.toString());
//        assertEquals("", reader.getID());
//    }
}

