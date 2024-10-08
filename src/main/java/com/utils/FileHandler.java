package com.utils;

import com.models.*;
import java.io.*;
import java.util.*;

/**
 * Utility class for handling file operations, specifically saving and loading data to/from files.
 * Uses serialization for object persistence.
 */
public class FileHandler {
    /**
     * The directory where data files are stored.
     */
    private static final String DATA_DIRECTORY = "data/";

    /**
     * Saves a list of objects to a file using serialization.
     *
     * @param fileName The name of the file to save the data to.
     * @param data     The list of objects to be saved.
     * @param <T>      The type of objects in the list.
     */
    public static <T> void saveData(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIRECTORY + fileName))) {
            oos.writeObject(data); // Write the list to the file
        } catch (IOException e) {
            System.err.println("Error saving data to " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of objects from a file using serialization.
     *
     * @param fileName The name of the file to load data from.
     * @param type     The class of the objects to be loaded.
     * @param <T>      The type of objects in the list.
     * @return A list of loaded objects, or an empty list if the file doesn't exist or an error occurs.
     */
    public static <T> List<T> loadData(String fileName, Class<T> type) {
        List<T> data = new ArrayList<>();
        File file = new File(DATA_DIRECTORY + fileName);
        if (file.exists()) { // Check if the file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject(); // Read the object from the file
                if (obj instanceof List<?>) { // Check if the object is a list
                    for (Object item : (List<?>) obj) { // Iterate through the list
                        if (type.isInstance(item)) { // Check if the item is of the correct type
                            data.add(type.cast(item)); // Cast and add the item to the list
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading data from " + fileName + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void saveUsers(List<User> users) {
        saveData("users.dat", users);
    }

    public static List<User> loadUsers() {
        return loadData("users.dat", User.class);
    }

    public static void savePorts(List<Port> ports) {
        saveData("ports.dat", ports);
    }

    public static List<Port> loadPorts() {
        return loadData("ports.dat", Port.class);
    }

    public static void saveVehicles(List<Vehicle> vehicles) {
        saveData("vehicles.dat", vehicles);
    }

    public static List<Vehicle> loadVehicles() {
        return loadData("vehicles.dat", Vehicle.class);
    }

    public static void saveContainers(List<Container> containers) {
        saveData("containers.dat", containers);
    }

    public static List<Container> loadContainers() {
        return loadData("containers.dat", Container.class);
    }

    public static void saveTrips(List<Trip> trips) {
        saveData("trips.dat", trips);
    }

    public static List<Trip> loadTrips() {
        return loadData("trips.dat", Trip.class);
    }

    static {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}