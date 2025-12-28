package com.epam.training.book.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VerseTest {
    private Verse underTest;
    private Part matthewPart1;
    private Book bookMatthew;

    @BeforeEach
    public void init() {
        matthewPart1 = new Part(1);
        bookMatthew = new Book("Matthew");
        underTest = new Verse(23,
                "'The virgin will conceive and give birth to a son, and they will call him Immanuel' (which means 'God with us').");
        matthewPart1.addVerse(underTest);
        bookMatthew.addPart(matthewPart1);
    }

    @Test
    @DisplayName("'Verse location print' test")
    public void testPrint() {
        //GIVEN - WHEN
        String result = underTest.format();

        //THEN
        assertEquals("Matthew 1,23 \"'The virgin will conceive and give birth to a son, and they will call him Immanuel' (which means 'God with us').\"", result);
    }
}