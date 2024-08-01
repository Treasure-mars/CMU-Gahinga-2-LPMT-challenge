package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Patient extends User {
    private Date dateOfBirth;
    private boolean isHivPositive;
    private Date dateOfInfection;
    private boolean onARTDrugs;
    private Date startARTDate;
    private String country;
    private Date demiseDate;
    private int lifeExpectancy;
    private String uuid;

    // Default constructor
    public Patient() {
        super();
        setAccessType();
    }

    // Parameterized constructor
    public Patient(String uuid, String firstName, String lastName, String email, String password, Date dateOfBirth, boolean isHivPositive, Date dateOfInfection, boolean onARTDrugs, Date startARTDate, String country, Date demiseDate, int lifeExpectancy) {
        super(firstName, lastName, email, password);
        setAccessType();
        this.uuid = uuid;
        this.dateOfBirth = dateOfBirth;
        this.isHivPositive = isHivPositive;
        this.dateOfInfection = dateOfInfection;
        this.onARTDrugs = onARTDrugs;
        this.startARTDate = startARTDate;
        this.country = country;
        this.demiseDate = demiseDate;
        this.lifeExpectancy = lifeExpectancy;
    }

    // Getters and Setters
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public boolean isHivPositive() { return isHivPositive; }
    public void setHivPositive(boolean hivPositive) { isHivPositive = hivPositive; }
    public Date getDateOfInfection() { return dateOfInfection; }
    public void setDateOfInfection(Date dateOfInfection) { this.dateOfInfection = dateOfInfection; }
    public boolean isOnARTDrugs() { return onARTDrugs; }
    public void setOnARTDrugs(boolean onARTDrugs) { this.onARTDrugs = onARTDrugs; }
    public Date getStartARTDate() { return startARTDate; }
    public void setStartARTDate(Date startARTDate) { this.startARTDate = startARTDate; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Date getDemiseDate() { return demiseDate; }
    public void setDemiseDate(Date demiseDate) { this.demiseDate = demiseDate; }
    public int getLifeExpectancy() { return lifeExpectancy; }
    public void setLifeExpectancy(int lifeExpectancy) { this.lifeExpectancy = lifeExpectancy; }

    // Patient-specific methods
    public void modifyProfile() {
        // Implementation
    }

    public void viewProfile() {
        String scriptPath = "/home/tresor/Desktop/CMU-Gahinga-2-LPMT-challenge/src/Bash/user-manager.sh";
        String response = executeScript(scriptPath, "view-profile", uuid);
        System.out.println(response);
    }

    public void computeLifeExpectancy() {
        // Implementation
    }

    @Override
    public void setAccessType(){
        this.accessType = UserRole.PATIENT;
    }

    public String completeRegistration() {
        String scriptPath = "/home/tresor/Desktop/CMU-Gahinga-2-LPMT-challenge/src/Bash/user-manager.sh";
        String response = executeScript(scriptPath, "complete-registration", uuid, getFirstName(), getLastName(), dateOfBirth.toString(), String.valueOf(isHivPositive), dateOfInfection != null ? dateOfInfection.toString() : "", String.valueOf(onARTDrugs), startARTDate != null ? startARTDate.toString() : "", country, getPassword());
        return response;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", accessType=" + getAccessType() +
                ", dateOfBirth=" + dateOfBirth +
                ", isHivPositive=" + isHivPositive +
                ", dateOfInfection=" + dateOfInfection +
                ", onARTDrugs=" + onARTDrugs +
                ", startARTDate=" + startARTDate +
                ", country='" + country + '\'' +
                ", demiseDate=" + demiseDate +
                ", lifeExpectancy=" + lifeExpectancy +
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
