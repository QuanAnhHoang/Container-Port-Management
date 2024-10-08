package com.interfaces;

import com.models.Container;
import com.models.Vehicle;
import com.models.Trip;
import com.models.Port;
import java.util.List;

/**
 * Interface defining the common methods for Port objects.
 */
public interface PortInterface {

    /**
     * Gets the ID of the port.
     * @return The port ID.
     */
    String getId();

    /**
     * Gets the name of the port.
     * @return The port name.
     */
    String getName();

    /**
     * Gets the latitude of the port.
     * @return The port latitude.
     */
    double getLatitude();

    /**
     * Gets the longitude of the port.
     * @return The port longitude.
     */
    double getLongitude();

    /**
     * Gets the storing capacity of the port.
     * @return The port storing capacity.
     */
    int getStoringCapacity();

    /**
     * Checks if the port has landing ability.
     * @return True if the port has landing ability, false otherwise.
     */
    boolean hasLandingAbility();

    /**
     * Gets the current number of containers in the port.
     * @return The container count.
     */
    int getContainerCount();

    /**
     * Gets the current number of vehicles in the port.
     * @return The vehicle count.
     */
    int getVehicleCount();

    /**
     * Gets the list of current trips associated with the port.
     * @return The list of current trips.
     */
    List<Trip> getCurrentTraffic();

    /**
     * Gets the list of past trips associated with the port.
     * @return The list of past trips.
     */
    List<Trip> getPastTraffic();

    /**
     * Adds a container to the port.
     * @param container The container to add.
     */
    void addContainer(Container container);

    /**
     * Removes a container from the port.
     * @param container The container to remove.
     */
    void removeContainer(Container container);

    /**
     * Adds a vehicle to the port.
     * @param vehicle The vehicle to add.
     */
    void addVehicle(Vehicle vehicle);

    /**
     * Removes a vehicle from the port.
     * @param vehicle The vehicle to remove.
     */
    void removeVehicle(Vehicle vehicle);

    /**
     * Calculates the distance between this port and another port.
     * @param otherPort The other port.
     * @return The distance between the two ports.
     */
    double calculateDistance(Port otherPort);
}