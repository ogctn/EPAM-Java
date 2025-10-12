package com.epam.rd.autotasks.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorCodeValidation {
    public static boolean validateColorCode(String color) {
        if (color == null || color.isEmpty())
            return (false);
        Pattern hexPattern = Pattern.compile("^(?i)#[0-9a-f]{3}([0-9a-f]{3})?$");
        Matcher hexMatcher = hexPattern.matcher(color);
        return (hexMatcher.matches());
    }
}
