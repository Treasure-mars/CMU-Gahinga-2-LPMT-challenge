package Model;

public class Admin extends User {
    public Admin() {
        super();
        setAccessType();
    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        setAccessType();
    }

    @Override
    public boolean login(String emailInput, String passwordInput) {
        // Call Bash script to verify admin login
        // Return true if successful, otherwise false
        String email = super.getEmail();
        String password = super.getPassword();
        return email.equals(emailInput) && password.equals(passwordInput);
    }

    @Override
    public void setAccessType(){
        this.accessType = UserRole.ADMIN;
    }

    // Admin-specific methods
    public void deleteUsers() { 
        // Implementation 
    }

    public void exportUserData() { 
        // Implementation 
    }

    public void aggregateUserData() { 
        // Implementation 
    }

    public void initiateRegistration() { 
        // Implementation 
    }

    public void getAllUsers() { 
        // Implementation 
    }

    @Override
    public String toString() {
        return "Admin{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", accessType=" + getAccessType() +
                '}';
    }
}
