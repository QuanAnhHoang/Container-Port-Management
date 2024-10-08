package com.interfaces;

import com.models.Container;

/**
 * Interface defining the common methods for Container objects.
 */
public interface ContainerInterface {

    /**
     * Gets the ID of the container.
     * @return The container ID.
     */
    String getId();

    /**
     * Gets the weight of the container.
     * @return The container weight.
     */
    double getWeight();

    /**
     * Gets the type of the container.
     * @return The container type.
     */
    Container.ContainerType getType();

    /**
     * Calculates the fuel consumption for transporting the container.
     *
     * @param isShip   True if the container is being transported by ship, false otherwise.
     * @param distance The distance the container is being transported.
     * @return The fuel consumption.
     */
    double calculateFuelConsumption(boolean isShip, double distance);
}