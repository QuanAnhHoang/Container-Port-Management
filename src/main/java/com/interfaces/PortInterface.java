package com.interfaces;

import com.models.Container;
import com.models.Vehicle;
import com.models.Trip;
import com.models.Port;
import java.util.List;

public interface PortInterface {
    String getId();
    String getName();
    double getLatitude();
    double getLongitude();
    int getStoringCapacity();
    boolean hasLandingAbility();
    int getContainerCount();
    int getVehicleCount();
    List<Trip> getCurrentTraffic();
    List<Trip> getPastTraffic();
    void addContainer(Container container);
    void removeContainer(Container container);
    void addVehicle(Vehicle vehicle);
    void removeVehicle(Vehicle vehicle);
    double calculateDistance(Port otherPort);
}