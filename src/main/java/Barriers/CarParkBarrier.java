package Barriers;

public class CarParkBarrier extends Barrier {

    private String[] barrierOptions = {"lowered", "raised"};
    private Integer barrierStatus; // 0 = lowered, 1 = raised

    private String barrierLocation;

    /**
     * Constructor for CarParkBarrier
     * <p>The default initial status of the barrier is lowered (barrierStatus = 0)</p>
     * <p>Setting the location of the barrier as a string will allow it to be identified as e.g an 'exit' or 'entrance' barrier</p>
     * @param barrierLocation String identifying the location of the barrier, eg 'exit' or 'entrance'
     */
    public CarParkBarrier(String barrierLocation) {
        this.barrierStatus = 0;
        this.barrierLocation = barrierLocation;
    }

    @Override
    public void raise() {
        this.barrierStatus = 1; // when barrierStatus = 1, the barrier is raised
        printStatus();
    }

    @Override
    public void lower() {
        this.barrierStatus = 0;// when barrierStatus = 0, the barrier is lowered
        printStatus();
    }

    /**
     * Method to print the location and status (raised or lowered) of the car park barrier.
     */
    @Override
    public void printStatus() {
        System.out.println("The " + barrierLocation + " barrier is " + barrierOptions[barrierStatus]);
    }

    @Override
    public String getLocation() {
        return this.barrierLocation;
    }

    @Override
    public Integer getStatus() {
        return this.barrierStatus;
    }
}