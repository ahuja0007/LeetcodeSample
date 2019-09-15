package interfaces;

import exceptions.NoSlotFoundException;
import model.Vehicle;

import java.util.Date;



/*
    Set of API to be implemented by a parking lot. Only Parking Lot management will be calling these api's.
*/

public interface IParkingLotManagement {
    void parkVehicle(Vehicle vehicle) throws NoSlotFoundException;

    void initializeParkingLot();

    void publishStats();

    void publishEarnings(Date date);

    void changePaymentStrategy(IParkingFeeStrategy parkingFeeStrategy);
}
