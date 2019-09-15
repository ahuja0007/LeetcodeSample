package impl;

import enums.SlotType;
import interfaces.IParkingFeeStrategy;
import model.ParkingTicket;

import java.util.HashMap;
import java.util.Map;

import static enums.SlotType.*;

/*
    Parking Base charge is 3$ per hour for the first 3 hours and for each extra hour you'll be charged 1$.
    Based on the spot Type, cost will be scaled based on the configured cost factor. It could be anything depending
    upon what is asked of you in interview.
*/
public class DefaultPaymentImpl implements IParkingFeeStrategy {

    private final Map<SlotType, Double> costFactorMap = new HashMap<>() {{
        put(SMALL, 1D);
        put(MEDIUM, 1.2D);
        put(LARGE, 1.5D);
    }};

    @Override
    public Double calculateFee(ParkingTicket parkingTicket) {
        Long elapsedHours, totalMinutesElapsed;
        Double residualHour, basePrice;

        basePrice = 0D;
        totalMinutesElapsed = parkingTicket.minutesElapsed();
        elapsedHours = totalMinutesElapsed / 60;
        residualHour = (totalMinutesElapsed - (elapsedHours * 60))/60.0;

        if(elapsedHours >= 1)
            basePrice = (elapsedHours / 24) * 30 + Math.min(elapsedHours % 24, 3) * 3 + Math.max(elapsedHours % 24 - 3, 0) + 0D;
        basePrice += residualHour * ((elapsedHours <= 3) ?  3 : 1);

        return basePrice * costFactorMap.get(parkingTicket.getSlot().getSlotType());
    }
}
