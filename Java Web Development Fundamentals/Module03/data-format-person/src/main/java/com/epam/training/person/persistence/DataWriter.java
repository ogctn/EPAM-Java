package com.epam.training.person.persistence;

public interface DataWriter<T> extends AutoCloseable {
    void write(T data);
}
