package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AllBooks extends CompositeText {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public int getNumberOfWords() {
        return books.stream()
                .mapToInt(Book::getNumberOfWords)
                .sum();
    }

    public List<Verse> getVersesContainingWord(String searchWord) {
        return books.stream()
                .flatMap(book -> book.getVersesContainingWord(searchWord).stream())
                .collect(Collectors.toList());
    }

    public List<? extends Text> getChildren() { return (Collections.unmodifiableList(books)); }

}
