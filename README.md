# Programming 1 (COSC2081) Assignment
![banner](Banner.png)

***

# Problem Details

As the global economy develops steadily, the amount of traffic between container ports is adding pressure to the ports to increase capacity. Besides physical expansion, the alternative solution to increasing capacity is via increased port performance. Therefore, a need for a digital management system is required to replace the traditional paper-based system.


For this project, we will implement a simulation of a port management system. The main objective
is to document the traffic of the vehicles between ports. The system can also manage
vehicles/ports/containers and keep track of all the history of the vehicles in the ports. All these
tasks can be executed only by the persons in charge.


Vehicles carry five types of containers: dry storage, open top, open side, refrigerated, and liquid.
Each of them must be handled differently. Containers in one port can be loaded to a vehicle, and
also they can be unloaded from a vehicle to a port. For a vehicle to move from one port to another
one, a certain amount of fuel is necessary.


A port must have a unique ID (formatted as p-number), along with its name, its latitude/longitude,
its storing capacity, and its landing ability (A truck can move between two ports only if both of them
have this ability). It must maintain the number of the containers (The total weights of containers
must not exceed the port storing capacity), the number of the vehicles, and keep track of the past
as well as the current traffic (Each trip must have the vehicle information, the departure/arrival
date, the port of departure/arrival, and its status). For the sake of simplicity, we can assume that
there is no limit to the number of ships in a port at one time. Also, a port must have the ability to
calculate the distance between itself and another port.


A vehicle must have its name, its current fuel, its carrying capacity, and its fuel capacity. The
vehicles must know their current port (null if sailaway), and must keep track of the total number of
containers (both in general and each type). There are two types of vehicles: ship and truck.
Although ships can carry all types of containers, only some specific types can be handled by
trucks. A basic truck can only carry dry storage, open top, and open side containers, while to carry
refrigerated containers we need to use reefer trucks, and to carry liquid containers we need to use
tanker trucks. Moreover, not all the trips can be handled by trucks. Only the ports that are marked
“landing” can utilize trucks for carrying. Also, each vehicle must have a unique ID (formatted as
sh-number and tr-number for ships and trucks, respectively).


To include the necessary operations for vehicles and ports, an interface for each class should be
implemented. This provides an abstract layer for the entities and makes them easier for scalability.


About containers, each must have its own unique ID (formatted as c-number) and weight. Also,
the containers must be able to provide the required fuel consumption (For the sake of simplicity,
assume that all the same type of vehicles share the same fuel consumption for the same
containers). As mentioned above, there are five types of containers in our system. Each type of
containers will consume different amount of fuel (in gallons), as follows:


| Type         | Per unit of weight per km (Ship) | Per unit of weight per km (Truck) |
|--------------|-----------------------------------|------------------------------------|
| Dry storage  | 3.5                              | 4.6                               |
| Open top     | 2.8                              | 3.2                               |
| Open side    | 2.7                              | 3.2                               |
| Refrigerated | 4.5                              | 5.4                               |
| Liquid       | 4.8                              | 5.3                               |


In our system, there will be two types of users: system admin and port manager. There is only one
admin, and each manager for each port. The users can login with the pre-defined
username/password and will have varied permissions based on their roles. For the port managers,
they can only process on the port they are in charge, while the system admin can access and
process all the information. A menu of operations must be provided, and the list of the available
options varies from user to user.


All data will be loaded from the input files initially, and any changes within the system must be
updated instantly in the files. Design the structure of the data in your files so that it can efficiently
keep track of your system. Also, provide sample data for testing (At least 20 Vehicles, 5 Ports, 30
Containers, and 25 Trips).


The application should have the following basic features (You can add more operations to the list if
suitable):

- Vehicles/Ports/Containers/Managers can be added to the system (Port managers can only handle Containers)
- Vehicles/Ports/Containers/Managers can be removed from the system (Port managers can only handle Containers)
- All the CRUD operations must be implemented (The parameters have to be suitable).
- A vehicle can load/unload a container.
- A vehicle must be able to determine whether it can successfully move to a port with its current load.
- A vehicle can move to a port.
- A vehicle can be refueled.
- A user can login with a predefined username and password, can view/modify the
information and process the corresponding entities (Ports/containers/vehicles for
managers, everything for the admin system).

A user can do some statistics operations:
- Calculate how much fuel has been used in a day
- Calculate how much weight of each type of all containers
- List all the ships in a port
- List all the trips in a given day
- List all the trips from day A to day B


Note that all the history is only kept for 7 days.

## Welcome Screen

When completing your application, it should have a welcome screen.

## Submission

- No external libraries can be used. 

- Your work will be tested with Java 20 JVM. 

