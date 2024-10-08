package com.interfaces;

import com.models.Container;
import com.models.Port;
import java.util.List;

/**
 * Interface defining the common methods for Vehicle objects.
 */
public interface VehicleInterface {

    /**
     * Gets the ID of the vehicle.
     * @return The vehicle ID.
     */
    String getId();

    /**
     * Gets the name of the vehicle.
     * @return The vehicle name.
     */
    String getName();

    /**
     * Gets the current fuel level of the vehicle.
     * @return The current fuel level.
     */
    double getCurrentFuel();

    /**
     * Gets the fuel capacity of the vehicle.
     * @return The fuel capacity.
     */
    double getFuelCapacity();

    /**
     * Gets the carrying capacity of the vehicle.
     * @return The carrying capacity.
     */
    int getCarryingCapacity();

    /**
     * Gets the current port where the vehicle is located.
     * @return The current port.
     */
    Port getCurrentPort();

    /**
     * Sets the current port of the vehicle.
     * @param port The new current port.
     */
    void setCurrentPort(Port port);

    /**
     * Gets the list of containers currently carried by the vehicle.
     * @return The list of containers.
     */
    List<Container> getContainers();

    /**
     * Adds a container to the vehicle.
     * @param container The container to add.
     */
    void addContainer(Container container);

    /**
     * Removes a container from the vehicle.
     * @param container The container to remove.
     */
    void removeContainer(Container container);

    /**
     * Checks if the vehicle can move to the specified destination port.
     * This considers factors like fuel level and carrying capacity.
     * @param destination The destination port.
     * @return True if the vehicle can move, false otherwise.
     */
    boolean canMove(Port destination);

    /**
     * Moves the vehicle to the specified destination port.
     * @param destination The destination port.
     */
    void move(Port destination);

    /**
     * Refuels the vehicle by the specified amount.
     * @param amount The amount of fuel to add.
     */
    void refuel(double amount);

    /**
     * Calculates the required fuel to reach the specified destination port.
     * @param destination The destination port.
     * @return The required fuel.
     */
    double calculateRequiredFuel(Port destination);
}