package com.parkingspacemanager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ParkingLot {

    // List that stores all ParkingSpot objects
    private final List<ParkingSpot> parkingSpots;

    // Constructor
    public ParkingLot(int numberOfSpaces) {

        // Validate the number of spaces
        if (numberOfSpaces <= 0) {
            throw new IllegalArgumentException(
                    "Number of parking spaces must be greater than zero."
            );
        }

        // Creating the ArrayList object
        parkingSpots = new ArrayList<>();

        // Creating ParkingSpot objects
        // and adding them to the list.
        for (int i = 1; i <= numberOfSpaces; i++) {

            // Creating a ParkingSpot object
            ParkingSpot spot = new ParkingSpot(i);

            // Adding the object to the list
            parkingSpots.add(spot);
        }
    }

    // Returns the list of parking spots
    public List<ParkingSpot> getParkingSpots() {

        // Returning an unmodifiable view of the list
        return Collections.unmodifiableList(parkingSpots);
    }

    // Finds the first available parking spot
    public ParkingSpot findAvailableSpot() {

        for (ParkingSpot spot : parkingSpots) {
            if (spot.getVehicle() == null) {
                return spot;
            }
        }
        // No available spot
        return null;
    }

    // Parks a vehicle in an available spot
    public boolean parkVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            return false;
        }

        // Check if vehicle is already parked
        if (findVehicle(vehicle.getLicensePlate()) != null) {
            return false;
        }

        // Find an available spot
        ParkingSpot spot = findAvailableSpot();

        if (spot == null) {
            return false;
        }

        // Store the Vehicle object inside the ParkingSpot
        spot.setVehicle(vehicle);
        return true;
    }

    // Finds a vehicle using its license plate
    public ParkingSpot findVehicle(String licensePlate) {

        if (licensePlate == null || licensePlate.isBlank()) {
            return null;
        }

        for (ParkingSpot spot : parkingSpots) {

            // Get the Vehicle object from the spot
            Vehicle vehicle = spot.getVehicle();

            if (vehicle != null &&
                    vehicle.getLicensePlate()
                            .equalsIgnoreCase(licensePlate)) {

                return spot;
            }
        }

        return null;
    }

    // Removes a vehicle from the parking lot
    public boolean removeVehicle(String licensePlate) {

        ParkingSpot spot = findVehicle(licensePlate);

        if (spot == null) {
            return false;
        }

        // Remove the Vehicle object
        spot.setVehicle(null);
        return true;
    }

    // Counts available parking spaces
    public int getAvailableSpaceCount() {

        int count = 0;

        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    // Calculates occupied spaces
    public int getOccupiedSpaceCount() {
        return parkingSpots.size() - getAvailableSpaceCount();
    }
}





