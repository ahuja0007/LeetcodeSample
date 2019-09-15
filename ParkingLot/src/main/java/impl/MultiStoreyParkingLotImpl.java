package impl;

import enums.SlotType;
import enums.TicketStatus;
import exceptions.InvalidTicketException;
import exceptions.NoSlotFoundException;
import interfaces.IParkingFeeStrategy;
import interfaces.IParkingLotManagement;
import interfaces.IParkingLotUser;
import interfaces.IParkingLotFloorBuilder;
import model.ParkingFloor;
import model.ParkingTicket;
import model.Slot;
import model.Vehicle;

import java.util.*;

public class MultiStoreyParkingLotImpl implements IParkingLotManagement, IParkingLotUser {

    private IParkingLotFloorBuilder floorBuilder;
    private IParkingFeeStrategy parkingFeeStrategy;
    private List<ParkingFloor> parkingFloors;
    private Integer numFloors;

    // Could be anything depending upon how you want to configure the floors based on traffic patterns in your area.
    private final Double SMALL_FLOORS = 0.2;
    private final Double MEDIUM_FLOORS = 0.5;

    // This Map will be used to validate the parking ticket and also, in case a ticket is lost.
    private Map<String, ParkingTicket> parkingTicketMap;

    public MultiStoreyParkingLotImpl(IParkingLotFloorBuilder floorBuilder, IParkingFeeStrategy parkingFeeStrategy, Integer numFloors) {
        if(numFloors < 10)
            throw new RuntimeException("Atleast 10 floors required");

        this.floorBuilder = floorBuilder;
        this.parkingFeeStrategy = parkingFeeStrategy;
        this.numFloors = numFloors;
    }

    // Use suitable factory impl to construct the floors of Parking lot. For now, Homogeneous floors only.
    @Override
    public void initializeParkingLot() {
        int smallFloors = (int) (numFloors * SMALL_FLOORS);
        int medFlooors = (int) (numFloors * MEDIUM_FLOORS);
        int largeFloors = numFloors - (smallFloors + medFlooors);

        this.parkingTicketMap = new HashMap<>();
        this.parkingFloors = new ArrayList<>();

        int floorId = 0;

        // Let the number of slots be inversely proportional to the size of slots.
        for (int floor = 0; floor < smallFloors; floor++) {
            parkingFloors.add(floorBuilder.buildHomogeneousFloor(SlotType.SMALL, 50, floorId++));
        }

        for (int floor = 0; floor < medFlooors; floor++) {
            parkingFloors.add(floorBuilder.buildHomogeneousFloor(SlotType.MEDIUM, 30, floorId++));
        }

        for (int floor = 0; floor < largeFloors; floor++) {
            parkingFloors.add(floorBuilder.buildHomogeneousFloor(SlotType.LARGE, 15, floorId++));
        }
    }

    // Search all the floors to find suitable slot for vehicle and generate a parking ticket.
    @Override
    public void parkVehicle(Vehicle vehicle) throws NoSlotFoundException {
        for (int floor = 0; floor < parkingFloors.size(); floor++) {
            Slot slot = parkingFloors.get(floor).getEmptySlot(vehicle.getVehicleType());
            if (slot != null) {
                vehicle.assignTicket(new ParkingTicket(slot), this);
                slot.setAssignedVehicle(vehicle);
                parkingTicketMap.put(vehicle.getVehicleNumber(), vehicle.getParkingTicket());
                vehicle.park();
                return;
            }
        }
        throw new NoSlotFoundException("No Slot Found for Vehicle : " + vehicle.getVehicleNumber());
    }

    // Display the stats for all the levels in Parking Lot to the incoming vehicles.
    @Override
    public void publishStats() {
        for (int floor = 0; floor < parkingFloors.size(); floor++) {
            System.out.println("Level : " + floor + " Empty Slots : " +
                    parkingFloors.get(floor).getEmptySlotCount() + " Slot Type : " + parkingFloors.get(floor).getFloorType().name());
        }
    }

    // TODO : Left as a Follow-Up for interested readers
    @Override
    public void publishEarnings(Date date) {
    }

    // Changing the parking fee strategy dynamically in future.
    @Override
    public void changePaymentStrategy(IParkingFeeStrategy parkingFeeStrategy) {
        this.parkingFeeStrategy = parkingFeeStrategy;
    }

    // Called by the vehicle when exiting the lot. Calculate the amount to be paid and re-claim the spot.
    @Override
    public void unparkVehicle(Vehicle parkedVehicle) throws InvalidTicketException {
        ParkingTicket parkingTicket = parkedVehicle.getParkingTicket();

        if (parkingTicket == null || parkingTicket.getTicketStatus() == TicketStatus.PAID ||
                !parkingTicket.getSlot().isOccupied() || parkingTicketMap.get(parkedVehicle.getVehicleNumber()) != parkingTicket) {
            throw new InvalidTicketException("Parking Ticket is Invalid. Please provide a valid one.");
        }

        Slot toBeFreed = parkingTicket.getSlot();
        parkedVehicle.payParkingCharges(parkingFeeStrategy.calculateFee(parkingTicket));
        parkingTicket.setTicketStatusPaid();
        parkingFloors.get(toBeFreed.getSlotAddress().getFloorNumber()).claimOccupiedSlot(toBeFreed);
        parkedVehicle.clear();
    }

    // TODO - As an Exercise. Keep in mind all the validations.
    @Override
    public ParkingTicket getLostParkingTicket(Vehicle parkedVehicle) {
        return null;
    }
}
