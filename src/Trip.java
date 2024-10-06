import java.io.Serializable;
import java.time.LocalDate;

public class Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    private Vehicle vehicle;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private TripStatus status;

    public enum TripStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    public Trip(Vehicle vehicle, LocalDate departureDate, Port departurePort, Port arrivalPort) {
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = TripStatus.PENDING;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void start() {
        if (status == TripStatus.PENDING) {
            status = TripStatus.IN_PROGRESS;
            departurePort.removeVehicle(vehicle);
        } else {
            throw new IllegalStateException("Trip cannot be started");
        }
    }

    public void complete(LocalDate arrivalDate) {
        if (status == TripStatus.IN_PROGRESS) {
            this.arrivalDate = arrivalDate;
            status = TripStatus.COMPLETED;
            arrivalPort.addVehicle(vehicle);
            vehicle.setCurrentPort(arrivalPort);
        } else {
            throw new IllegalStateException("Trip cannot be completed");
        }
    }

    @Override
    public String toString() {
        return "Trip{" +
                "vehicle=" + vehicle.getId() +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", departurePort=" + departurePort.getId() +
                ", arrivalPort=" + arrivalPort.getId() +
                ", status=" + status +
                '}';
    }
}