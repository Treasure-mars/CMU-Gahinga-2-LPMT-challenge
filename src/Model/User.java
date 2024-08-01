package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password; // Hashed password
    protected UserRole accessType; // Protected to allow subclasses to set it directly

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UserRole getAccessType() { return accessType; }
    public abstract void setAccessType();

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessType=" + accessType +
                '}';
    }

    // Abstract methods for polymorphism
    public String login(){
        String scriptPath = "../Bash/user-manager.sh";
        String response = executeScript(scriptPath, "login", email, password);
        return response;
    }

    public static String executeScript(String... command) {
        StringBuilder response = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        
        try {
            Process process = processBuilder.start();
            
            // Capture output from the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }

            // Capture any errors from the script
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("Error: " + errorLine);
            }

            // Wait for the script to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Script exited with error code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        return response.toString().trim(); // Trim to remove any trailing new lines
    }
}
