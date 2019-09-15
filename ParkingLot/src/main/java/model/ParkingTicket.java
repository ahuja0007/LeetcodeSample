package model;

import enums.TicketStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ParkingTicket {
    private Timestamp parkTime;
    private Slot slot;
    private TicketStatus ticketStatus;

    public ParkingTicket(Slot slot){
        this.parkTime = new Timestamp(new Date().getTime());
        this.ticketStatus = TicketStatus.ACTIVE;
        this.slot = slot;
    }

    public Long minutesElapsed() {
        return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - parkTime.getTime());
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setTicketStatusPaid() {
        this.ticketStatus = TicketStatus.PAID;
    }
}