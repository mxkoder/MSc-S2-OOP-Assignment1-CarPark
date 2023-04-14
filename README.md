Java version: 17.05

# Car Park Operation Program

## About
This is a Java OOP program designed to manage the operation of a car park. 
The core functionality is to manage vehicles entering and leaving the car park, operating sensors, bariers and ID readers to allow access and record data.
The is a file handling system with a log file to restore data in the event of program failure. 

## Dependencies
Junit is used to implement unit tests with Mockito.
Java version 17.05 is used in the program.

## Running the project
The project runs from:
src/main/java/MainCarParkDemo.java

You may need to run 'maven clean install' before running MainCarParkDemo.java.

The unit tests can be run from src/test/java . 


## Operating the program

A demonstration of the program running is available in this video:
[Program demo video](https://uowtsd-my.sharepoint.com/:v:/g/personal/2222151_student_uwtsd_ac_uk/EXyDYnp3-MZBqnCRMDjM0RgBZ2UauT2gQcF_aI9zdfXPpQ?e=t7Ly3y)

When the Car Park program is run, the user will be presented with a selection of options in the Main Menu:

    ----------Main Car Park Menu----------
    Please choose an option:
    
    Primary project function:
    1) Operate car park checking for cars arriving and departing
       Additional options:
       2) Print list of cars currently in Car Park to console
      3) Print list of car park members to console
      4) Print car park log file to console
      5) Restore live list of cars in Car Park from log file: carLogFile.csv
      6) Restore live list of Car Park members from members file: car_park_members.csv
      7) Clear the contents of log file: carLogFile.csv
      8) Print Car Park status
      9) Exit program

Type in an integer to select a menu option and hit 'enter'.

Option 1 is the core operation of the program, and lets the user manage a vehicle entering and leaving the car park.
An example of the expected console output is shown below. 

    Please select an option:
    1
    
    Would you like to continue to operate the car park and poll the exit and entrance?
    Please enter y or n:
    y
    You have chosen to continue to operate the car park.
    
    Is there a car at the entrance barrier?
    Please enter y or n:
    y
    The sensor at the entrance barrier has been updated as detecting a car.
    
    ----------ID Reader Menu----------
    Please choose an option:
    1) Read vehicle barcode
    2) Read vehicle registration
    3) Exit to continue operating car park
    Please select an option:
          2
    
    Please enter a valid UK car registration number: ab12abc
    The value of the registration reader has been set to AB12ABC
    Confirmed as car park member.
    We have retrieved the barcode 123456789012 from the car park members list for registration number AB12ABC
    The value of the barcode reader has been set to 123456789012
    
    Car park entrance update:
    Car with barcode 123456789012 and registration AB12ABC has been added.
    Arrival of vehicle was recorded in the log file with barcode: 123456789012 and registration: AB12ABC
    The entrance barrier is raised
    Vehicle enters car park.
    The entrance barrier is lowered
    
    ----------Resetting car park devices----------
    The barcode reader has been reset to default and any previous recorded id has been cleared.
    The registration reader has been reset to default and any previous recorded id has been cleared.
    The full sign is switched OFF.
    
    -----------------------------------------------

The above cycle is then repeated for a vehicle leaving the car park, and the user can exit the cycle when they choose.


## Setup Options

The program in it's current state will run with a car park capacity of 100 vehicles. 
A default list of members with barcode - registration data entries is loaded from car_park_members.csv, edit this file to change the default members list.   

To start the program at near full capacity, comment 'Option 1' and uncomment 'Option 2' at the top of src/main/java/MainCarParkDemo.java,
or alternatively create a custom configuration.

    //Option 1: Start with an empty car park
    CarPark carPark = new CarPark(100);
                try {
                    carPark.setSpacesAvailable(100);
                } catch (InvalidAvailabilityAndCapacityException e) {
                    e.printStackTrace();
                }
    
    //        //Option 2: Start with a near full car park
    //        CarPark carPark = new CarPark(6);
    //        try {
    //            carPark.setSpacesAvailable(5);
    //        }
    //        catch (InvalidAvailabilityAndCapacityException e) {
    //            e.printStackTrace();
    //        }


## Author

Agnes Beviz