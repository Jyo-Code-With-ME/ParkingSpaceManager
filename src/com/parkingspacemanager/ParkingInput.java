package com.parkingspacemanager;

import java.util.Scanner;

public class ParkingInput {

    public String getLicensePlate(Scanner scanner) {

        System.out.print("License plate: ");

        return scanner.nextLine()
                .trim()
                .toUpperCase();
    }

    public String getBrandModel(Scanner scanner) {

        System.out.print("Brand/model: ");

        return scanner.nextLine().trim();
    }

    public String chooseVehicleType(Scanner scanner) {

        System.out.println("\nVehicle Type:");
        System.out.println("1. Car");
        System.out.println("2. Truck");
        System.out.println("3. Motorcycle");

        System.out.print("Choose vehicle type: ");

        return scanner.nextLine().trim();
    }

    public boolean confirmParking(
            Scanner scanner,
            double hourlyRate) {

        System.out.printf(
                "Hourly parking rate: $%.2f%n",
                hourlyRate
        );

        System.out.print("Do you want to proceed? (Y/N): ");

        String answer = scanner.nextLine().trim();

        return answer.equalsIgnoreCase("Y");
    }

    public String choosePaymentMethod(Scanner scanner) {

        System.out.println("\nPayment Method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");

        System.out.print("Choose payment method: ");

        return scanner.nextLine().trim();
    }

    public String getCardNumber(Scanner scanner) {

        System.out.print("Card number (16 digits): ");

        return scanner.nextLine().trim();
    }
}
