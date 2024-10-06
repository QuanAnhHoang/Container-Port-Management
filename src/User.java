import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String username;
    protected String password;
    protected List<String> permissions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.permissions = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public abstract boolean hasPermission(String operation);

    public abstract void displayMenu();

    public abstract void processOperation(String operation, PortManagementSystem system);
}