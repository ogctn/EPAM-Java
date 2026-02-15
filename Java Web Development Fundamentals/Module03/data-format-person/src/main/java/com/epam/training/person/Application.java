package com.epam.training.person;

import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Subscriber;
import com.epam.training.person.persistence.CSVPersonReader;
import com.epam.training.person.persistence.DataReader;
import com.epam.training.person.persistence.DataWriter;
import com.epam.training.person.persistence.XMLSubscriberWriter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    private void run() {
        List<Person> persons = new ArrayList<>();
        try (DataReader<List<Person>> reader = new CSVPersonReader(new FileInputStream("data/person.csv"))) {
            persons = reader.read();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Subscriber> subscribers = new PersonSubscriberTransformer(
                new PersonDateOfBirthPredicate(LocalDate.of(1990, 1, 1)))
                .transform(persons);

        try (DataWriter<List<Subscriber>> writer = new XMLSubscriberWriter(new FileOutputStream("data/phonebook.xml"))) {
            writer.write(subscribers);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}