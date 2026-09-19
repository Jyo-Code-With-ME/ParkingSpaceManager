package com.parkingspacemanager;

import java.time.LocalDateTime;

public class ParkingSpot {

    // Constructor
    public ParkingSpot(int spaceNumber) {

        if(spaceNumber<=0){
            throw new IllegalArgumentException("Space number be greater");
        }
        // spaceNumber refers to the constructor parameter
        this.spaceNumber = spaceNumber;

        // Initialize the parking spot as empty
        this.vehicle = null;

        // Initialize entry time as empty
        this.entryTime = null;
    }

    // Getter for space number
    public int getSpaceNumber() {
        return spaceNumber;
    }

    // Getter for Vehicle
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Setter for Vehicle
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Checks whether this parking spot is empty
    public boolean isAvailable() {
        return vehicle == null;
    }

    // Getter for entry time
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    // Setter for entry time
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    // final means the space number cannot change
    // after the object is created.
    private final int spaceNumber;

    // Stores the vehicle currently parked here
    private Vehicle vehicle;

    // Stores when the vehicle entered
    private LocalDateTime entryTime;

}
