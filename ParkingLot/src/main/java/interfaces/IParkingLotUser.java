package interfaces;

import model.ParkingTicket;
import model.Vehicle;

/*
    Set of API to be implemented by a parking lot. A vehicle entering in the parking lot should be able to call these.
    Vehicle should be able to decide when to un-park. We are using interface segregation technique here to restrict
    vehicles calling api's from management side.
*/
public interface IParkingLotUser {
    void unparkVehicle(Vehicle parkedVehicle);

    // Will provide a feature to provide a parking ticket in case it is lost by a vehicle.
    ParkingTicket getLostParkingTicket(Vehicle parkedVehicle);
}