import java.io.Serializable;

public class Container implements ContainerInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private double weight;
    private ContainerType type;

    public enum ContainerType {
        DRY_STORAGE(3.5, 4.6),
        OPEN_TOP(2.8, 3.2),
        OPEN_SIDE(2.7, 3.2),
        REFRIGERATED(4.5, 5.4),
        LIQUID(4.8, 5.3);

        private final double shipConsumption;
        private final double truckConsumption;

        ContainerType(double shipConsumption, double truckConsumption) {
            this.shipConsumption = shipConsumption;
            this.truckConsumption = truckConsumption;
        }

        public double getShipConsumption() {
            return shipConsumption;
        }

        public double getTruckConsumption() {
            return truckConsumption;
        }
    }

    public Container(String id, double weight, ContainerType type) {
        this.id = id;
        this.weight = weight;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public double calculateFuelConsumption(boolean isShip, double distance) {
        double consumptionRate = isShip ? type.getShipConsumption() : type.getTruckConsumption();
        return (weight / 1000) * consumptionRate * distance; // weight in tons
    }
}