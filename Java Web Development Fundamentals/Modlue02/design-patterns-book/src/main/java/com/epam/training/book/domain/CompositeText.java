package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.List;

abstract public class CompositeText implements Text {

    abstract List<? extends Text> getChildren();

    public abstract int getNumberOfWords();
    public abstract List<Verse> getVersesContainingWord(String word);

}
