package Model;

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

    // Default constructor
    public Patient() {
        super();
        setAccessType();
    }

    // Parameterized constructor
    public Patient(String firstName, String lastName, String email, String password, Date dateOfBirth, boolean isHivPositive, Date dateOfInfection, boolean onARTDrugs, Date startARTDate, String country, Date demiseDate, int lifeExpectancy) {
        super(firstName, lastName, email, password);
        this.dateOfBirth = dateOfBirth;
        this.isHivPositive = isHivPositive;
        this.dateOfInfection = dateOfInfection;
        this.onARTDrugs = onARTDrugs;
        this.startARTDate = startARTDate;
        this.country = country;
        this.demiseDate = demiseDate;
        this.lifeExpectancy = lifeExpectancy;
        setAccessType();
    }

    // Getters and Setters
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
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
        this.accessType = UserRole.PATIENT;
    }

    // Patient-specific methods
    public void modifyProfile() { 
        // Implementation 
    }

    public void viewProfile() { 
        // Implementation 
    }

    public void computeLifeExpectancy() { 
        // Implementation 
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
}
