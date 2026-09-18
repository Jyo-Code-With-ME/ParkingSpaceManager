package com.example.parkingspacemanager;

// Interface defines what a payment method must provide.
public interface PaymentMethod {

    // Any class implementing PaymentMethod
    // must implement these methods.
    boolean validate();

    String getPaymentType();
}
