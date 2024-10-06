import java.util.ArrayList;
import java.util.List;

public class Port implements PortInterface {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private boolean landingAbility;
    private List<Container> containers;
    private List<Vehicle> vehicles;
    private List<Trip> currentTraffic;
    private List<Trip> pastTraffic;

    public Port(String id, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.currentTraffic = new ArrayList<>();
        this.pastTraffic = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public int getStoringCapacity() {
        return storingCapacity;
    }

    @Override
    public boolean hasLandingAbility() {
        return landingAbility;
    }

    @Override
    public int getContainerCount() {
        return containers.size();
    }

    @Override
    public int getVehicleCount() {
        return vehicles.size();
    }

    @Override
    public List<Trip> getCurrentTraffic() {
        return new ArrayList<>(currentTraffic);
    }

    @Override
    public List<Trip> getPastTraffic() {
        return new ArrayList<>(pastTraffic);
    }

    @Override
    public void addContainer(Container container) {
        if (getTotalContainerWeight() + container.getWeight() <= storingCapacity) {
            containers.add(container);
        } else {
            throw new IllegalStateException("Port storage capacity exceeded");
        }
    }

    @Override
    public void removeContainer(Container container) {
        containers.remove(container);
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    @Override
    public double calculateDistance(Port otherPort) {
        final int R = 6371; // Earth's radius in kilometers

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(otherPort.getLatitude());
        double lon2 = Math.toRadians(otherPort.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private double getTotalContainerWeight() {
        return containers.stream().mapToDouble(Container::getWeight).sum();
    }

    public void addTrip(Trip trip) {
        currentTraffic.add(trip);
    }

    public void completeTrip(Trip trip) {
        currentTraffic.remove(trip);
        pastTraffic.add(trip);
        if (pastTraffic.size() > 7) {
            pastTraffic.remove(0);
        }
    }
}