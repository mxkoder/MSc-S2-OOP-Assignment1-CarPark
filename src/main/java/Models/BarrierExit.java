package Models;

public class BarrierExit extends Barrier {

    String[] barrierOptions = {"raised", "lowered"};
    Integer barrierStatus = 1; // 1 = raised, 0 = lowered

    public BarrierExit(Integer barrierStatus) {
        this.barrierStatus = barrierStatus;
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
