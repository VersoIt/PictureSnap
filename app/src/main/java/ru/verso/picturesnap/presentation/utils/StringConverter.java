package ru.verso.picturesnap.presentation.utils;

public class StringConverter {

    public static String convertPhoneNumberToConvenientFormat(String phoneNumber) {

        return String.format("%s (%s) %s-%s-%s",
                phoneNumber.substring(0, 2),
                phoneNumber.substring(2, 5),
                phoneNumber.substring(5, 8),
                phoneNumber.substring(8, 10),
                phoneNumber.substring(10, 12));
    }
}
