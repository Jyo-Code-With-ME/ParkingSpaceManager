package com.parkingspacemanager;

public enum VehicleType {
    CAR("1", "Car"),
    TRUCK("2", "Truck"),
    MOTORCYCLE("3", "Motorcycle");

    private final String menuCode;
    private final String displayName;

    VehicleType(String menuCode, String displayName) {
        this.menuCode = menuCode;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Looks up a VehicleType from the menu code the user typed.
    // Returns null if the code doesn't match any known vehicle type.
    public static VehicleType fromMenuCode(String menuCode) {

        for (VehicleType type : values()) {
            if (type.menuCode.equals(menuCode)) {
                return type;
            }
        }

        return null;
    }
}
