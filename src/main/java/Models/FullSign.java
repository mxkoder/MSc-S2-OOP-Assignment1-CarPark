package Models;

public class FullSign {

    private boolean fullSignIsOn;

    public FullSign(boolean fullSignIsOn) {
        this.fullSignIsOn = fullSignIsOn;
    }

    public void switchOn () {
        this.fullSignIsOn = true;
    }

    public void switchOff () {
        this.fullSignIsOn = false;
    }

    //TODO java doc returns true if on, false if off
    public boolean update (Integer spacesAvailable) {
        if (spacesAvailable > 0) {
            fullSignIsOn = false;
        }
        else {
            fullSignIsOn = true;
        }
        return fullSignIsOn;
    }
}
