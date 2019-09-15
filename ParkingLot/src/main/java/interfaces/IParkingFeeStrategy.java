package interfaces;

import exceptions.InvalidTicketException;
import model.ParkingTicket;

public interface IParkingFeeStrategy {
    Double calculateFee(ParkingTicket parkingTicket) throws InvalidTicketException;
}