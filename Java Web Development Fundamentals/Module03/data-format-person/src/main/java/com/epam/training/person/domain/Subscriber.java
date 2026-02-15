package com.epam.training.person.domain;

import jakarta.xml.bind.annotation.*;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"phone", "clientName"})
public class Subscriber {

    @XmlElement(name = "phone-number")
    private String phone;

    @XmlElement(name = "client-name")
    private String clientName;

    public Subscriber() {}

    public Subscriber(String phone, String clientName) {
        this.phone = phone;
        this.clientName = clientName;
    }

    public String getPhone() { return phone; }

    public String getClientName() { return clientName; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(phone, that.phone) && Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, clientName);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "phone='" + phone + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
