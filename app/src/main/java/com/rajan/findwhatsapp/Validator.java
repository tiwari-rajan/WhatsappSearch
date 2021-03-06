package com.rajan.findwhatsapp;

import android.util.Patterns;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class to check some validation.
 */
public class Validator {

    /**
     * @param obj
     * @return true/false
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * @param obj
     * @return true/false
     */
    public static boolean isNullOrEmpty(Collection obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * @param obj
     * @return true/false
     */
    public static boolean isNullOrEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * @param obj
     * @return true/false
     */
    public static boolean isNullOrEmpty(Map obj) {
        return obj == null || obj.isEmpty();
    }

    /**
     * @param email
     * @return true, if it is valid
     * check if enter email is valid or not
     */
    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    /**
     * checks if string is empty or null
     *
     * @param text
     * @return true, if null or empty
     */
    public static boolean isEmptyString(String text) {
        return text == null || text.trim().length() == 0;
    }

    /**
     * @param name
     * @return true, if name is invalid
     */
    public static boolean invalidName(String name) {
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*,.()_+=|<>?{}\\[\\]~-]");
        Matcher hasDigit = digit.matcher(name);
        Matcher hasSpecial = special.matcher(name);

        return hasDigit.find() || hasSpecial.find();
    }

    /**
     * @param phoneNo
     * @return true, if phone no. is invalid
     */
    public static boolean isInvalidPhoneNo(CharSequence phoneNo) {
        Pattern pattern = Pattern.compile("^([+][9][1]|[9][1]|[0]){0,1}([7-9]{1})([0-9]{9})$");
        return !pattern.matcher(phoneNo).find();
    }
}
