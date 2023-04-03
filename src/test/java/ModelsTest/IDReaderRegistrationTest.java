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
        System.setIn(new ByteArrayInputStream("AB12 ABC\n".getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        reader.readID();
        String prompt = "\nPlease enter a valid UK car registration number: ";
        assertEquals(prompt + "The value of the registration reader has been set to AB12ABC\n", outContent.toString());
        assertEquals("AB12ABC", reader.getID());
    }

    @Test
    public void testGetID() {
        IDReaderRegistration reader = new IDReaderRegistration("AB12ABC");
        assertEquals("AB12ABC", reader.getID());
    }

    @Test
    public void testSetIDWithCorrectFormat() {
        IDReaderRegistration reader = new IDReaderRegistration("");
        reader.setID("AB12ABC");
        assertEquals("AB12ABC", reader.getID());
    }

    //TODO check below
    @Test
    public void testSetIDWithIncorrectFormat() {
        IDReaderRegistration reader = new IDReaderRegistration("");

        reader.setID("abc");
        assertEquals("", reader.getID());

        reader.setID("236");
        assertEquals("", reader.getID());

        reader.setID("rejhiwwxio");
        assertEquals("", reader.getID());

        reader.setID("AWERTUIOTRE");
        assertEquals("", reader.getID());
    }

    @Test
    public void testResetToDefault() {
        IDReaderRegistration reader = new IDReaderRegistration("AB12ABC");
        reader.resetToDefault();
        assertEquals("", reader.getID());
    }

    @Test
    public void testRecordRegistrationIfCorrectFormatWithValidInput() {
        IDReaderRegistration reader = new IDReaderRegistration("");

        assertTrue(reader.recordRegistrationIfCorrectFormat("ab12abc"));
        assertTrue(reader.recordRegistrationIfCorrectFormat("KE98LLL"));
        assertTrue(reader.recordRegistrationIfCorrectFormat("OP11klJ"));
        assertTrue(reader.recordRegistrationIfCorrectFormat("QW30UTE"));
    }

    @Test
    public void testRecordRegistrationIfCorrectFormatWithInvalidInput() {
        IDReaderRegistration reader = new IDReaderRegistration("");

        assertFalse(reader.recordRegistrationIfCorrectFormat("sadjkljlffh"));
        assertFalse(reader.recordRegistrationIfCorrectFormat("KE98LLL796"));
        assertFalse(reader.recordRegistrationIfCorrectFormat("OP11sawklJ"));
        assertFalse(reader.recordRegistrationIfCorrectFormat("QW30U"));
    }
}
