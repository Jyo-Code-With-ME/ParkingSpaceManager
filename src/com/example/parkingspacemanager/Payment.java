package com.example.parkingspacemanager;


public class Payment {

    // Constant hourly parking rate.
    // static = belongs to the class.
    // final = cannot be changed.
    private static final double HOURLY_RATE = 5.00;

    // Stores number of parking hours
    private final double hours;

    // Stores the selected payment method
    private final PaymentMethod paymentMethod;

    // Stores the calculated payment amount
    private final double amount;

    // Constructor
    public Payment(double hours,Vehicle vechile PaymentMethod paymentMethod) {

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
        this.amount = hours * HOURLY_RATE * vehicle.getRateMultiplier();
    }

    // Processes the payment
    public boolean processPayment() {

        // Calling validate() on the payment method object
        return paymentMethod.validate();
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
