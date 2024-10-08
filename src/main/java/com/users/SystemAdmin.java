package com.users;

import com.models.User;
import com.PortManagementSystem;
import java.util.Arrays;

/**
 * Represents a System Administrator user in the Port Management System.
 * SystemAdmin has full access to all system operations.
 */
public class SystemAdmin extends User {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new SystemAdmin user.
     *
     * @param username The username of the SystemAdmin.
     * @param password The password of the SystemAdmin.
     */
    public SystemAdmin(String username, String password) {
        super(username, password);
        // Grant all permissions to the SystemAdmin
        this.permissions.addAll(Arrays.asList(
            "add_vehicle", "remove_vehicle",
            "add_port", "remove_port",
            "add_container", "remove_container",
            "add_manager", "remove_manager",
            "view_all", "modify_all" //, potentially more permissions
        ));
    }

    /**
     * Checks if the SystemAdmin has permission for a specific operation.
     * SystemAdmin always has permission for all operations.
     *
     * @param operation The operation to check permission for.
     * @return True if the SystemAdmin has permission (which is always true), false otherwise.
     */
    @Override
    public boolean hasPermission(String operation) {
        return permissions.contains(operation);
    }

    /**
     * Displays the System Admin menu.
     */
    @Override
    public void displayMenu() {
        System.out.println("System Admin Menu:");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Remove Vehicle");
        System.out.println("3. Add Port");
        System.out.println("4. Remove Port");
        System.out.println("5. Add Container");
        System.out.println("6. Remove Container");
        System.out.println("7. Add Manager");
        System.out.println("8. Remove Manager");
        System.out.println("9. View All Data");
        System.out.println("10. Modify Data");
        System.out.println("11. Calculate Fuel Usage");
        System.out.println("12. Calculate Container Weights");
        System.out.println("13. List Ships in Port");
        System.out.println("14. List Trips on Date");
        System.out.println("15. List Trips between Dates");
        System.out.println("0. Logout");
    }

    @Override
    public void processOperation(String operation, PortManagementSystem system) {
        switch (operation) {
            case "1":
                system.addVehicle();
                break;
            case "2":
                system.removeVehicle();
                break;
            case "3":
                system.addPort();
                break;
            case "4":
                system.removePort();
                break;
            case "5":
                system.addContainer();
                break;
            case "6":
                system.removeContainer();
                break;
            case "7":
                system.addManager();
                break;
            case "8":
                system.removeManager();
                break;
            case "9":
                viewAllData(system);
                break;
            case "10":
                modifyData(system);
                break;
            case "11":
                system.calculateFuelUsage();
                break;
            case "12":
                system.calculateContainerWeights();
                break;
            case "13":
                system.listShipsInPort();
                break;
            case "14":
                system.listTripsOnDate();
                break;
            case "15":
                system.listTripsBetweenDates();
                break;
            case "0":
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void viewAllData(PortManagementSystem system) {
        System.out.println("Viewing all data:");
        system.listAllPorts();
        system.listAllVehicles();
        system.listAllContainers();
        system.listAllUsers();
    }

    private void modifyData(PortManagementSystem system) {
        System.out.println("Modify Data:");
        System.out.println("1. Modify Port");
        System.out.println("2. Modify Vehicle");
        System.out.println("3. Modify Container");
        System.out.println("4. Modify User");
        System.out.println("0. Back to Main Menu");

        String choice = system.getScanner().nextLine();
        switch (choice) {
            case "1":
                system.modifyPort();
                break;
            case "2":
                system.modifyVehicle();
                break;
            case "3":
                system.modifyContainer();
                break;
            case "4":
                system.modifyUser();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}