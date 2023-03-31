package Models;

public class CarParkBarrier extends Barrier {

    private String[] barrierOptions = {"lowered", "raised"};
    private Integer barrierStatus; // 0 = lowered, 1 = raised

    private String barrierLocation;

    public CarParkBarrier(String barrierLocation) {
        this.barrierStatus = 0;
        this.barrierLocation = barrierLocation;
    }

    @Override
    public void raise() {
        this.barrierStatus = 1;
        printStatus();
    }

    @Override
    public void lower() {
        this.barrierStatus = 0;
        printStatus();
    }

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