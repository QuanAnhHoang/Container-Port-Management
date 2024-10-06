package com.models;

public class Truck extends Vehicle {
    private static final long serialVersionUID = 1L;
    private TruckType type;

    public enum TruckType {
        BASIC(4.6),
        REEFER(5.4),
        TANKER(5.3);

        private final double fuelConsumptionRate;

        TruckType(double fuelConsumptionRate) {
            this.fuelConsumptionRate = fuelConsumptionRate;
        }

        public double getFuelConsumptionRate() {
            return fuelConsumptionRate;
        }
    }
    
    public void setType(TruckType type) {
        this.type = type;
    }

    public Truck(String id, String name, double fuelCapacity, int carryingCapacity, TruckType type) {
        super(id, name, fuelCapacity, carryingCapacity);
        this.type = type;
    }

    public TruckType getType() {
        return type;
    }

    @Override
    public double calculateRequiredFuel(Port destination) {
        if (currentPort == null) {
            throw new IllegalStateException("Truck is not at any port");
        }
        double distance = currentPort.calculateDistance(destination);
        double totalWeight = getTotalContainerWeight();
        return distance * totalWeight * type.getFuelConsumptionRate() / 1000; // Convert to tons
    }

    @Override
    public boolean canMove(Port destination) {
        return super.canMove(destination) && currentPort.hasLandingAbility() && destination.hasLandingAbility();
    }

    @Override
    public void addContainer(Container container) {
        if (canCarryContainerType(container)) {
            super.addContainer(container);
        } else {
            throw new IllegalArgumentException("This truck type cannot carry this container type");
        }
    }

    private boolean canCarryContainerType(Container container) {
        switch (type) {
            case BASIC:
                return container.getType() == Container.ContainerType.DRY_STORAGE ||
                       container.getType() == Container.ContainerType.OPEN_TOP ||
                       container.getType() == Container.ContainerType.OPEN_SIDE;
            case REEFER:
                return container.getType() == Container.ContainerType.REFRIGERATED;
            case TANKER:
                return container.getType() == Container.ContainerType.LIQUID;
            default:
                return false;
        }
    }
}