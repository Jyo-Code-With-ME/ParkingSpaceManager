package com.parkingspacemanager;

// DebitCardPayment implements PaymentMethod
public final class DebitCardPayment implements PaymentMethod {

    // Stores the card number
    private final String cardNumber;

    // Constructor
    public DebitCardPayment(String cardNumber) {

        // Initialize cardNumber
        this.cardNumber = cardNumber;

    }

    // Implementation of interface method
    @Override
    public boolean validate() {

        // Check whether card number contains exactly 16 digits
            return cardNumber != null && cardNumber.matches("\\d{16}");
        }

    // Implementation of interface method
    @Override
    public String getPaymentType() {
        return "Debit Card";
    }
}
