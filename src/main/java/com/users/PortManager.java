package com.users;

import com.models.User;
import com.models.Port;
import com.PortManagementSystem;
import java.util.Arrays;

/**
 * Represents a Port Manager user in the Port Management System.
 * Port Managers have specific permissions related to managing a port.
 */
public class PortManager extends User {
    private static final long serialVersionUID = 1L; // Serialization version UID

    /**
     * The port managed by this PortManager.
     */
    private Port managedPort;

    /**
     * Constructs a new PortManager.
     *
     * @param username    The username of the PortManager.
     * @param password    The password of the PortManager.
     * @param managedPort The port to be managed by this PortManager.
     */
    public PortManager(String username, String password, Port managedPort) {
        super(username, password);
        this.managedPort = managedPort;
        // Add specific permissions for PortManager
        this.permissions.addAll(Arrays.asList(
            "add_container", "remove_container",
            "view_port", "modify_port",
            "list_ships", "calculate_weights"
        ));
    }

    /**
     * Sets the managed port for this PortManager.
     *
     * @param managedPort The new port to be managed.
     */
    public void setManagedPort(Port managedPort) {
        this.managedPort = managedPort;
    }

    /**
     * Gets the port managed by this PortManager.
     *
     * @return The managed port.
     */
    public Port getManagedPort() {
        return managedPort;
    }

    /**
     * Checks if the PortManager has permission for a specific operation.
     *
     * @param operation The operation to check permission for.
     * @return True if the PortManager has permission, false otherwise.
     */
    @Override
    public boolean hasPermission(String operation) {
        return permissions.contains(operation);
    }

    /**
     * Displays the Port Manager menu.
     */
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

    /**
     * Processes the selected operation from the Port Manager menu.
     *
     * @param operation The operation code selected by the user.
     * @param system    The PortManagementSystem instance.
     */
    @Override
    public void processOperation(String operation, PortManagementSystem system) {
        switch (operation) {
            case "1":
                system.addContainer(); // Delegate adding container to the system
                break;
            case "2":
                system.removeContainer(); // Delegate removing container to the system
                break;
            case "3":
                viewPortData(); // View details of the managed port
                break;
            case "4":
                modifyPortData(system); // Modify data of the managed port
                break;
            case "5":
                system.listShipsInPort(); // Delegate listing ships to the system
                break;
            case "6":
                system.calculateContainerWeights(); // Delegate weight calculation to the system
                break;
            case "0":
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    /**
     * Displays the details of the managed port.
     */
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

    /**
     * Allows modification of the managed port's data.
     *
     * @param system The PortManagementSystem instance.
     */
    private void modifyPortData(PortManagementSystem system) {
        System.out.println("Modify Port Data:");
        System.out.println("1. Change Name");
        System.out.println("2. Change Storing Capacity");
        System.out.println("3. Change Landing Ability");
        System.out.println("0. Back to Main Menu");

        String choice = system.getScanner().nextLine();
        switch (choice) {
            case "1":
                System.out.print("Enter new name: ");
                String newName = system.getScanner().nextLine();
                managedPort.setName(newName); // Update port name
                break;
            case "2":
                System.out.print("Enter new storing capacity: ");
                int newCapacity = Integer.parseInt(system.getScanner().nextLine());
                managedPort.setStoringCapacity(newCapacity); // Update storing capacity
                break;
            case "3":
                System.out.print("Enter new landing ability (true/false): ");
                boolean newLandingAbility = Boolean.parseBoolean(system.getScanner().nextLine());
                managedPort.setLandingAbility(newLandingAbility); // Update landing ability
                break;
            case "0":
                return; // Return to main menu
            default:
                System.out.println("Invalid option. Please try again.");
        }
        System.out.println("Port data updated successfully.");
        system.updatePort(managedPort); // Update the port in the system
    }
}