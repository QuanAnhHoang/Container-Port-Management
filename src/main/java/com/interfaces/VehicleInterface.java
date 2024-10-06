package com.interfaces;

import com.models.Container;
import com.models.Port;
import java.util.List;

public interface VehicleInterface {
    String getId();
    String getName();
    double getCurrentFuel();
    double getFuelCapacity();
    int getCarryingCapacity();
    Port getCurrentPort();
    void setCurrentPort(Port port);
    List<Container> getContainers();
    void addContainer(Container container);
    void removeContainer(Container container);
    boolean canMove(Port destination);
    void move(Port destination);
    void refuel(double amount);
    double calculateRequiredFuel(Port destination);
}