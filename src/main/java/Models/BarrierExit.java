package Models;

public class BarrierExit extends Barrier {

    String[] barrierOptions = {"lowered", "raised"};
    public Integer barrierStatus; // 0 = lowered, 1 = raised

    public BarrierExit() {
        this.barrierStatus = 0;
    }

    @Override
    public void raise() {
        this.barrierStatus = 1;
        System.out.println("The exit barrier is " + barrierOptions[barrierStatus]);
    }

    @Override
    public void lower() {
        this.barrierStatus = 0;
        System.out.println("The exit barrier is " + barrierOptions[barrierStatus]);
    }

    public void setExitBarrier (boolean carAtExitSensor) {
        if (carAtExitSensor) {
            barrierStatus = 1;
        }
        else {
            barrierStatus = 0;
        }

    }
}
