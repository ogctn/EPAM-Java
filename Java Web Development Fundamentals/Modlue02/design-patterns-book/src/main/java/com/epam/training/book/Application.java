package com.epam.training.book;

import com.epam.training.book.domain.AllBooks;
import com.epam.training.book.domain.Verse;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        AllBooks allBooks = TestDataCreator.createAllBooks();

        // word count
        int numberOfWords = 0; // = allBooks.getNumberOfWords();
        System.out.println("Number of all words: " + numberOfWords);

        // contain word
        List<Verse> verses = new ArrayList<>(); // = allBooks.getVersesContainingWord("God");
        System.out.println("Verses containing the word 'God':");
        for (Verse v : verses) {
            System.out.println(v.format());
        }
    }

}
