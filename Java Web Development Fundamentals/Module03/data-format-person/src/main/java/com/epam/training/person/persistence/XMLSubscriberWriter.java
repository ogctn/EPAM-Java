package com.epam.training.person.persistence;

import com.epam.training.person.domain.Phonebook;
import com.epam.training.person.domain.Subscriber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.OutputStream;
import java.util.List;


public class XMLSubscriberWriter implements DataWriter<List<Subscriber>> {

    private final OutputStream out;

    public XMLSubscriberWriter(OutputStream out) {
        if (out == null)
            throw (new IllegalArgumentException(""));
        this.out = out;
    }

    @Override
    public void write(List<Subscriber> data) {
        try {
            Phonebook phonebook = new Phonebook(data);

            JAXBContext ctx = JAXBContext.newInstance(Phonebook.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            try {
                marshaller.setProperty("com.sun.xml.bind.indentString", "    ");
            } catch (Exception ignored) {
            }

            marshaller.marshal(phonebook, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        out.close();
    }
}
