package com.parkingspacemanager;

import java.util.Optional;
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
    public Optional <ParkingSpot> findAvailableSpot() {

        for (ParkingSpot spot : parkingSpots) {
            if (spot.getVehicle() == null) {
                return Optional.of(spot);
            }
        }
        // No available spot
        return Optional.empty();
    }

    // Parks a vehicle in an available spot
    public boolean parkVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            return false;
        }

        // Find an available spot
        Optional<ParkingSpot> spot = findAvailableSpot();

        if (spot.isEmpty()) {
            return false;
        }

        // Store the Vehicle object inside the ParkingSpot
        spot.get().setVehicle(vehicle);
        return true;
    }

    // Finds a vehicle using its license plate
    public Optional<ParkingSpot> findVehicle(String licensePlate) {

        if (licensePlate == null || licensePlate.isBlank()) {
            return Optional.empty();
        }

        for (ParkingSpot spot : parkingSpots) {

            // Get the Vehicle object from the spot
            Vehicle vehicle = spot.getVehicle();

            if (vehicle != null &&
                    vehicle.getLicensePlate()
                            .equalsIgnoreCase(licensePlate)) {

                return Optional.of(spot);
            }
        }

        return Optional.empty();
    }

    // Removes a vehicle from the parking lot
    public boolean removeVehicle(String licensePlate) {

        Optional<ParkingSpot> spot = findVehicle(licensePlate);

        if (spot.isEmpty()) {
            return false;
        }

        // Remove the Vehicle object
        spot.get().setVehicle(null);
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





