package ModelsTest;

import Models.FullSign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FullSignTest {

    private Integer mockSpacesAvailable;

    @InjectMocks
    private FullSign fullSign1 = new FullSign(false);
    private FullSign fullSign2 = new FullSign(true);

    @Test
    public void testSwitchOn() {
        fullSign1.switchOn();
        assertTrue(fullSign1.getStatus());
    }

    @Test
    public void testSwitchOff() {
        fullSign2.switchOff();
        assertFalse(fullSign2.getStatus());
    }

    @Test
    public void testUpdateWhenSpacesAvailable() {
        mockSpacesAvailable = 2;
        assertFalse(fullSign1.update(mockSpacesAvailable));
    }

    @Test
    public void testUpdateWhenNoSpacesAvailable() {
        mockSpacesAvailable = 0;
        assertTrue(fullSign1.update(mockSpacesAvailable));
    }

    @Test
    public void testUpdateWhenNegativeSpacesAvailable() {
        mockSpacesAvailable = -10;
        assertTrue(fullSign1.update(mockSpacesAvailable));
    }

    @Test
    public void testGetStatus() {
        assertFalse(fullSign1.getStatus());
        assertTrue(fullSign2.getStatus());
    }
}
