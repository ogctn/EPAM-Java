package com.epam.training.person;

import com.epam.training.person.domain.Phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhoneParser {

    private static final Pattern FORMATS = Pattern.compile("^(\\d{3})-?(\\d{3})-?(\\d{4})$");

    private PhoneParser() { }

    public static Phone parse(String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty())
            throw (new IllegalArgumentException());

        String v = value.trim();
        Matcher m = FORMATS.matcher(v);
        if (!m.matches())
            throw (new IllegalArgumentException("Invalid phone: " + value));
        return (new Phone(m.group(1), m.group(2), m.group(3)));
    }
}
