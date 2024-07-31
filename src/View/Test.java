package View;

import Model.*;

import java.io.Console;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {
    private static final Scanner scanner = new Scanner(System.in);
    private static Admin currentAdmin = null;
    private static Patient currentPatient = null;

    public static void main(String[] args) {
        while (true) {
            clearScreen();
            System.out.println("Welcome to the Health Management System");
            System.out.println("1. Register as Admin");
            System.out.println("2. Register as Patient");
            System.out.println("3. Login as Admin");
            System.out.println("4. Login as Patient");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerAdmin();
                    break;
                case 2:
                    registerPatient();
                    break;
                case 3:
                    loginAdmin();
                    break;
                case 4:
                    loginPatient();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void registerAdmin() {
        clearScreen();
        System.out.println("Register as Admin");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String password = readPassword("Password: ");

        currentAdmin = new Admin(firstName, lastName, email, password);
        System.out.println("Admin registered successfully!");
        pressEnterToContinue();
    }

    private static void registerPatient() {
        clearScreen();
        System.out.println("Register as Patient");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String password = readPassword("Password: ");

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        Date dateOfBirth = parseDate(scanner.nextLine());

        System.out.print("Is HIV Positive (true/false): ");
        boolean isHivPositive = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        Date dateOfInfection = null;
        if (isHivPositive) {
            System.out.print("Date of Infection (yyyy-MM-dd): ");
            dateOfInfection = parseDate(scanner.nextLine());
        }

        System.out.print("On ART Drugs (true/false): ");
        boolean onARTDrugs = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        Date startARTDate = null;
        if (onARTDrugs) {
            System.out.print("Start ART Date (yyyy-MM-dd): ");
            startARTDate = parseDate(scanner.nextLine());
        }

        System.out.print("Country: ");
        String country = scanner.nextLine();

        System.out.print("Life Expectancy: ");
        int lifeExpectancy = scanner.nextInt();
        scanner.nextLine(); // consume newline

        currentPatient = new Patient(firstName, lastName, email, password, dateOfBirth, isHivPositive,
                dateOfInfection, onARTDrugs, startARTDate, country, null, lifeExpectancy);

        System.out.println("Patient registered successfully!");
        pressEnterToContinue();
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter in yyyy-MM-dd format.");
            return null;
        }
    }

    private static void loginAdmin() {
        clearScreen();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String password = readPassword("Password: ");

        if (currentAdmin != null && currentAdmin.login(email, password)) {
            System.out.println("Admin login successful!");
            adminMenu();
        } else {
            System.out.println("Invalid email or password.");
            pressEnterToContinue();
        }
    }

    private static void loginPatient() {
        clearScreen();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        String password = readPassword("Password: ");

        if (currentPatient != null && currentPatient.login(email, password)) {
            System.out.println("Patient login successful!");
            patientMenu();
        } else {
            System.out.println("Invalid email or password.");
            pressEnterToContinue();
        }
    }

    private static void adminMenu() {
        while (true) {
            clearScreen();
            System.out.println("Admin Menu");
            System.out.println("1. Delete Users");
            System.out.println("2. Export User Data");
            System.out.println("3. Aggregate User Data");
            System.out.println("4. Initiate Registration");
            System.out.println("5. Get All Users");
            System.out.println("6. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    currentAdmin.deleteUsers();
                    break;
                case 2:
                    currentAdmin.exportUserData();
                    break;
                case 3:
                    currentAdmin.aggregateUserData();
                    break;
                case 4:
                    currentAdmin.initiateRegistration();
                    break;
                case 5:
                    currentAdmin.getAllUsers();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    pressEnterToContinue();
            }
        }
    }

    private static void patientMenu() {
        while (true) {
            clearScreen();
            System.out.println("Patient Menu");
            System.out.println("1. Modify Profile");
            System.out.println("2. View Profile");
            System.out.println("3. Compute Life Expectancy");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    currentPatient.modifyProfile();
                    break;
                case 2:
                    currentPatient.viewProfile();
                    break;
                case 3:
                    currentPatient.computeLifeExpectancy();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    pressEnterToContinue();
            }
        }
    }

    private static String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword(prompt);
            return new String(passwordArray);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
