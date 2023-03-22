package Models;

public class CarPark implements PremiseCapacity {

    private Integer capacity;
    private Integer spacesAvailable;

    public CarPark(Integer capacity) {
        this.capacity = capacity;
    }

    public CarPark(Integer capacity, Integer spacesAvailable) {
        this.capacity = capacity;
        this.spacesAvailable = spacesAvailable;
    }

    @Override
    public void incrementSpacesAvailable() {
        this.spacesAvailable += 1;
    };

    @Override
    public void decrementSpacesAvailable() {
        this.spacesAvailable -= 1;
    };

    @Override
    public int getSpacesAvailable() {
        return this.spacesAvailable;
    };

    // TODO add update method
    @Override
    public void update() {

        //Place your carpark update logic here.
//            //The pseudo code illustrates some of the actions required
//            // in conjunction with other classes and will need further refinement.
//            //1.  poll car park components (sensor/etc).
//            //************************************************
//            //3.  if car present at entrance then
//            //4.    if ID valid && car park not full then
//            //5.      raise barrier and let car pass
//            //6.	  endif
//            //7.  endif
//            //************************************************
//            //9.  if car present at exit then
//            //10.   raise barrier and let car pass
//            //11.   update records and increment spaces
//            //12. endif
//            //************************************************
//            //14. if carpark full then
//            //15.   turn on full sign
//            //16: else
//            //17.   turn off full sign
//            //18. endif
//            //************************************************
//            //...and so on
    };

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}