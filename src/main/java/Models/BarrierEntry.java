package Models;

public class BarrierEntry extends Barrier {

    String[] barrierOptions = {"lowered", "raised"};
    public Integer barrierStatus; // 0 = lowered, 1 = raised

    public BarrierEntry() {
        this.barrierStatus = 0;
    }

    @Override
    public void raise() {
        this.barrierStatus = 1;
        System.out.println("The entry barrier is " + barrierOptions[barrierStatus]);
    }

    @Override
    public void lower() {
        this.barrierStatus = 0;
        System.out.println("The entry barrier is " + barrierOptions[barrierStatus]);
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