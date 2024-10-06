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
                    currentUser.processOperation(choice);
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
    }

    // Implement other methods for system operations here
    // For example: addVehicle, removeVehicle, addPort, removePort, etc.

    // Example method:
    public void addVehicle(Vehicle vehicle) {
        if (currentUser instanceof SystemAdmin) {
            vehicles.add(vehicle);
            saveData();
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("You don't have permission to add vehicles.");
        }
    }

    // Implement other methods similarly
}