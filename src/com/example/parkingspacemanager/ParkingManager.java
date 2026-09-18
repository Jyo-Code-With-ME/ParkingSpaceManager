package com.example.parkingspacemanager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ParkingManager {

    // Instance variables.
    // These variables belong to each ParkingManager object.
    private final ParkingLot parkingLot;

    // Constructor.
    // This is called when we create a ParkingManager object.
    public ParkingManager(int numberOfSpaces) {

        // Creating a ParkingLot object
        // and passing the number of spaces to its constructor.
        parkingLot = new ParkingLot(numberOfSpaces);

    }

    // Method responsible for parking a vehicle
    public void parkVehicle(Scanner scanner) {

        System.out.println("\n--- Park Vehicle ---");

        System.out.print("License plate: ");

        // Reading the license plate from the user
        String licensePlate = scanner.nextLine().trim();

        // Checking whether the license plate is empty
        if (licensePlate.isBlank()) {
            System.out.println(
                    "Error: License plate cannot be empty."
            );
            return;
        }

        // Calling findVehicle() on the ParkingLot object
        // to check whether this vehicle is already parked.
        if (parkingLot.findVehicle(licensePlate) != null) {
            System.out.println(
                    "Error: This vehicle is already parked."
            );
            return;
        }

        System.out.print("Brand/model: ");

        // Reading the vehicle brand/model
        String brandModel = scanner.nextLine().trim();

        if (brandModel.isBlank()) {
            System.out.println(
                    "Error: Brand/model cannot be empty."
            );
            return;
        }

        // Calling createVehicle() to create the appropriate
        // Car, Truck, or Motorcycle object.
        Vehicle vehicle = createVehicle(scanner, licensePlate, brandModel);

        // If vehicle creation failed, stop this method.
        if (vehicle == null) {
            return;
        }
        // Calling findAvailableSpot() on the ParkingLot object
        // to find an empty parking spot.
        ParkingSpot spot = parkingLot.findAvailableSpot();

        if (spot == null) {
            System.out.println(
                    "Sorry, the parking lot is full.");
            return;
        }

        // Calling parkVehicle() on the ParkingLot object
        // to place the vehicle into an available spot.
        boolean parked = parkingLot.parkVehicle(vehicle);

        if (!parked) {
            System.out.println("Vehicle could not be parked.");
            return;
        }

        // Calling LocalDateTime.now() to get the current date/time.
        // The returned value is stored in the ParkingSpot object.
        spot.setEntryTime(LocalDateTime.now());

        System.out.println("\nVehicle parked successfully!");
        System.out.println("Parking space: " + spot.getSpaceNumber());
        System.out.println("Entry time: " + spot.getEntryTime());


        // Calling the log() method of FileManager
        // to save information in the history file.
        FileManager.log(
               String.format ("Vehicle "
                        + vehicle.getLicensePlate()
                        + " ("
                        + vehicle.getVehicleType()
                        + ") parked in space "
                        + spot.getSpaceNumber())
        );
    }

    // Creates a specific type of Vehicle object
    private Vehicle createVehicle(
            Scanner scanner,
            String licensePlate,
            String brandModel) {

        System.out.println("\nVehicle Type:");
        System.out.println("1. Car");
        System.out.println("2. Truck");
        System.out.println("3. Motorcycle");

        System.out.print("Choose vehicle type: ");
        String type =
                scanner.nextLine().trim();

        switch (type) {
            // Creating a Car object
            case "1":
                return new Vehicle.Car(
                    licensePlate,
                    brandModel
            );

            // Creating a Truck object
            case "2":
                return new Vehicle.Truck(
                    licensePlate,
                    brandModel
            );

            // Creating a Motorcycle object
            case "3":
                return new Vehicle.Motorcycle(
                    licensePlate,
                    brandModel
            );

            default:
                System.out.println(
                        "Error: Invalid vehicle type."
                );

                return null;
        }
    }

    // Displays all parking spaces
    public void viewAvailableSpots() {

        System.out.println("\n--- Parking Spaces ---");

        // Getting the list of ParkingSpot objects
        // from the ParkingLot object.
        for (ParkingSpot spot :
                parkingLot.getParkingSpots()) {

            if (spot.isAvailable()) {

                System.out.println(
                        "Space "
                                + spot.getSpaceNumber()
                                + ": AVAILABLE"
                );

            } else {

                // Getting the Vehicle object from the ParkingSpot
                Vehicle vehicle =
                        spot.getVehicle();

                System.out.println(
                        "Space "
                                + spot.getSpaceNumber()
                                + ": OCCUPIED - "
                                + vehicle.getLicensePlate()
                );
            }
        }

        System.out.println(
                "\nAvailable spaces: "
                        + parkingLot.getAvailableSpaceCount()
        );

        System.out.println(
                "Occupied spaces: "
                        + parkingLot.getOccupiedSpaceCount()
        );
    }

    // Handles vehicle exit and payment
    public void exitVehicleAndPay(Scanner scanner) {

        System.out.println("\n--- Exit Vehicle & Pay ---");

        System.out.print("License plate: ");
        String licensePlate = scanner.nextLine().trim();

        // Finding the parking spot containing this vehicle
        ParkingSpot spot = parkingLot.findVehicle(licensePlate);

        if (spot == null) {
            System.out.println(
                    "Error: Vehicle not found."
            );
            return;
        }

        // Getting the Vehicle object from the parking spot
        Vehicle vehicle = spot.getVehicle();

        // Getting the entry time
        LocalDateTime entryTime = spot.getEntryTime();

        // Getting the current time as the exit time
        LocalDateTime exitTime = LocalDateTime.now();

        // Calculating the difference between entry and exit time
        long minutes = Duration.between(
                        entryTime,
                        exitTime
                ).toMinutes();

        // Calculating parking hours
        double hours =
                Math.max(
                        1,
                        Math.ceil(minutes / 60.0)
                );


        System.out.println("\nVehicle: " + vehicle.getLicensePlate());
        System.out.println("Parking space: " + spot.getSpaceNumber());
        System.out.println("Entry time: " + entryTime);
        System.out.println("Exit time: " + exitTime);
        System.out.println("Parking time: " + hours + " hour(s)");


        // Creating a Payment object
        // using the calculated parking hours
        // and selected payment method later.
        PaymentMethod paymentMethod = choosePaymentMethod(scanner);

        if (paymentMethod == null) {
            return;
        }

        // Creating a Payment object.
        Payment payment =
                new Payment(
                        hours,
                        vehicle,
                        paymentMethod);

        // Calling processPayment() on the Payment object
        if (!payment.processPayment()) {

            System.out.println("Payment failed.");

            System.out.println("Please check your payment details.");

            return;
        }

        System.out.printf("\nPayment successful! Amount: $%.2f%n", payment.getAmount());

        // Removing the vehicle from the parking lot
        parkingLot.removeVehicle(licensePlate);

        // Saving exit/payment information
        FileManager.log(
                String.format(
                        "Vehicle %s exited space %d - "
                                + "Parking time: %.1f hours - "
                                + "Payment: $%.2f - "
                                + "Method: %s",
                        licensePlate,
                        spot.getSpaceNumber(),
                        hours,
                        payment.getAmount(),
                        payment.getPaymentType()
                )
        );

        System.out.println("Vehicle exited successfully.");

        System.out.println("Parking space " + spot.getSpaceNumber() + " is now available.");
    }

    // Displays payment options and creates the selected payment object
    private PaymentMethod choosePaymentMethod(
            Scanner scanner) {

        System.out.println("\nPayment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");

        System.out.print("Choose payment method: ");
        String choice =
                scanner.nextLine().trim();

        if (choice.equals("1")) {

            System.out.print("Card number (16 digits): ");

            String cardNumber = scanner.nextLine().trim();

            // Creating a CreditCardPayment object
            return new CreditCardPayment(cardNumber);
        }

        if (choice.equals("2")) {

            System.out.print("Card number (16 digits): ");

            String cardNumber = scanner.nextLine().trim();

            // Creating a DebitCardPayment object
            return new DebitCardPayment(cardNumber);
        }

        System.out.println("Error: Invalid payment method.");

        return null;
    }


    public void showHelp() {
        System.out.println("\n--- Help ---");

        System.out.println("1. Park Vehicle");
        System.out.println("   Enter a license plate, vehicle brand/model,");
        System.out.println("   and vehicle type.");

        System.out.println();

        System.out.println("2. View Available Spots");
        System.out.println("   Shows which parking spaces are available");
        System.out.println("   and which spaces are occupied.");

        System.out.println();

        System.out.println("3. Exit Vehicle & Pay");
        System.out.println("   Enter the license plate of a parked vehicle.");
        System.out.println("   The application calculates the parking fee");
        System.out.println("   and asks you to select a payment method.");

        System.out.println();

        System.out.println("4. View Parking History");
        System.out.println("   Shows where the parking history is stored.");

        System.out.println();

        System.out.println("5. Help");
        System.out.println("   Displays this help information.");

        System.out.println();

        System.out.println("6. Exit");
        System.out.println("   Closes the application.");
    }

    // Displays parking history information
    public void viewParkingHistory() {

        System.out.println(
                "\n--- Parking History ---"
        );

        System.out.println(
                "Parking history is stored in:"
        );

        System.out.println(
                "parking_history.txt"
        );
    }
}