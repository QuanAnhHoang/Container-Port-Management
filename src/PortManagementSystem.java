import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class PortManagementSystem {
    private List<User> users;
    private List<Port> ports;
    private List<Vehicle> vehicles;
    private List<Container> containers;
    private List<Trip> trips;
    private User currentUser;
    private Scanner scanner;

    public PortManagementSystem() {
        users = new ArrayList<>();
        ports = new ArrayList<>();
        vehicles = new ArrayList<>();
        containers = new ArrayList<>();
        trips = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadData();
    }

    private void loadData() {
        users = FileHandler.loadUsers();
        ports = FileHandler.loadPorts();
        vehicles = FileHandler.loadVehicles();
        containers = FileHandler.loadContainers();
        trips = FileHandler.loadTrips();

        if (users.isEmpty()) {
            users.add(new SystemAdmin("admin", "admin123"));
        }
    }

    private void saveData() {
        FileHandler.saveUsers(users);
        FileHandler.savePorts(ports);
        FileHandler.saveVehicles(vehicles);
        FileHandler.saveContainers(containers);
        FileHandler.saveTrips(trips);
    }

    public void start() {
        System.out.println("Welcome to the Port Management System");
        while (true) {
            if (currentUser == null) {
                login();
            } else {
                currentUser.displayMenu();
                String choice = scanner.nextLine();
                if (choice.equals("0")) {
                    logout();
                } else {
                    currentUser.processOperation(choice, this);
                }
            }
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
        saveData();
    }


    public void addManager() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to add managers.");
            return;
        }

        System.out.print("Enter manager username: ");
        String username = scanner.nextLine();
        System.out.print("Enter manager password: ");
        String password = scanner.nextLine();
        System.out.print("Enter port ID for the manager: ");
        String portId = scanner.nextLine();

        Port managedPort = ports.stream()
                .filter(p -> p.getId().equals(portId))
                .findFirst()
                .orElse(null);

        if (managedPort == null) {
            System.out.println("Port not found.");
            return;
        }

        PortManager newManager = new PortManager(username, password, managedPort);
        users.add(newManager);
        System.out.println("Port manager added successfully.");
        saveData();
    }

    public void removeManager() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to remove managers.");
            return;
        }

        System.out.print("Enter manager username to remove: ");
        String username = scanner.nextLine();

        User managerToRemove = users.stream()
                .filter(u -> u instanceof PortManager && u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (managerToRemove != null) {
            users.remove(managerToRemove);
            System.out.println("Port manager removed successfully.");
            saveData();
        } else {
            System.out.println("Port manager not found.");
        }
    }

    public void calculateFuelUsage() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        double totalFuelUsed = trips.stream()
                .filter(trip -> trip.getDepartureDate().equals(date) || 
                                (trip.getArrivalDate() != null && trip.getArrivalDate().equals(date)))
                .mapToDouble(trip -> {
                    Vehicle vehicle = trip.getVehicle();
                    Port arrivalPort = trip.getArrivalPort();
                    return vehicle.calculateRequiredFuel(arrivalPort);
                })
                .sum();

        System.out.printf("Total fuel used on %s: %.2f gallons\n", date, totalFuelUsed);
    }

    public void calculateContainerWeights() {
        Map<Container.ContainerType, Double> weights = new EnumMap<>(Container.ContainerType.class);

        for (Container.ContainerType type : Container.ContainerType.values()) {
            weights.put(type, 0.0);
        }

        for (Container container : containers) {
            weights.put(container.getType(), weights.get(container.getType()) + container.getWeight());
        }

        System.out.println("Total weight of containers by type:");
        for (Map.Entry<Container.ContainerType, Double> entry : weights.entrySet()) {
            System.out.printf("%s: %.2f tons\n", entry.getKey(), entry.getValue());
        }
    }



    public void listTripsOnDate() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        List<Trip> tripsOnDate = trips.stream()
                .filter(trip -> trip.getDepartureDate().equals(date) || 
                                (trip.getArrivalDate() != null && trip.getArrivalDate().equals(date)))
                .collect(Collectors.toList());

        if (tripsOnDate.isEmpty()) {
            System.out.println("No trips on the specified date.");
        } else {
            System.out.println("Trips on " + date + ":");
            for (Trip trip : tripsOnDate) {
                System.out.println(trip);
            }
        }
    }




    public void addVehicle() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to add vehicles.");
            return;
        }

        System.out.print("Enter vehicle type (ship/truck): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter vehicle ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter vehicle name: ");
        String name = scanner.nextLine();
        System.out.print("Enter fuel capacity: ");
        double fuelCapacity = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter carrying capacity: ");
        int carryingCapacity = Integer.parseInt(scanner.nextLine());

        Vehicle vehicle;
        if (type.equals("ship")) {
            vehicle = new Ship(id, name, fuelCapacity, carryingCapacity);
        } else if (type.equals("truck")) {
            System.out.print("Enter truck type (BASIC/REEFER/TANKER): ");
            Truck.TruckType truckType = Truck.TruckType.valueOf(scanner.nextLine().toUpperCase());
            vehicle = new Truck(id, name, fuelCapacity, carryingCapacity, truckType);
        } else {
            System.out.println("Invalid vehicle type.");
            return;
        }

        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully.");
        saveData();
    }

    public void removeVehicle() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to remove vehicles.");
            return;
        }

        System.out.print("Enter vehicle ID to remove: ");
        String id = scanner.nextLine();

        Vehicle vehicleToRemove = vehicles.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (vehicleToRemove != null) {
            vehicles.remove(vehicleToRemove);
            System.out.println("Vehicle removed successfully.");
            saveData();
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    public void addPort() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to add ports.");
            return;
        }

        System.out.print("Enter port ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter port name: ");
        String name = scanner.nextLine();
        System.out.print("Enter latitude: ");
        double latitude = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter longitude: ");
        double longitude = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter storing capacity: ");
        int storingCapacity = Integer.parseInt(scanner.nextLine());
        System.out.print("Does the port have landing ability? (true/false): ");
        boolean landingAbility = Boolean.parseBoolean(scanner.nextLine());

        Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
        ports.add(port);
        System.out.println("Port added successfully.");
        saveData();
    }

    public void removePort() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to remove ports.");
            return;
        }

        System.out.print("Enter port ID to remove: ");
        String id = scanner.nextLine();

        Port portToRemove = ports.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (portToRemove != null) {
            ports.remove(portToRemove);
            System.out.println("Port removed successfully.");
            saveData();
        } else {
            System.out.println("Port not found.");
        }
    }

    public void addContainer() {
        if (!(currentUser instanceof SystemAdmin) && !(currentUser instanceof PortManager)) {
            System.out.println("You don't have permission to add containers.");
            return;
        }

        System.out.print("Enter container ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter container weight: ");
        double weight = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter container type (DRY_STORAGE/OPEN_TOP/OPEN_SIDE/REFRIGERATED/LIQUID): ");
        Container.ContainerType type = Container.ContainerType.valueOf(scanner.nextLine().toUpperCase());

        Container container = new Container(id, weight, type);
        containers.add(container);

        if (currentUser instanceof PortManager) {
            PortManager portManager = (PortManager) currentUser;
            portManager.getManagedPort().addContainer(container);
        }

        System.out.println("Container added successfully.");
        saveData();
    }

    public void removeContainer() {
        if (!(currentUser instanceof SystemAdmin) && !(currentUser instanceof PortManager)) {
            System.out.println("You don't have permission to remove containers.");
            return;
        }

        System.out.print("Enter container ID to remove: ");
        String id = scanner.nextLine();

        Container containerToRemove = containers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (containerToRemove != null) {
            containers.remove(containerToRemove);
            if (currentUser instanceof PortManager) {
                PortManager portManager = (PortManager) currentUser;
                portManager.getManagedPort().removeContainer(containerToRemove);
            }
            System.out.println("Container removed successfully.");
            saveData();
        } else {
            System.out.println("Container not found.");
        }
    }

    public void loadContainer() {
        if (!(currentUser instanceof SystemAdmin) && !(currentUser instanceof PortManager)) {
            System.out.println("You don't have permission to load containers.");
            return;
        }

        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter container ID: ");
        String containerId = scanner.nextLine();

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);

        Container container = containers.stream()
                .filter(c -> c.getId().equals(containerId))
                .findFirst()
                .orElse(null);

        if (vehicle == null || container == null) {
            System.out.println("Vehicle or container not found.");
            return;
        }

        try {
            vehicle.addContainer(container);
            if (currentUser instanceof PortManager) {
                PortManager portManager = (PortManager) currentUser;
                portManager.getManagedPort().removeContainer(container);
            }
            System.out.println("Container loaded successfully.");
            saveData();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error loading container: " + e.getMessage());
        }
    }

    public void unloadContainer() {
        if (!(currentUser instanceof SystemAdmin) && !(currentUser instanceof PortManager)) {
            System.out.println("You don't have permission to unload containers.");
            return;
        }

        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter container ID: ");
        String containerId = scanner.nextLine();

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        Container container = vehicle.getContainers().stream()
                .filter(c -> c.getId().equals(containerId))
                .findFirst()
                .orElse(null);

        if (container == null) {
            System.out.println("Container not found on the vehicle.");
            return;
        }

        vehicle.removeContainer(container);
        if (currentUser instanceof PortManager) {
            PortManager portManager = (PortManager) currentUser;
            portManager.getManagedPort().addContainer(container);
        }
        System.out.println("Container unloaded successfully.");
        saveData();
    }

    public void moveVehicle() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to move vehicles.");
            return;
        }

        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter destination port ID: ");
        String portId = scanner.nextLine();

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);

        Port destinationPort = ports.stream()
                .filter(p -> p.getId().equals(portId))
                .findFirst()
                .orElse(null);

        if (vehicle == null || destinationPort == null) {
            System.out.println("Vehicle or port not found.");
            return;
        }

        if (vehicle.canMove(destinationPort)) {
            vehicle.move(destinationPort);
            System.out.println("Vehicle moved successfully.");
            saveData();
        } else {
            System.out.println("Vehicle cannot move to the specified port.");
        }
    }

    public void refuelVehicle() {
        if (!(currentUser instanceof SystemAdmin)) {
            System.out.println("You don't have permission to refuel vehicles.");
            return;
        }

        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter amount of fuel to add: ");
        double fuelAmount = Double.parseDouble(scanner.nextLine());

        Vehicle vehicle = vehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        vehicle.refuel(fuelAmount);
        System.out.println("Vehicle refueled successfully.");
        saveData();
    }





    public void listShipsInPort() {
        System.out.print("Enter port ID: ");
        String portId = scanner.nextLine();

        Port port = ports.stream()
                .filter(p -> p.getId().equals(portId))
                .findFirst()
                .orElse(null);

        if (port == null) {
            System.out.println("Port not found.");
            return;
        }

        List<Vehicle> shipsInPort = vehicles.stream()
                .filter(v -> v instanceof Ship && v.getCurrentPort() != null && v.getCurrentPort().equals(port))
                .collect(Collectors.toList());

        if (shipsInPort.isEmpty()) {
            System.out.println("No ships in the port.");
        } else {
            System.out.println("Ships in the port:");
            for (Vehicle ship : shipsInPort) {
                System.out.println(ship.getId() + " - " + ship.getName());
            }
        }
    }


    public void listTripsBetweenDates() {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        List<Trip> tripsBetweenDates = trips.stream()
                .filter(trip -> (trip.getDepartureDate().isEqual(startDate) || trip.getDepartureDate().isAfter(startDate)) &&
                                (trip.getArrivalDate() == null || trip.getArrivalDate().isEqual(endDate) || trip.getArrivalDate().isBefore(endDate)))
                .collect(Collectors.toList());

        if (tripsBetweenDates.isEmpty()) {
            System.out.println("No trips between the specified dates.");
        } else {
            System.out.println("Trips between " + startDate + " and " + endDate + ":");
            for (Trip trip : tripsBetweenDates) {
                System.out.println(trip);
            }
        }
    }
    
    public void updatePort(Port updatedPort) {
        // Find the port in the list and update it
        for (int i = 0; i < ports.size(); i++) {
            if (ports.get(i).getId().equals(updatedPort.getId())) {
                ports.set(i, updatedPort);
                break;
            }
        }
        saveData();
    }

    public void listAllPorts() {
        System.out.println("All Ports:");
        for (Port port : ports) {
            System.out.println(port);
        }
    }

    public void listAllVehicles() {
        System.out.println("All Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    public void listAllContainers() {
        System.out.println("All Containers:");
        for (Container container : containers) {
            System.out.println(container);
        }
    }

    public void listAllUsers() {
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getClass().getSimpleName());
        }
    }

    public void modifyPort() {
        System.out.print("Enter port ID to modify: ");
        String portId = scanner.nextLine();
        Port port = ports.stream().filter(p -> p.getId().equals(portId)).findFirst().orElse(null);
        if (port == null) {
            System.out.println("Port not found.");
            return;
        }
    
        System.out.println("Current port details:");
        System.out.println(port);
    
        System.out.println("Enter new details (press Enter to keep current value):");
    
        System.out.print("Name [" + port.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            port.setName(name);
        }
    
        System.out.print("Latitude [" + port.getLatitude() + "]: ");
        String latitudeStr = scanner.nextLine();
        if (!latitudeStr.isEmpty()) {
            double latitude = Double.parseDouble(latitudeStr);
            port.setLatitude(latitude);
        }
    
        System.out.print("Longitude [" + port.getLongitude() + "]: ");
        String longitudeStr = scanner.nextLine();
        if (!longitudeStr.isEmpty()) {
            double longitude = Double.parseDouble(longitudeStr);
            port.setLongitude(longitude);
        }
    
        System.out.print("Storing Capacity [" + port.getStoringCapacity() + "]: ");
        String capacityStr = scanner.nextLine();
        if (!capacityStr.isEmpty()) {
            int capacity = Integer.parseInt(capacityStr);
            port.setStoringCapacity(capacity);
        }
    
        System.out.print("Landing Ability [" + port.hasLandingAbility() + "]: ");
        String landingStr = scanner.nextLine();
        if (!landingStr.isEmpty()) {
            boolean landingAbility = Boolean.parseBoolean(landingStr);
            port.setLandingAbility(landingAbility);
        }
    
        System.out.println("Port modified successfully.");
        saveData();
    }
    
    public void modifyVehicle() {
        System.out.print("Enter vehicle ID to modify: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = vehicles.stream().filter(v -> v.getId().equals(vehicleId)).findFirst().orElse(null);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }
    
        System.out.println("Current vehicle details:");
        System.out.println(vehicle);
    
        System.out.println("Enter new details (press Enter to keep current value):");
    
        System.out.print("Name [" + vehicle.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            vehicle.setName(name);
        }
    
        System.out.print("Fuel Capacity [" + vehicle.getFuelCapacity() + "]: ");
        String fuelCapacityStr = scanner.nextLine();
        if (!fuelCapacityStr.isEmpty()) {
            double fuelCapacity = Double.parseDouble(fuelCapacityStr);
            vehicle.setFuelCapacity(fuelCapacity);
        }
    
        System.out.print("Carrying Capacity [" + vehicle.getCarryingCapacity() + "]: ");
        String carryingCapacityStr = scanner.nextLine();
        if (!carryingCapacityStr.isEmpty()) {
            int carryingCapacity = Integer.parseInt(carryingCapacityStr);
            vehicle.setCarryingCapacity(carryingCapacity);
        }
    
        if (vehicle instanceof Truck) {
            Truck truck = (Truck) vehicle;
            System.out.print("Truck Type [" + truck.getType() + "]: ");
            String truckTypeStr = scanner.nextLine();
            if (!truckTypeStr.isEmpty()) {
                Truck.TruckType truckType = Truck.TruckType.valueOf(truckTypeStr.toUpperCase());
                truck.setType(truckType);
            }
        }
    
        System.out.println("Vehicle modified successfully.");
        saveData();
    }
    
    public void modifyContainer() {
        System.out.print("Enter container ID to modify: ");
        String containerId = scanner.nextLine();
        Container container = containers.stream().filter(c -> c.getId().equals(containerId)).findFirst().orElse(null);
        if (container == null) {
            System.out.println("Container not found.");
            return;
        }
    
        System.out.println("Current container details:");
        System.out.println(container);
    
        System.out.println("Enter new details (press Enter to keep current value):");
    
        System.out.print("Weight [" + container.getWeight() + "]: ");
        String weightStr = scanner.nextLine();
        if (!weightStr.isEmpty()) {
            double weight = Double.parseDouble(weightStr);
            container.setWeight(weight);
        }
    
        System.out.print("Type [" + container.getType() + "]: ");
        String typeStr = scanner.nextLine();
        if (!typeStr.isEmpty()) {
            Container.ContainerType type = Container.ContainerType.valueOf(typeStr.toUpperCase());
            container.setType(type);
        }
    
        System.out.println("Container modified successfully.");
        saveData();
    }
    
    public void modifyUser() {
        System.out.print("Enter username to modify: ");
        String username = scanner.nextLine();
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
    
        System.out.println("Current user details:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("User Type: " + user.getClass().getSimpleName());
    
        System.out.println("Enter new details (press Enter to keep current value):");
    
        System.out.print("New Password: ");
        String password = scanner.nextLine();
        if (!password.isEmpty()) {
            user.setPassword(password);
        }
    
        if (user instanceof PortManager) {
            PortManager portManager = (PortManager) user;
            System.out.print("Managed Port ID [" + portManager.getManagedPort().getId() + "]: ");
            String portId = scanner.nextLine();
            if (!portId.isEmpty()) {
                Port newPort = ports.stream().filter(p -> p.getId().equals(portId)).findFirst().orElse(null);
                if (newPort != null) {
                    portManager.setManagedPort(newPort);
                } else {
                    System.out.println("Port not found. Managed port remains unchanged.");
                }
            }
        }
    
        System.out.println("User modified successfully.");
        saveData();
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public void createSampleData() {
        // Create 5 Ports
        Port port1 = new Port("P1", "New York Port", 40.7128, -74.0060, 1000000, true);
        Port port2 = new Port("P2", "Los Angeles Port", 34.0522, -118.2437, 1200000, true);
        Port port3 = new Port("P3", "Miami Port", 25.7617, -80.1918, 800000, true);
        Port port4 = new Port("P4", "Seattle Port", 47.6062, -122.3321, 900000, true);
        Port port5 = new Port("P5", "Houston Port", 29.7604, -95.3698, 1100000, true);
    
        ports.addAll(Arrays.asList(port1, port2, port3, port4, port5));
    
        // Create 20 Vehicles (10 Ships and 10 Trucks)
        Ship ship1 = new Ship("S1", "Maersk Sealand", 500000, 150000);
        Ship ship2 = new Ship("S2", "COSCO Shipping", 450000, 140000);
        Ship ship3 = new Ship("S3", "MSC Oscar", 550000, 160000);
        Ship ship4 = new Ship("S4", "CMA CGM Marco Polo", 480000, 145000);
        Ship ship5 = new Ship("S5", "OOCL Hong Kong", 520000, 155000);
        Ship ship6 = new Ship("S6", "Ever Golden", 490000, 148000);
        Ship ship7 = new Ship("S7", "HMM Algeciras", 530000, 158000);
        Ship ship8 = new Ship("S8", "MOL Triumph", 470000, 142000);
        Ship ship9 = new Ship("S9", "Madrid Maersk", 510000, 153000);
        Ship ship10 = new Ship("S10", "CSCL Globe", 460000, 138000);
    
        Truck truck1 = new Truck("T1", "Volvo FH", 1000, 30000, Truck.TruckType.BASIC);
        Truck truck2 = new Truck("T2", "Scania R500", 1100, 32000, Truck.TruckType.REEFER);
        Truck truck3 = new Truck("T3", "Mercedes-Benz Actros", 950, 28000, Truck.TruckType.TANKER);
        Truck truck4 = new Truck("T4", "MAN TGX", 1050, 31000, Truck.TruckType.BASIC);
        Truck truck5 = new Truck("T5", "DAF XF", 1000, 29000, Truck.TruckType.REEFER);
        Truck truck6 = new Truck("T6", "Iveco Stralis", 980, 27000, Truck.TruckType.TANKER);
        Truck truck7 = new Truck("T7", "Renault T", 1020, 30500, Truck.TruckType.BASIC);
        Truck truck8 = new Truck("T8", "Kenworth T680", 1150, 33000, Truck.TruckType.REEFER);
        Truck truck9 = new Truck("T9", "Peterbilt 579", 1100, 32500, Truck.TruckType.TANKER);
        Truck truck10 = new Truck("T10", "Freightliner Cascadia", 1080, 31500, Truck.TruckType.BASIC);
    
        vehicles.addAll(Arrays.asList(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10,
                truck1, truck2, truck3, truck4, truck5, truck6, truck7, truck8, truck9, truck10));
    
        // Assign vehicles to ports
        for (int i = 0; i < vehicles.size(); i++) {
            Port port = ports.get(i % ports.size());
            vehicles.get(i).setCurrentPort(port);
            port.addVehicle(vehicles.get(i));
        }
    
        // Create 30 Containers
        for (int i = 1; i <= 30; i++) {
            Container.ContainerType type = Container.ContainerType.values()[i % Container.ContainerType.values().length];
            double weight = 5000 + (Math.random() * 20000); // Random weight between 5000 and 25000
            Container container = new Container("C" + i, weight, type);
            containers.add(container);
    
            // Assign containers to ports
            Port port = ports.get(i % ports.size());
            port.addContainer(container);
        }
    
        // Create 25 Trips
        LocalDate startDate = LocalDate.now().minusDays(30);
        for (int i = 1; i <= 25; i++) {
            Vehicle vehicle = vehicles.get(i % vehicles.size());
            Port departurePort = ports.get(i % ports.size());
            Port arrivalPort = ports.get((i + 1) % ports.size());
            LocalDate departureDate = startDate.plusDays(i);
            LocalDate arrivalDate = departureDate.plusDays(2 + (int)(Math.random() * 5)); // Trip duration 2-7 days
    
            Trip trip = new Trip(vehicle, departureDate, departurePort, arrivalPort);
            trip.start();
            trip.complete(arrivalDate);
            trips.add(trip);
    
            // Update vehicle location
            vehicle.setCurrentPort(arrivalPort);
        }

        // Save all data
        saveData();
        System.out.println("Sample data created successfully.");
    }
}