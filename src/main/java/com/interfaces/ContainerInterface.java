package com.interfaces;

import com.models.Container;

public interface ContainerInterface {
    String getId();
    double getWeight();
    Container.ContainerType getType();
    double calculateFuelConsumption(boolean isShip, double distance);
}