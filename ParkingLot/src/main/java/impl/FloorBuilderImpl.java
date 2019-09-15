package impl;

import enums.SlotType;
import interfaces.IParkingLotFloorBuilder;
import model.ParkingFloor;
import model.Slot;
import model.SlotAddress;

import java.util.ArrayList;
import java.util.List;


/*
    This class is responsible for constructing the floors of a Parking Lot. For now, we are considering each floor of
    our multi-storey parking lot to be composed of homogeneous slots. This constraint can be relaxed in future as second
    function can be used to construct a floor having hybrid allocation of slots.
*/
public class FloorBuilderImpl implements IParkingLotFloorBuilder {
    @Override
    public ParkingFloor buildHomogeneousFloor(SlotType slotType, int numSlots, int floorNumber) {
        List<Slot> slotList = new ArrayList<>();

        for(int slotId = 0; slotId < numSlots; slotId++) {
            slotList.add(new Slot(new SlotAddress(floorNumber, slotId), slotType));
        }
        return new ParkingFloor(slotList, floorNumber, slotType);
    }

    // TODO : An Exercise to have Hybrid Construction
    @Override
    public ParkingFloor buildHeterogeneousFloor(int maxSlots, int floorNumber) {
        return null;
    }
}
