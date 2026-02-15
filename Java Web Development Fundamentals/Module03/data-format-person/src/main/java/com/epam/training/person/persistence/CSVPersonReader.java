package com.epam.training.person.persistence;

import com.epam.training.person.PhoneParser;
import com.epam.training.person.domain.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import com.epam.training.person.PhoneParser;



public class CSVPersonReader implements DataReader<List<Person>> {

    private final BufferedReader reader;

    public CSVPersonReader(InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException();
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    @Override
    public List<Person> read() {
        try {
            List<Person> persons = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank())
                    continue;
                persons.add(parseLine(line));
            }
            return persons;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Person parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 11)
            throw (new IllegalArgumentException("Invalid CVS data: " + Arrays.toString(parts)));
        UUID id = UUID.fromString(parts[0].trim());
        String name = parts[1].trim();
        LocalDate dob = LocalDate.parse(parts[2].trim());
        Location location = new Location(
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim()
        );
        Gender gender = Gender.valueOf(parts[7].trim().toUpperCase());

        List<Phone> phones = new ArrayList<>();
        for (int i = 8; i <= 10; i++) {
            String raw = parts[i].trim();
            if (raw.isEmpty() || raw.equals("-"))
                continue;
            phones.add(PhoneParser.parse(raw));
        }
        return (new Person(id, name, dob, location, gender, List.copyOf(phones)));
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
