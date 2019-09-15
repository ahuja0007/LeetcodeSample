package model;

import enums.SlotType;
import interfaces.IParkingLotUser;

public class Vehicle {
    private String vehicleNumber;
    private SlotType vehicleType;

    private ParkingTicket parkingTicket;
    private IParkingLotUser parkingLot;

    public Vehicle(String vehicleNumber, SlotType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public ParkingTicket getParkingTicket() {
        return parkingTicket;
    }

    public SlotType getVehicleType() {
        return vehicleType;
    }

    public void assignTicket(ParkingTicket parkingTicket, IParkingLotUser parkingLot) {
        this.parkingTicket = parkingTicket;
        this.parkingLot = parkingLot;
    }

    public void payParkingCharges(Double amount) {
        System.out.println(String.format("%.2f", amount) + "$ paid by the vehicle with registration number : [" + getVehicleNumber() + "]");
    }

    public void park() {
        System.out.println("Vehicle with Registration number : [" +
                getVehicleNumber() + "] and type : "  + getVehicleType().name() + " parked at :" + getParkingTicket().getSlot().getSlotAddress());
    }

    public void unpark() {
        parkingLot.unparkVehicle(this);
    }

    public void clear() {
        this.parkingTicket = null;
        this.parkingLot = null;
    }
}
