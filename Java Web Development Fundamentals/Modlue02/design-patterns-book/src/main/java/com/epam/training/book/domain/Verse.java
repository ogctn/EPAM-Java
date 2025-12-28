package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Verse implements Text {
    private Part parent;
    private final int number;
    private final String content;

    public Verse(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public void setParent(Part parent) {
        this.parent = parent;
    }

    public String format() { return (parent.format() + "," + this.number + " \"" + this.content) + "\""; }

    @Override
    public int getNumberOfWords() {
        if (content == null || content.isBlank())
            return (0);

        return (int) Arrays.stream(content.trim().split("[ ,.:;!?']+"))
                .filter(w -> !w.isBlank())
                .count();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        if (word == null || word.isBlank())
            return (List.of());

        String regex = "\\b" + Pattern.quote(word) + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        if (pattern.matcher(this.content).find())
            return (List.of(this));
        return (List.of());
    }
}
