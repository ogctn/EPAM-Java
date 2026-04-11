package com.epam.training.food.domain;


import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public class Credentials {
    private String userName;
    private String password;

    public Credentials() {}

    public String getUserName() { return userName; }
    public void setUserName(String email) { this.userName = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

}
