package com.parkingspacemanager;


// Abstract class.
// We don't create a normal Vehicle object directly.
public abstract class Vehicle {

    // Encapsulation:
    // fields are private and accessed through methods.
    private final String licensePlate;
    private final String brandModel;

    // Constructor of the Vehicle class
    public Vehicle(String licensePlate, String brandModel) {

        // Initialize the fields
        this.licensePlate = licensePlate;
        this.brandModel = brandModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrandModel() {
        return brandModel;
    }


    // Abstract method.
    // Child classes must provide their own implementation.
    // Must be exactly "abstract" and "getVehicleType()"
    public abstract String getVehicleType();

    // Abstract method.
    // Each vehicle type charges a different multiplier on the base
    // hourly rate - this is what makes the Vehicle hierarchy affect
    // actual program behavior (fee amount), not just a display label.
    public abstract double getRateMultiplier();

    // === Child CLASSES START HERE (Inside Vehicle) ===
    // Car inherits from Vehicle
    public static final class Car extends Vehicle {

        // Car constructor
        public Car(String licensePlate, String brandModel) {

            // Calling the parent class constructor
            super(licensePlate, brandModel);  // Polymorphism / method overriding
        }

        // Providing implementation of abstract method
        @Override
        public String getVehicleType() {
            return "Car";
        }

        @Override
        public double getRateMultiplier() {
            return 1.0; // standard rate
        }
    }

    // Truck inherits from Vehicle
    public static final class Truck extends Vehicle {
        public Truck(String licensePlate, String brandModel) {

            // Calling Vehicle constructor
            super(licensePlate, brandModel);
        }

        @Override
        public String getVehicleType() {
            return "Truck";
        }
        @Override
        public double getRateMultiplier() {
            return 1.5; // larger vehicle, higher rate
        }
    }


    // Motorcycle inherits from Vehicle
    public static final class Motorcycle extends Vehicle {
        public Motorcycle(String licensePlate, String brandModel) {
            // Calling Vehicle constructor
            super(licensePlate, brandModel);
        }

        @Override
        public String getVehicleType() {
            return "Motorcycle";
        }
        @Override
        public double getRateMultiplier() {
            return 0.5; // smaller vehicle, discounted rate
        }
    }
}


