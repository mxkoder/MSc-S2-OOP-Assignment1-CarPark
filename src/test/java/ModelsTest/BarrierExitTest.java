package ModelsTest;
import Models.BarrierExit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class BarrierExitTest {

    private BarrierExit barrier;

    @Before
    public void setUp() {
        barrier = new BarrierExit();
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
    public void testSetExitBarrierWithCar() {
        barrier.setExitBarrier(true);
        assertEquals(1, barrier.barrierStatus);
    }

    @Test
    public void testSetExitBarrierWithoutCar() {
        barrier.setExitBarrier(false);
        assertEquals(0, barrier.barrierStatus);
    }
}

