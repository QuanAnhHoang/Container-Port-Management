public class Ship extends Vehicle {
    private static final double FUEL_CONSUMPTION_RATE = 3.5; // gallons per ton per km

    public Ship(String id, String name, double fuelCapacity, int carryingCapacity) {
        super(id, name, fuelCapacity, carryingCapacity);
    }

    @Override
    protected double calculateRequiredFuel(Port destination) {
        if (currentPort == null) {
            throw new IllegalStateException("Ship is not at any port");
        }
        double distance = currentPort.calculateDistance(destination);
        double totalWeight = getTotalContainerWeight();
        return distance * totalWeight * FUEL_CONSUMPTION_RATE / 1000; // Convert to tons
    }

    @Override
    public boolean canMove(Port destination) {
        return super.canMove(destination);
    }
}