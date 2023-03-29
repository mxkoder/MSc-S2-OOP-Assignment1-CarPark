package ModelsTest;
import Models.BarrierEntry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class BarrierEntryTest {

    private BarrierEntry barrier;

    @Before
    public void setUp() {
        barrier = new BarrierEntry();
    }

    @Test
    public void testDefaultBarrierStatus() {
        assertEquals(0, barrier.barrierStatus);
    }

    @Test
    public void testRaiseBarrier() {
        barrier.raise();
        assertEquals(1, barrier.barrierStatus);
    }

    @Test
    public void testLowerBarrier() {
        barrier.lower();
        assertEquals(0, barrier.barrierStatus);
    }

    @Test
    public void testUpdateBarrierStatus() {
        barrier.update(5);
        assertEquals(1, barrier.barrierStatus);
    }

    @Test
    public void testUpdateBarrierStatusZeroSpaces() {
        barrier.update(0);
        assertEquals(0, barrier.barrierStatus);
    }
}