package com.parkingspacemanager;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ParkingManager {

    private static final double BASE_HOURLY_RATE = 5.00;

    private final ParkingLot parkingLot;
    private final ParkingInput parkingInput;

    public ParkingManager(int numberOfSpaces) {
        parkingLot = new ParkingLot(numberOfSpaces);
        parkingInput = new ParkingInput();
    }

    public void parkVehicle(Scanner scanner) {

        System.out.println("\n--- Park Vehicle ---");

        String licensePlate = parkingInput.getLicensePlate(scanner);

        if (!InputValidator.isValidLicensePlate(licensePlate)) {
            System.out.println(
                    "Error: License plate can only contain letters and numbers."
            );
            return;
        }

        if (parkingLot.findVehicle(licensePlate) != null) {
            System.out.println(
                    "Error: This vehicle is already parked."
            );
            return;
        }

        String brandModel = parkingInput.getBrandModel(scanner);

        if (!InputValidator.isValidBrandModel(brandModel)) {
            System.out.println(
                    "Error: Brand/model cannot be empty."
            );
            return;
        }

        Vehicle vehicle = createVehicle(
                scanner,
                licensePlate,
                brandModel
        );

        if (vehicle == null) {
            return;
        }

        ParkingSpot spot = parkingLot.findAvailableSpot();

        if (spot == null) {
            System.out.println(
                    "Sorry, the parking lot is full."
            );
            return;
        }

        if (!parkingLot.parkVehicle(vehicle)) {
            System.out.println(
                    "Vehicle could not be parked."
            );
            return;
        }

        spot.setEntryTime(LocalDateTime.now());

        System.out.println("\nVehicle parked successfully!");
        System.out.println(
                "Parking space: " + spot.getSpaceNumber()
        );
        System.out.println(
                "Entry time: " + spot.getEntryTime()
        );

        FileManager.log(
                String.format(
                        "Vehicle %s (%s) parked in space %d",
                        vehicle.getLicensePlate(),
                        vehicle.getVehicleType(),
                        spot.getSpaceNumber()
                )
        );
    }

    private Vehicle createVehicle(
            Scanner scanner,
            String licensePlate,
            String brandModel) {

        String type = parkingInput.chooseVehicleType(scanner);

        Vehicle vehicle;

        switch (type) {

            case "1":
                vehicle = new Vehicle.Car(
                        licensePlate,
                        brandModel
                );
                break;

            case "2":
                vehicle = new Vehicle.Truck(
                        licensePlate,
                        brandModel
                );
                break;

            case "3":
                vehicle = new Vehicle.Motorcycle(
                        licensePlate,
                        brandModel
                );
                break;

            default:
                System.out.println(
                        "Error: Invalid vehicle type."
                );
                return null;
        }

        double hourlyRate =
                BASE_HOURLY_RATE
                        * vehicle.getRateMultiplier();

        if (!parkingInput.confirmParking(
                scanner,
                hourlyRate)) {

            System.out.println("Parking cancelled.");
            return null;
        }

        return vehicle;
    }

    public void viewAvailableSpots() {

        System.out.println("\n--- Parking Spaces ---");

        for (ParkingSpot spot : parkingLot.getParkingSpots()) {

            if (spot.isAvailable()) {

                System.out.println(
                        "Space "
                                + spot.getSpaceNumber()
                                + ": AVAILABLE"
                );

            } else {

                Vehicle vehicle = spot.getVehicle();

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

    public void payAndExit(Scanner scanner) {

        System.out.println("\n--- Exit Vehicle & Pay ---");

        String licensePlate =
                parkingInput.getLicensePlate(scanner);

        ParkingSpot spot =
                parkingLot.findVehicle(licensePlate);

        if (spot == null) {
            System.out.println(
                    "Error: Vehicle not found."
            );
            return;
        }

        Vehicle vehicle = spot.getVehicle();

        LocalDateTime entryTime =
                spot.getEntryTime();

        LocalDateTime exitTime =
                LocalDateTime.now();

        long minutes = Duration.between(
                entryTime,
                exitTime
        ).toMinutes();

        double hours = Math.max(
                1,
                Math.ceil(minutes / 60.0)
        );

        System.out.println(
                "\nVehicle: "
                        + vehicle.getLicensePlate()
        );

        System.out.println(
                "Parking space: "
                        + spot.getSpaceNumber()
        );

        System.out.println(
                "Entry time: " + entryTime
        );

        System.out.println(
                "Exit time: " + exitTime
        );

        System.out.println(
                "Parking time: "
                        + hours
                        + " hour(s)"
        );

        PaymentMethod paymentMethod =
                choosePaymentMethod(scanner);

        if (paymentMethod == null) {
            return;
        }

        if (!paymentMethod.validate()) {

            System.out.println("Payment failed.");
            System.out.println(
                    "Please check your payment details."
            );

            return;
        }

        Payment payment = new Payment(
                hours,
                vehicle,
                paymentMethod
        );

        System.out.printf(
                "\nPayment successful! Amount: $%.2f%n",
                payment.getAmount()
        );

        parkingLot.removeVehicle(licensePlate);

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

        System.out.println(
                "Vehicle exited successfully."
        );

        System.out.println(
                "Parking space "
                        + spot.getSpaceNumber()
                        + " is now available."
        );
    }


    private PaymentMethod choosePaymentMethod(
            Scanner scanner) {

        String choice =
                parkingInput.choosePaymentMethod(scanner);

        if (choice.equals("1")) {

            String cardNumber =
                    parkingInput.getCardNumber(scanner);

            return new CreditCardPayment(cardNumber);
        }

        if (choice.equals("2")) {

            String cardNumber =
                    parkingInput.getCardNumber(scanner);

            return new DebitCardPayment(cardNumber);
        }

        System.out.println(
                "Error: Invalid payment method."
        );

        return null;
    }

    public void showHelp() {

        System.out.println("\n--- Help ---");

        System.out.println("1. Park Vehicle");
        System.out.println("   Enter a license plate, brand/model, and vehicle type.");

        System.out.println("\n2. View Available Spots");
        System.out.println("   Shows available and occupied parking spaces.");

        System.out.println("\n3. Exit Vehicle & Pay");
        System.out.println(
                "   Enter a license plate to calculate the parking fee and pay."
        );

        System.out.println("\n4. View Parking History");
        System.out.println(
                "   Shows the location of the parking history file."
        );

        System.out.println("\n5. Help");
        System.out.println(
                "   Displays this help information."
        );

        System.out.println("\n6. Exit");
        System.out.println(
                "   Closes the application."
        );
    }

    public void viewParkingHistory() {


        System.out.println("\n--- Parking History ---");
        System.out.println("Parking history is stored at:");
        System.out.println(FileManager.getHistoryFilePath());
    }
}
