package enums;


// Currently Our Parking Lot Impl supports Bikes, cars, mini vans, RUV etc
public enum SlotType {
    SMALL, MEDIUM, LARGE;

    private Integer size;
    static {
        SMALL.size = 0;
        MEDIUM.size = 1;
        LARGE.size = 2;
    }

    public Integer getSize(){
        return size;
    }
}