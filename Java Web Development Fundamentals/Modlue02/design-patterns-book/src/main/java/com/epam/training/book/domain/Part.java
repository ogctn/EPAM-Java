package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.*;
import java.util.stream.Collectors;

public class Part extends CompositeText {
    private Book parent;
    private final int number;
    private final List<Verse> verses = new ArrayList<>();

    public Part(int number) {
        this.number = number;
    }

    public void setParent(Book parent) {
        this.parent = parent;
    }

    public int getNumber() {
        return number;
    }
    public String getBookName() { return (parent.getTitle()); }
    public String format() { return (parent.getTitle() + " " + number); }

    public void addVerse(Verse verse) {
        verse.setParent(this);
        verses.add(verse);
    }

    public List<? extends Text> getChildren() { return (Collections.unmodifiableList(verses)); }

    @Override
    public int getNumberOfWords() {
        return verses.stream()
                .mapToInt(Verse::getNumberOfWords)
                .sum();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        return verses.stream()
                .flatMap(verse -> verse.getVersesContainingWord(word).stream())
                .collect(Collectors.toList());
    }


}
