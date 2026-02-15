package com.epam.training.person;

public interface Transformer<I, O> {
    O transform(I input);
}
