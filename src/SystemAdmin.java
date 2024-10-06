import java.util.Arrays;

public class SystemAdmin extends User {
    public SystemAdmin(String username, String password) {
        super(username, password);
        this.permissions.addAll(Arrays.asList(
            "add_vehicle", "remove_vehicle",
            "add_port", "remove_port",
            "add_container", "remove_container",
            "add_manager", "remove_manager",
            "view_all", "modify_all"
        ));
    }

    @Override
    public boolean hasPermission(String operation) {
        return permissions.contains(operation);
    }

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
    public void processOperation(String operation) {
        // Implementation will be added later when PortManagementSystem is created
    }
}