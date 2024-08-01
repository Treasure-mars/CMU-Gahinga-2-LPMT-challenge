package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;


public class Admin extends User {
    private static final Scanner scanner = new Scanner(System.in);

    private List<User> users; 

    public Admin() {
        super();
        setAccessType();
    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        setAccessType();
    }

    // Admin-specific methods
    public void deleteUsers() {
        // Implementation
    }

    public void exportUserData() {
        String csvFile = "../Storage/user_data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("UUID,Email,FirstName,LastName,DateOfBirth,IsHivPositive,DateOfInfection,OnARTDrugs,StartARTDate,Country,LifeExpectancy");
            writer.newLine();

            // for (User user : users) {
            //     writer.write(userToCsv(user));
            //     writer.newLine();
            // }

            System.out.println("User data exported successfully to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String userToCsv(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(",");
        sb.append(user.getEmail()).append(",");
        sb.append(user.getFirstName()).append(",");
        sb.append(user.getLastName()).append(",");
        sb.append("").append(",");
        sb.append("").append(",");
        sb.append("").append(",");
        sb.append("").append(",");
        sb.append("").append(",");
        sb.append("").append(",");
        sb.append("");
        return sb.toString();
    }

    public void aggregateUserData() {
        String csvFile = "../Storage/user_data_aggregated.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("UUID,Email,FirstName,LastName,DateOfBirth,IsHivPositive,DateOfInfection,OnARTDrugs,StartARTDate,Country,LifeExpectancy");
            writer.newLine();

            // for (User user : users) {
            //     writer.write(userToCsv(user));
            //     writer.newLine();
            // }

            System.out.println("User data exported successfully to " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAccessType(){
        this.accessType = UserRole.ADMIN;
    }

    public void initiateRegistration(String accessType) {
        // Implementation
        System.out.println("Initiate Registration");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        String scriptPath = "../Bash/user-manager.sh";
        String response = executeScript(scriptPath, "initiate-registration", accessType, email);
        System.out.print(response);
        System.out.println();
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
