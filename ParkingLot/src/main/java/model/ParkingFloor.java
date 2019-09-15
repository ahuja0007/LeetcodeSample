package model;

import enums.SlotType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ParkingFloor {
    private Set<Slot> emptySlots;
    private Set<Slot> occupiedSlots;
    private Integer floorNumber;
    private SlotType floorType;

    public ParkingFloor(List<Slot> parkingSlots, Integer floorNumber, SlotType floorType){
        this.emptySlots = new HashSet<> (parkingSlots);
        this.occupiedSlots = new HashSet<>();
        this.floorNumber = floorNumber;
        this.floorType = floorType;
    }

    public Integer getEmptySlotCount() {
        return emptySlots.size();
    }

    public Slot getEmptySlot(SlotType slotType) {
        Slot toOccupy = null;

        if(floorType.getSize() >= slotType.getSize() && !emptySlots.isEmpty()) {
            toOccupy = emptySlots.iterator().next();
            occupiedSlots.add(toOccupy);
            emptySlots.remove(toOccupy);
        }
        return toOccupy;
    }

    public void claimOccupiedSlot(Slot slot) {
        if(slot.isOccupied()){
            emptySlots.add(slot);
            occupiedSlots.remove(slot);
            slot.setAssignedVehicle(null);
        }
    }

    public SlotType getFloorType() {
        return floorType;
    }
}
