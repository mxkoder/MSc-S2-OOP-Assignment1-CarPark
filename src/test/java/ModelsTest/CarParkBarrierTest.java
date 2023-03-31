package ModelsTest;
import Models.CarParkBarrier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class CarParkBarrierTest {
    private CarParkBarrier barrier;

    @Before
    public void setUp() {
        barrier = new CarParkBarrier("test");
    }

    @Test
    public void testDefaultBarrierStatus() {
        assertEquals(0, barrier.getStatus());
    }


    @Test
    public void testRaiseBarrier() {
        barrier.raise();
        assertEquals(1, barrier.getStatus());
    }

    @Test
    public void testLowerBarrier() {
        barrier.lower();
        assertEquals(0, barrier.getStatus());
    }

    @Test
    public void testGetLocation() {
        assertEquals("test", barrier.getLocation());
    }


}
