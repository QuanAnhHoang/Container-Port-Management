package com.models;

import com.interfaces.VehicleInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements VehicleInterface, Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected double currentFuel;
    protected double fuelCapacity;
    protected int carryingCapacity;
    protected Port currentPort;
    protected List<Container> containers;

    public Vehicle(String id, String name, double fuelCapacity, int carryingCapacity) {
        this.id = id;
        this.name = name;
        this.currentFuel = fuelCapacity;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.containers = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCurrentFuel() {
        return currentFuel;
    }

    @Override
    public double getFuelCapacity() {
        return fuelCapacity;
    }

    @Override
    public int getCarryingCapacity() {
        return carryingCapacity;
    }

    @Override
    public Port getCurrentPort() {
        return currentPort;
    }

    @Override
    public void setCurrentPort(Port port) {
        this.currentPort = port;
    }

    @Override
    public List<Container> getContainers() {
        return new ArrayList<>(containers);
    }

    @Override
    public void addContainer(Container container) {
        if (getTotalContainerWeight() + container.getWeight() <= carryingCapacity) {
            containers.add(container);
        } else {
            throw new IllegalStateException("Vehicle carrying capacity exceeded");
        }
    }

    @Override
    public void removeContainer(Container container) {
        containers.remove(container);
    }

    @Override
    public boolean canMove(Port destination) {
        double requiredFuel = calculateRequiredFuel(destination);
        return currentFuel >= requiredFuel;
    }

    @Override
    public void move(Port destination) {
        if (!canMove(destination)) {
            throw new IllegalStateException("Insufficient fuel to move to the destination");
        }
        double requiredFuel = calculateRequiredFuel(destination);
        currentFuel -= requiredFuel;
        if (currentPort != null) {
            currentPort.removeVehicle(this);
        }
        destination.addVehicle(this);
        setCurrentPort(destination);
    }

    @Override
    public void refuel(double amount) {
        if (currentFuel + amount > fuelCapacity) {
            currentFuel = fuelCapacity;
        } else {
            currentFuel += amount;
        }
    }

    public abstract double calculateRequiredFuel(Port destination);

    protected double getTotalContainerWeight() {
        return containers.stream().mapToDouble(Container::getWeight).sum();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setCarryingCapacity(int carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

}