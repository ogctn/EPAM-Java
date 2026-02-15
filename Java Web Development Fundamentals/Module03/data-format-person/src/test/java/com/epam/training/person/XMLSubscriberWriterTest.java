package com.epam.training.person;

import com.epam.training.person.domain.Subscriber;
import com.epam.training.person.persistence.XMLSubscriberWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLSubscriberWriterTest {

    private static final String EXPECTED = """
            <phonebook>
                <subscriber>
                    <phone-number>123-456-7890</phone-number>
                    <client-name>John Doe</client-name>
                </subscriber>
                <subscriber>
                    <phone-number>111-111-2222</phone-number>
                    <client-name>John Doe</client-name>
                </subscriber>
                <subscriber>
                    <phone-number>222-222-1111</phone-number>
                    <client-name>Jane Smith</client-name>
                </subscriber>
                <subscriber>
                    <phone-number>333-333-1111</phone-number>
                    <client-name>Michael Johnson</client-name>
                </subscriber>
                <subscriber>
                    <phone-number>333-333-2222</phone-number>
                    <client-name>Michael Johnson</client-name>
                </subscriber>
            </phonebook>""";

    private XMLSubscriberWriter underTest;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void initWriter() {
        outputStream = new ByteArrayOutputStream();
        underTest = new XMLSubscriberWriter(outputStream);
    }

    @DisplayName("Passing three subscribers to be persisted and save to XML.")
    @Test
    public void writeTest() {
        //Given
        List<Subscriber> subscribers = SubscriberFactory.create();
        //When
        underTest.write(subscribers);
        String actual = outputStream.toString().trim();
        actual = actual.substring(actual.indexOf("<phonebook>"));
        //Then
        assertEquals(EXPECTED, actual);
    }
}
