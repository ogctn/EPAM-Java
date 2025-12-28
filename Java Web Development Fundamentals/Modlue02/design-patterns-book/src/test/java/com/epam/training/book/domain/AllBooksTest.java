package com.epam.training.book.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AllBooksTest {
    public static final String VERSE_CREATION = "So God created mankind in his own image ,in the image of God he created them ; male and female he created them .";
    public static final String VERSE_RAINBOW = "I have set my rainbow in the clouds";

    private AllBooks underTest;

    @BeforeEach
    public void init() {
        underTest = new AllBooks();

        Book bookGenesis = new Book("Genesis");
        Part genesisPart1 = new Part(1);
        genesisPart1.addVerse(new Verse(27, VERSE_CREATION));
        bookGenesis.addPart(genesisPart1);

        Part genesisPart2 = new Part(2);
        genesisPart2.addVerse(new Verse(13, VERSE_RAINBOW));
        bookGenesis.addPart(genesisPart2);

        underTest.addBook(bookGenesis);
    }

    @Test
    @DisplayName("'Number of words in all books' test")
    public void getNumberOfWords() {
        //GIVEN - WHEN
        int result = underTest.getNumberOfWords();

        //THEN
        assertEquals(30, result);
    }

    @ParameterizedTest
    @DisplayName("'Retrieve occurrences by a given word' test")
    @MethodSource("getVersesByWordSource")
    public void testGetVersesContainingWord(String searchWord, List<String> expectedContents) {
        //GIVEN - WHEN
        List<Verse> result = underTest.getVersesContainingWord(searchWord);
        List<String> verseContents = result.stream()
                .map(Verse::getContent)
                .collect(Collectors.toList());

        //THEN
        assertAll("Get occurrences by word assertions",
                () -> assertEquals(expectedContents.size(), result.size()),
                () -> assertIterableEquals(expectedContents, verseContents));
    }

    private static Stream<Arguments> getVersesByWordSource() {
        return Stream.of(
                Arguments.of("God", List.of(VERSE_CREATION)),
                Arguments.of("in", List.of(VERSE_CREATION, VERSE_RAINBOW)),
                Arguments.of("bow", List.of())
        );
    }
}