- The entry point of your program (i.e. the main() method) must be in a file named Main.java. If I cannot start from Main, I will consider your program is not able to run. 

***

# Technical Aspects

## Project Structure

    Container-Port-Management/
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/
    │   │           ├── Main.java - The entry point of the program.
    │   │           ├── PortManagementSystem.java - Orchestrates the entire system.
    │   │           ├── interfaces/
    │   │           │   ├── ContainerInterface.java - Interface for Container operations.
    │   │           │   ├── PortInterface.java - Interface for Port operations.
    │   │           │   └── VehicleInterface.java - Interface for Vehicle operations.
    │   │           ├── models/
    │   │           │   ├── Container.java - Represents a container.
    │   │           │   ├── Port.java - Represents a port.
    │   │           │   ├── Trip.java - Represents a trip.
    │   │           │   ├── User.java - Abstract class for users.
    │   │           │   ├── Vehicle.java - Abstract class for vehicles.
    │   │           │   ├── Ship.java - Represents a ship.
    │   │           │   └── Truck.java - Represents a truck.
    │   │           ├── users/
    │   │           │   ├── SystemAdmin.java - Represents the system admin.
    │   │           │   └── PortManager.java - Represents the port manager.
    │   │           └── utils/
    │   │               └── FileHandler.java - Handles file operations.
    └── data/
        ├── containers.dat
        ├── ports.dat
        ├── trips.dat
        ├── users.dat
        └── vehicles.dat

## Classes and Interfaces

| Category          | Class/Interface      | Description                                                                  |
|-------------------|----------------------|------------------------------------------------------------------------------|
| **Core Model**       | `Container`          | Represents a shipping container.                                             |
|                    | `Port`               | Represents a port.                                                          |
|                    | `Vehicle (abstract)` | Abstract class representing a vehicle.                                       |
|                    | `Ship`               | Represents a ship (extends Vehicle).                                         |
|                    | `Truck`              | Represents a truck (extends Vehicle).                                        |
|                    | `Trip`               | Represents a trip.                                                           |
|                    | `User (abstract)`    | Abstract class representing a user.                                          |
| **User**             | `SystemAdmin`        | Represents a system administrator (extends User).                            |
|                    | `PortManager`        | Represents a port manager (extends User).                                   |
| **Utility**          | `FileHandler`        | Handles file operations.                                                    |
| **Main System**      | `PortManagementSystem` | The main class that orchestrates the system.                               |
| **Interfaces**       | `ContainerInterface` | Interface for container interactions.                                      |
|                    | `PortInterface`      | Interface for port interactions.                                            |
|                    | `VehicleInterface`  | Interface for vehicle interactions.                                         |


## OOP Principles Applied

a. Encapsulation
- Private attributes with public getter/setter methods
- Implementation details hidden within classes

b. Inheritance
- Vehicle -> Ship/Truck
- User -> SystemAdmin/PortManager

c. Polymorphism
- Vehicle's calculateRequiredFuel method is overridden in Ship and Truck
- User's abstract methods are implemented differently in SystemAdmin and PortManager

d. Abstraction:
- Abstract classes: Vehicle and User
- Interfaces: ContainerInterface, PortInterface, VehicleInterface

## Personal Contributions

### 1. Worked on fuel consumption calculation system adaptable to different vehicle and container types
- In the Vehicle abstract class, implemented the calculateRequiredFuel method, which is then overridden in the Ship and Truck subclasses to provide specific fuel consumption calculations.
- In the Ship class, implemented a constant FUEL_CONSUMPTION_RATE and used it in the calculateRequiredFuel method to calculate fuel consumption based on distance, total weight of containers, and the ship-specific consumption rate.
- In the Truck class, created an enum TruckType with different fuel consumption rates for each type (BASIC, REEFER, TANKER). The calculateRequiredFuel method in this class uses these type-specific rates to calculate fuel consumption.
- In the Container class, implemented a ContainerType enum with specific fuel consumption rates for both ships and trucks. The calculateFuelConsumption method in this class uses these rates to calculate fuel consumption based on the container type, weight, distance, and whether it's being transported by ship or truck.

### 2. Worked on distance calculation feature using the Haversine formula, enabling accurate fuel requirement estimations for trips between ports
- In the Port class, implemented the calculateDistance method, which uses the Haversine formula to calculate the great-circle distance between two ports based on their latitude and longitude coordinates.
- This method takes into account the Earth's radius and uses trigonometric functions to accurately calculate the distance between two points on a sphere (in this case, the Earth).
- The distance calculation is used in the fuel consumption calculations in both the Ship and Truck classes, allowing for precise estimations of fuel requirements for trips between ports.

***