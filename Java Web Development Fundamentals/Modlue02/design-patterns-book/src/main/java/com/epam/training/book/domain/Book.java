package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends CompositeText {
    private final String title;
    private final List<Part> parts = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPart(Part part) {
        part.setParent(this);
        parts.add(part);
    }

    public List<? extends Text> getChildren() { return (Collections.unmodifiableList(parts)); }

    @Override
    public int getNumberOfWords() {
        return parts.stream()
                .mapToInt(Part::getNumberOfWords)
                .sum();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        return parts.stream()
                .flatMap(part -> part.getVersesContainingWord(word).stream())
                .collect(Collectors.toList());
    }
}
