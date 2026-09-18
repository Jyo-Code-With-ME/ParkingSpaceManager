package com.example.parkingspacemanager;

import java.util.Scanner;

public class ParkingSpaceApplicationMain {

    public static void main(String[] args) {

        // Creating a Scanner object to get input from the user

        Scanner scanner = new Scanner(System.in);

        // Creating a ParkingManager object.
        // 10 is passed to the constructor as the number of parking spaces.
        ParkingManager parkingManager = new ParkingManager(15);

        // Initializing the running variable.
        // It controls whether the application continues running.
        boolean running = true;

        System.out.println("================================");
        System.out.println("     PARKING SPACE MANAGER");
        System.out.println("================================");

        // while loop continues as long as running is true
        while (running) {

            // Calling the displayMenu() method
            displayMenu();

            // Calling nextLine() on the Scanner object
            // to read the user's choice.
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            // Checking the user's choice
            switch (choice) {

                // Calling parkVehicle() method
                case "1" -> parkingManager.parkVehicle(scanner);

                // Calling viewAvailableSpots() method
                case "2" -> parkingManager.viewAvailableSpots();

                // Calling exitVehicleAndPay() method
                case "3" -> parkingManager.exitVehicleAndPay(scanner);

                // Calling viewParkingHistory() method
                case "4" -> parkingManager.viewParkingHistory();

               //Calling  help method
                case "5" -> parkingManager.showHelp();

                // Changing running from true to false
                // so the while loop will stop.
                case "6" ->{
                    running = false;
                    System.out.println(
                            "Thank you for using Parking Space Manager."
                    );
                }

                default  -> System.out.println("Invalid option. Please choose 1 to 6.");
            }
        }

        // Closing the Scanner object
        scanner.close();
    }

    // Method used to display the application menu
    private static void displayMenu() {

        System.out.println();
        System.out.println("------------- MENU -------------");
        System.out.println("1. Park Vehicle");
        System.out.println("2. View Available Spots");
        System.out.println("3. Exit Vehicle & Pay");
        System.out.println("4. View Parking History");
        System.out.println("5. Help");
        System.out.println("6. Exit");
        System.out.println("--------------------------------");
    }
}