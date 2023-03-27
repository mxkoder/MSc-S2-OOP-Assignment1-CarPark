import FileHandling.CarLogFile;
import DataStorage.Cars;
import FileHandling.MembersFile;

public class MainCarParkDemo {
    public static void main(String[] args) {

////        IDReaderBarcode barcodeReader = new IDReaderBarcode();
////
////        barcodeReader.readID();
//
////        //----------------reg reader----------
////        // TODO - test reg plate ID reader
////        IDReaderRegistration regReader = new IDReaderRegistration();
////
////        regReader.readID();
////
////        System.out.println("reg plate recorded is: " + regReader.getID());
//
//        //--------------- log file
//
//        CarLogFile carLog1 = new CarLogFile("carLog1.csv");
//        carLog1.createLogFile();
//
//        //------------cars list----------------------------------
//        Cars cars = new Cars();
//
//        cars.add("768886", "thy7hj");
//        carLog1.recordArrival("768886", "thy7hj");
//
//        cars.add("123", "abc");
//        carLog1.recordArrival("123", "abc");
//
//        cars.add("7654886", "tyuy7hj");
//        carLog1.recordArrival("7654886", "tyuy7hj");
//
//        cars.add("7872030886", "tyuy7abcggehj");
//        carLog1.recordArrival("7872030886", "tyuy7abcggehj");
//
//        cars.add("76521246", "tyurety");
//        carLog1.recordArrival("76521246", "tyurety");
//
//        cars.printAll();
//
//        cars.remove("123", "abc");
//        carLog1.recordDeparture("123", "abc");
//
//        cars.printAll();
////
////        System.out.println("search for barcode, present: " + cars.checkVehiclePresentByBarcode("768886"));
////        System.out.println("search for barcode,  not present: " + cars.checkVehiclePresentByBarcode("778687686868886"));
////
////        System.out.println("search for reg, present: " + cars.checkVehiclePresentByReg("thy7hj"));
////        System.out.println("search for reg, not present: " + cars.checkVehiclePresentByReg("thy7gyyjbhj"));
////
////        cars.deleteVehicles();
////
////        System.out.println("vehicle list is now: " );
////        cars.printVehicles();
//
//        //--------------check log file
//
//        carLog1.printFileToConsole();
//
//        Cars restoredCars = new Cars();
//
//
//        carLog1.populateHashFromFile(restoredCars);
//
//
//        //------------comparison check of restored records and previous records-------------
//        cars.printAll();
//        restoredCars.printAll();


        //-------------Check Member's list--------------------------------------------------

        Cars carMembers = new Cars();

        MembersFile carMembersFile = new MembersFile("car_park_members.csv");

        // carMembersFile.createLogFile();

        carMembersFile.populateHashFromFile(carMembers);

        carMembers.printAll();
    }

//------pseudo code from Brief------------------------------
//     public class source {
//         public static void main(String[] args) {
//             cCarPark NCP = new cCarPark();
//             for (int i = 0; i < 10; i++) {
//                 NCP.update();
//                 System.out.println(i + ": polling carpark components and reacting to state");
//                 try {
//                     System.in.read();
//                 }
//                 catch (Exception e) {
//                 }
//             }
//         }
//     }
//
//    //cCarPark.java
//    public class cCarPark {
//        public void update() {
//            //Place your carpark update logic here.
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
//        }
//    }


    // -----guidance
    // lambdas
    // ternary operator
    // file handling
    // generics
    // ternary operator??
    // lambdas??

    //----file handling
    // need logging
//-------------------END OF BRIEF----------------------------------------------------


}


//------pseudo code from Brief - BACKUP------------------------------
//     public class source {
//         public static void main(String[] args) {
//             cCarPark NCP = new cCarPark();
//             for (int i = 0; i < 10; i++) {
//                 NCP.update();
//                 System.out.println(i + ": polling carpark components and reacting to state");
//                 try {
//                     System.in.read();
//                 }
//                 catch (Exception e) {
//                 }
//             }
//         }
//     }
//
//    //cCarPark.java
//    public class cCarPark {
//        public void update() {
//            //Place your carpark update logic here.
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
//        }
//    }
//-------------------END OF BRIEF----------------------------------------------------