package com.epam.rd.autotasks.collections.map;

import java.util.Objects;

public class Author implements Comparable<Author> {
    private final String firstName;
    private final String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    /**
     * The natural ordering is by first name in ascending order,
     * then by last name in ascending order.
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */

    @Override
    public int compareTo(Author o) {
        if (o == null)
            throw (new NullPointerException());
        if (getClass() != o.getClass())
            throw (new ClassCastException());
        int res = this.firstName.compareTo(o.firstName);
        if (res != 0)
            return (res);
        return (this.lastName.compareTo(o.lastName));
    }
}
