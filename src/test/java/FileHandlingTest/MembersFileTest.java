package FileHandlingTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import FileHandling.MembersFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import DataStorage.Cars;
import Exceptions.IsFull;
import Exceptions.RecordCannotBeAdded;

@RunWith(MockitoJUnitRunner.class)
public class MembersFileTest {

    @Mock
    private Cars mockCarParkMembers;

    private MembersFile membersFile;

    private String membersFilename = "testMembersFile.cvs";

    @Before
    public void setUp() {
        membersFile = new MembersFile(membersFilename);
        membersFile.clearFileContentsOnlyForTest();
    }

    @Test
    public void testGetFilename() {
        assertEquals(membersFilename, membersFile.getFileName());
    }

    @Test
    public void testAddMember() throws IOException {
        String barcode = "123456789012";
        String registration = "AB12ABC";

        membersFile.addMember(barcode, registration);

        String expected = barcode + "," + registration;
        BufferedReader reader = new BufferedReader(new FileReader(membersFilename));
        String actual = reader.readLine();
        reader.close();
        assertEquals(expected, actual);
    }

    @Test
    public void testPrintFileToConsole() {
        String barcode1 = "123456789012";
        String registration1 = "AB12ABC";
        String barcode2 = "432156789012";
        String registration2 = "AB12AAA";

        membersFile.addMember(barcode1, registration1);
        membersFile.addMember(barcode2, registration2);

        membersFile.printFileToConsole();

        //Console output is as expected:
//        ---------Printout of car park members list file with name: testMembersFile.cvs---------
//        123456789012,AB12ABC
//        432156789012,AB12AAA
    }

    @Test
    public void testPopulateHashFromFileMethodInvocations() throws IOException, RecordCannotBeAdded {
        String barcode = "123456789012";
        String registration = "AB12ABC";
        BufferedWriter writer = new BufferedWriter(new FileWriter(membersFilename, true));
        writer.append(barcode + "," + registration);
        writer.append("\n");
        writer.close();
        membersFile.populateHashFromFile(mockCarParkMembers);
        verify(mockCarParkMembers, times(1)).add(barcode, registration.toUpperCase());
    }
}
