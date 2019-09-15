package interfaces;

import enums.SlotType;
import model.ParkingFloor;

public interface IParkingLotFloorBuilder {
    ParkingFloor buildHomogeneousFloor(SlotType slotType, int numSlots, int floorNumber);

    ParkingFloor buildHeterogeneousFloor(int maxSlots, int floorNumber);
}