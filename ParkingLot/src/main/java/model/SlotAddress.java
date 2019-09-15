package model;

import java.util.Objects;

public class SlotAddress {
    private Integer floorNumber;
    private Integer slotNumber;

    public SlotAddress(Integer floorNumber, Integer slotNumber) {
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotAddress that = (SlotAddress) o;
        return floorNumber.equals(that.floorNumber) &&
                slotNumber.equals(that.slotNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorNumber, slotNumber);
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    @Override
    public String toString() {
        return "SlotAddress{" +
                "floorNumber=" + floorNumber +
                ", slotNumber=" + slotNumber +
                '}';
    }
}