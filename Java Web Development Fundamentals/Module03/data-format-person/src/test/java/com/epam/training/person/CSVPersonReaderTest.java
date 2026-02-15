package com.epam.training.person;

import com.epam.training.person.domain.Person;
import com.epam.training.person.persistence.CSVPersonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVPersonReaderTest {
    private CSVPersonReader underTest;

    @BeforeEach
    public void addInputStreamToReader() throws IOException {
        List<String> lines = List.of(
                "2b1a8035-0aa3-4865-9f0f-5c3e46a11289,John Doe,1995-07-15,US,United States,12345,New York,Male,1234567890,111-111-2222,-",
                "d0b9301d-6720-4764-a527-8f12f4f99b1e,Jane Smith,1990-01-01,CA,Canada,V1X 2Y3,Toronto,Female,222-222-1111,-,-",
                "c82a14c3-9309-4e6d-a2f3-7162f6dbb159,Michael Johnson,1978-11-05,GB,United Kingdom,SW1A 1AA,London,Male,333-333-1111,3333332222,-");
        String concatenatedString = lines.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        byte[] bytes = concatenatedString.getBytes(StandardCharsets.UTF_8);
        InputStream in = new ByteArrayInputStream(bytes);
        underTest = new CSVPersonReader(in);
    }

    @DisplayName("Read method should return list of persons populated out of CSV")
    @Test
    public void readerOkTest() {
        //Arrange
        List<Person> expected = PersonFactory.create();

        //Act
        List<Person> actual = underTest.read();

        //Assert
        assertIterableEquals(expected, actual, "Lists do not match!");
    }

    @DisplayName("CSV processing test Act phone number format is invalid")
    @ParameterizedTest
    @MethodSource("getInput")
    public void readerFailTest(String line) throws IOException {
        //Arrange
        byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
        InputStream in = new ByteArrayInputStream(bytes);

        underTest = new CSVPersonReader(in);

        //Act
        //Assert
        assertThrows(IllegalArgumentException.class, () -> underTest.read());
    }

    static Stream<Arguments> getInput() {
        return Stream.of(
                Arguments.of("2b1a8035-0aa3-4865-9f0f-5c3e46a11289,John Doe,1995-07-15,US,United States,12345,New York,Male,1234567,-,-"),
                Arguments.of("2b1a8035-0aa3-4865-9f0f-5c3e46a11289,John Doe,1995-07-15,US,United States,12345,New York,Male,-,555-555-55555,-"),
                Arguments.of("2b1a8035-0aa3-4865-9f0f-5c3e46a11289,John Doe,1995-07-15,US,United States,12345,New York,Male,-,-,1fagds"),
                Arguments.of("2b1a8035-0aa3-4865-9f0f-5c3e46a11289,John Doe,1995-07-15,US,United States,12345,New York,Male,--,-,-")
        );
    }
}
