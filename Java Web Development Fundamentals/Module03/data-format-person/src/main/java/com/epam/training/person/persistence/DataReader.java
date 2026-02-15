package com.epam.training.person.persistence;

public interface DataReader<T> extends AutoCloseable {
    T read();
}
