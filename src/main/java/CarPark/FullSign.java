package CarPark;

public class FullSign {

    private boolean fullSignIsOn;

    public FullSign(boolean fullSignIsOn) {
        this.fullSignIsOn = fullSignIsOn;
    }

    public void switchOn () {
        this.fullSignIsOn = true;
        System.out.println("The full sign is switched ON.");
    }

    public void switchOff () {
        this.fullSignIsOn = false;
        System.out.println("The full sign is switched OFF.");
    }

    //TODO java doc returns true if on, false if off
    public boolean update (Integer spacesAvailable) {
        if (spacesAvailable > 0) {
            switchOff();
        }
        else {
            switchOn();
        }
        return fullSignIsOn;
    }

    //TODO add javadoc
    public boolean getStatus () {
        return this.fullSignIsOn;
    }
}

