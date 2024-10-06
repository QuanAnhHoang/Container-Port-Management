import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String DATA_DIRECTORY = "data/";

    public static void saveData(String fileName, List<?> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIRECTORY + fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Error saving data to " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadData(String fileName) {
        List<T> data = new ArrayList<>();
        File file = new File(DATA_DIRECTORY + fileName);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                data = (List<T>) ois.readObject();
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
        return loadData("users.dat");
    }

    public static void savePorts(List<Port> ports) {
        saveData("ports.dat", ports);
    }

    public static List<Port> loadPorts() {
        return loadData("ports.dat");
    }

    public static void saveVehicles(List<Vehicle> vehicles) {
        saveData("vehicles.dat", vehicles);
    }

    public static List<Vehicle> loadVehicles() {
        return loadData("vehicles.dat");
    }

    public static void saveContainers(List<Container> containers) {
        saveData("containers.dat", containers);
    }

    public static List<Container> loadContainers() {
        return loadData("containers.dat");
    }

    public static void saveTrips(List<Trip> trips) {
        saveData("trips.dat", trips);
    }

    public static List<Trip> loadTrips() {
        return loadData("trips.dat");
    }

    static {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}