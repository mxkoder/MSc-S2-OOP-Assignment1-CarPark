package Models;

public class BarrierExit extends Barrier {

    String[] barrierOptions = {"lowered", "raised"};
    Integer barrierStatus = 0; // 0 = lowered, 1 = raised

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

    @Override
    public boolean barrierIsRaised() {
        System.out.println("The exit barrier is " + barrierOptions[barrierStatus]);

        if(barrierStatus == 1) {
            return true;
        }
        else return false;
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
