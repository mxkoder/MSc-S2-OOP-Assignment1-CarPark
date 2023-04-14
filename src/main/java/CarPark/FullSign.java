package CarPark;

public class FullSign {

    private boolean fullSignIsOn;

    public FullSign(boolean fullSignIsOn) {
        this.fullSignIsOn = fullSignIsOn;
    }

    public void switchOn() {
        this.fullSignIsOn = true;
        System.out.println("The full sign is switched ON.");
    }

    public void switchOff() {
        this.fullSignIsOn = false;
        System.out.println("The full sign is switched OFF.");
    }

    /**
     * Method to update the FullSign based on the number of spaces available in the car park.
     * <p>The will sign will be switched ON if there are spaces available, and off if the car park is full.</p>
     *
     * @param spacesAvailable Integer value of the number of spaces available in the car park.
     * @return Boolean - returns 'true' if the full sign is ON, and 'false' if the full sign is OFF.
     */
    public boolean update(Integer spacesAvailable) {
        if (spacesAvailable > 0) {
            switchOff();
        } else {
            switchOn();
        }
        return fullSignIsOn;
    }

    /**
     * Method to get the status of the car park FullSign
     *
     * @return Boolean - returns 'true' if the full sign is ON, and 'false' if the full sign is OFF.
     */
    public boolean getStatus() {
        return this.fullSignIsOn;
    }
}

