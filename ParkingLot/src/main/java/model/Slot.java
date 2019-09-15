package model;

import enums.SlotType;

import java.util.Objects;

// A Slot will be uniquely identified by SlotAddress
public class Slot {
    private SlotAddress slotAddress;
    private SlotType slotType;
    private Vehicle assignedVehicle;

    public Slot(SlotAddress slotAddress, SlotType slotType) {
        this.slotAddress = slotAddress;
        this.slotType = slotType;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public boolean isOccupied() {
        return assignedVehicle != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return slotAddress.equals(slot.slotAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotAddress);
    }

    public SlotAddress getSlotAddress() {
        return slotAddress;
    }

    public SlotType getSlotType() {
        return slotType;
    }

}
