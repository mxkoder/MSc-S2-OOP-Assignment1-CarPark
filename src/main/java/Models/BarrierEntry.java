package Models;

public class BarrierEntry extends Barrier {

    String[] barrierOptions = {"raised", "lowered"};
    Integer barrierStatus = 1; // 1 = raised, 0 = lowered

    public BarrierEntry(Integer barrierStatus) {
        this.barrierStatus = barrierStatus;
    }

    @Override
    public void raise() {
        this.barrierStatus = 1;
        System.out.println("The barrier is " + barrierOptions[barrierStatus]);
    }

    @Override
    public void lower() {
        this.barrierStatus = 0;
        System.out.println("The barrier is " + barrierOptions[barrierStatus]);
    }

    @Override
    public boolean barrierIsRaised() {
        System.out.println("The barrier is " + barrierOptions[barrierStatus]);

        if(barrierStatus == 1) {
            return true;
        }
        else return false;
    }

    public void update(Integer spacesAvailable) {
        if (spacesAvailable > 0) {
            barrierStatus = 1;
        }
        else {
            barrierStatus = 0;
        }
    }
}