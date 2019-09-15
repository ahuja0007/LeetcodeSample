package Driver;

public class Simulator {
    public static void main(String[] args) throws InterruptedException {
        ParkingLotManagement parkingLotManagement = new ParkingLotManagement();

        parkingLotManagement.initializeParkingLotSimulation();
        parkingLotManagement.startParkingLotSimulation();
    }
}
