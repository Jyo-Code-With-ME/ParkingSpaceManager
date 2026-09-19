package com.parkingspacemanager;


public class Payment {

    private final double hours;
    private final PaymentMethod paymentMethod;
    private final double amount;

    // Constructor
    public Payment(double hours,double baseHourlyRate,Vehicle vehicle ,PaymentMethod paymentMethod) {

        // Validate hours
        if (hours <= 0) {
            throw new IllegalArgumentException(
                    "Parking time must be greater than zero."
            );
        }

        // Validate payment method
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle is required."
            );
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException(
                    "Payment method is required."
            );
        }

        // Initialize fields
        this.hours = hours;
        this.paymentMethod = paymentMethod;

         // vehicle.getRateMultiplier() - polymorphic call: the actual
        // value returned depends on the vehicle's real runtime type
        // (Car, Truck, or Motorcycle), resolved via dynamic dispatch.
        this.amount = hours * baseHourlyRate * vehicle.getRateMultiplier();
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Getter for hours
    public double getHours() {
        return hours;
    }

    // Gets payment type
    public String getPaymentType() {
        return paymentMethod.getPaymentType();
    }

}
