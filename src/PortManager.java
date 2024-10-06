import java.util.Arrays;
import java.util.Scanner;

public class PortManager extends User {
    private static final long serialVersionUID = 1L;
    
    private Port managedPort;

    public PortManager(String username, String password, Port managedPort) {
        super(username, password);
        this.managedPort = managedPort;
        this.permissions.addAll(Arrays.asList(
            "add_container", "remove_container",
            "view_port", "modify_port",
            "list_ships", "calculate_weights"
        ));
    }

    public void setManagedPort(Port managedPort) {
        this.managedPort = managedPort;
    }

    public Port getManagedPort() {
        return managedPort;
    }

    @Override
    public boolean hasPermission(String operation) {
        return permissions.contains(operation);
    }

    @Override
    public void displayMenu() {
        System.out.println("Port Manager Menu:");
        System.out.println("1. Add Container");
        System.out.println("2. Remove Container");
        System.out.println("3. View Port Data");
        System.out.println("4. Modify Port Data");
        System.out.println("5. List Ships in Port");
        System.out.println("6. Calculate Container Weights");
        System.out.println("0. Logout");
    }

    @Override
    public void processOperation(String operation, PortManagementSystem system) {
        switch (operation) {
            case "1":
                system.addContainer();
                break;
            case "2":
                system.removeContainer();
                break;
            case "3":
                viewPortData();
                break;
            case "4":
                modifyPortData(system);
                break;
            case "5":
                system.listShipsInPort();
                break;
            case "6":
                system.calculateContainerWeights();
                break;
            case "0":
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void viewPortData() {
        System.out.println("Port Data:");
        System.out.println("ID: " + managedPort.getId());
        System.out.println("Name: " + managedPort.getName());
        System.out.println("Latitude: " + managedPort.getLatitude());
        System.out.println("Longitude: " + managedPort.getLongitude());
        System.out.println("Storing Capacity: " + managedPort.getStoringCapacity());
        System.out.println("Landing Ability: " + managedPort.hasLandingAbility());
        System.out.println("Current Container Count: " + managedPort.getContainerCount());
        System.out.println("Current Vehicle Count: " + managedPort.getVehicleCount());
    }

    private void modifyPortData(PortManagementSystem system) {
        System.out.println("Modify Port Data:");
        System.out.println("1. Change Name");
        System.out.println("2. Change Storing Capacity");
        System.out.println("3. Change Landing Ability");
        System.out.println("0. Back to Main Menu");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                managedPort.setName(newName);
                break;
            case "2":
                System.out.print("Enter new storing capacity: ");
                int newCapacity = Integer.parseInt(scanner.nextLine());
                managedPort.setStoringCapacity(newCapacity);
                break;
            case "3":
                System.out.print("Enter new landing ability (true/false): ");
                boolean newLandingAbility = Boolean.parseBoolean(scanner.nextLine());
                managedPort.setLandingAbility(newLandingAbility);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        System.out.println("Port data updated successfully.");
        system.updatePort(managedPort);
    }
}