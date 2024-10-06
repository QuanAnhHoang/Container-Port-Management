import java.util.*;
import java.time.LocalDate;

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

    // Implement other methods for system operations here

}