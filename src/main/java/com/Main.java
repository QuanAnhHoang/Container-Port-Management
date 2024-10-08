package com;

/**
 * Main class for the Port Management System.
 * This class initializes and starts the system.
 */
public class Main {

    /**
     * Main method - entry point of the application.
     * Creates a PortManagementSystem object, populates it with sample data, and starts the system.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Create an instance of the PortManagementSystem
        PortManagementSystem system = new PortManagementSystem();

        // Populate the system with sample data (ships, containers, etc.)
        system.createSampleData();

        // Start the port management system (e.g., begin processing operations)
        system.start();
    }
}