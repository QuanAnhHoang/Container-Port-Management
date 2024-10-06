import java.util.Arrays;

public class PortManager extends User {
    private Port managedPort;

    public PortManager(String username, String password, Port managedPort) {
        super(username, password);
        this.managedPort = managedPort;
        this.permissions.addAll(Arrays.asList(
            "add_container", "remove_container",
            "view_port", "modify_port"
        ));
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
    public void processOperation(String operation) {
        // Implementation will be added later when PortManagementSystem is created
    }
}