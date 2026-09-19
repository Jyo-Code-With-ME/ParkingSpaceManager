package com.parkingspacemanager;

public final class InputValidator {
    private InputValidator() {
    }

    public static boolean isValidLicensePlate(String licensePlate) {
        return licensePlate != null
                && !licensePlate.isBlank()
                && licensePlate.matches("[A-Z0-9]+");
    }

    public static boolean isValidBrandModel(String brandModel) {
        return brandModel != null && !brandModel.isBlank();
    }

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null
                && cardNumber.matches("\\d{16}");
    }
}
