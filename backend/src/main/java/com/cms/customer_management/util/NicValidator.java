package com.cms.customer_management.util;

public class NicValidator {

    // OLD NIC: 9 digits + V or X
    private static final String OLD_NIC_REGEX = "^[0-9]{9}[VX]$";

    // NEW NIC: 12 digits
    private static final String NEW_NIC_REGEX = "^[0-9]{12}$";

    public static boolean isValid(String nic) {
        if (nic == null) {
            return false;
        }

        nic = nic.trim().toUpperCase();

        return nic.matches(OLD_NIC_REGEX) || nic.matches(NEW_NIC_REGEX);
    }
}
