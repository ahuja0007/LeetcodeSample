package Driver;

import enums.SlotType;
import exceptions.NoSlotFoundException;
import impl.DefaultPaymentImpl;
import impl.FloorBuilderImpl;
import impl.MultiStoreyParkingLotImpl;
import interfaces.IParkingLotManagement;
import model.Vehicle;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


/*
    Simple Driver Class to test the functionality of our implemented classes.
 */
public class ParkingLotManagement {

    IParkingLotManagement parkingLot;

    // Initialize the Parking Lot
    public void initializeParkingLotSimulation() {
        parkingLot = new MultiStoreyParkingLotImpl(new FloorBuilderImpl(), new DefaultPaymentImpl(), 20);
        parkingLot.initializeParkingLot();
    }

    public void startParkingLotSimulation() throws InterruptedException {

        SlotType[] slotTypes = new SlotType[]{SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE};
        Set<Vehicle> parkedVehicles = new HashSet<>();
        Random rand = new Random();

        parkingLot.publishStats();
        // Create Random Traffic in Parking Lot
        try {
            for(int i=0; i < 12; i++) {
                Vehicle vehicle = new Vehicle(UUID.randomUUID().toString(), slotTypes[rand.nextInt(3)]);
                parkingLot.parkVehicle(vehicle);
                parkedVehicles.add(vehicle);
            }
        }catch (NoSlotFoundException ex){
            System.out.println('\n' + "Parking Lot Full. Aborting all the vehicles in queue" + '\n');
        }

        parkingLot.publishStats();

        // Start Un-Parking the vehicles from the Lot at random intervals
        for(Vehicle parkedVehicle : parkedVehicles) {
            Thread.sleep(rand.nextInt(2000) + 2000);
            parkedVehicle.unpark();
        }

        // All spots should be empty
        parkingLot.publishStats();
    }
}
