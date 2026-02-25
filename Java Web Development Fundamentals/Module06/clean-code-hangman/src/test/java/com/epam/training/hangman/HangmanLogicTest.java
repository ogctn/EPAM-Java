package com.epam.training.hangman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HangmanLogicTest {

    private HangmanLogic testLogic;

    @BeforeEach
    void setUp() throws Exception{
        String testWord = "test";
        testLogic = new HangmanLogic(testWord);
    }

    @Test
    @DisplayName("Guess should throw IllegalArgumentException when given an invalid character")
    public void testGuessShouldThrowIllegalArgumentWhenGivenInvalidCharacter(){
        //GIVEN

        //WHEN

        //THEN
        assertThrows(IllegalArgumentException.class, () -> testLogic.guess('@'),
                "When guessing the '@' character IllegalArgumentException should be thrown!");
    }

    @Test
    @DisplayName("Guess should throw IllegalArgumentException when given the same character twice")
    public void testGuessShouldThrowIllegalArgumentWhenGivenTheSameCharacterTwice(){
        //GIVEN
        testLogic.guess('a');

        //WHEN

        //THEN
        assertThrows(IllegalArgumentException.class, () -> testLogic.guess('a'),
                "When guessing the 'a' character twice IllegalArgumentException should be thrown!");
    }

    @Test
    @DisplayName("State should be IN_PROGRESS when the game is started")
    public void testStateShouldBeInProgressWhenGameIsStarted(){
        //GIVEN

        //WHEN

        //THEN
        assertEquals(State.IN_PROGRESS, testLogic.getState());
    }


    @Test
    @DisplayName("State should be IN_PROGRESS when the first guess of the game is made")
    public void testStateShouldBeInProgressWhenTheFirstGuessIsMade(){
        //GIVEN

        //WHEN
        testLogic.guess('t');

        //THEN
        assertEquals(State.IN_PROGRESS, testLogic.getState());
    }

    @Test
    @DisplayName("State should be LOST when seven wrong guesses were made")
    public void testStateShouldBeLostWhenSevenWrongGuessesWereMade(){
        //GIVEN
        guessWrongSixTimes();

        //WHEN
        testLogic.guess('z');

        //THEN
        assertEquals(State.LOST, testLogic.getState());
    }

    @Test
    @DisplayName("State should be WON when every letter has been guessed correctly")
    public void testStateShouldBeWonWhenEveryLetterHasBeenGuessed(){
        //GIVEN

        //WHEN
        testLogic.guess('e');
        testLogic.guess('s');
        testLogic.guess('t');

        //THEN
        assertEquals(State.WON, testLogic.getState());
    }

    @Test
    @DisplayName("LettersTried should be empty when the game is started")
    public void testLettersTriedShouldBeEmptyWhenTheGameIsStarted(){
        //GIVEN

        //WHEN

        //THEN
        assertEquals(List.of(), testLogic.getLettersTried());
    }

    @Test
    @DisplayName("LettersTried should be updated when a wrong guess is made")
    public void testLettersTriedShouldChangeCorrectlyWhenWrongGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('a');
        testLogic.guess('c');
        testLogic.guess('b');

        //THEN
        assertEquals(List.of('a', 'c', 'b') ,testLogic.getLettersTried(), "When guessing " +
                "a, c and b, LettersTried should be updated with them in the given order");
    }

    @Test
    @DisplayName("LettersTried should be updated when a correct guess is made")
    public void testLettersTriedShouldChangeCorrectlyWhenCorrectGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('e');
        testLogic.guess('t');

        //THEN
        assertEquals(List.of('e', 't') ,testLogic.getLettersTried(), "When guessing " +
                "e, and t, LettersTried should be updated with them in the given order");
    }

    @Test
    @DisplayName("LettersTried should be updated in the correct order when any guess is made")
    public void testLettersTriedShouldChangeCorrectlyWhenMixedGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('t');
        testLogic.guess('a');
        testLogic.guess('e');
        testLogic.guess('b');

        //THEN
        assertEquals(List.of('t', 'a', 'e', 'b'), testLogic.getLettersTried(), "When guessing " +
                "t, a, e and b, LettersTried should be updated with them in the given order");
    }

    @Test
    @DisplayName("WrongGuessesLeft should decrease by one when a wrong guess is made")
    public void testWrongGuessesLeftShouldDecreaseWhenWrongGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('g');
        testLogic.guess('i');

        //THEN
        assertEquals(5, testLogic.getWrongGuessesLeft(), "The number of " +
                "allowed wrong guesses should go down twice when two wrong guess were made");
    }

    @Test
    @DisplayName("WrongGuessesLeft should not change when correct guesses are made")
    public void testWrongGuessesLeftShouldNotChangeWhenCorrectGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('t');
        testLogic.guess('e');

        //THEN
        assertEquals(7, testLogic.getWrongGuessesLeft());
    }

    @Test
    @DisplayName("WrongGuessesLeft should only decrease when a wrong guess is made")
    public void testWrongGuessesLeftShouldChangeCorrectlyWhenMixedGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('t');
        testLogic.guess('g');
        testLogic.guess('e');

        //THEN
        assertEquals(6, testLogic.getWrongGuessesLeft(), "The number of " +
                "allowed wrong guesses should only go down by one when one incorrect and two correct guesses are made");
    }

    @Test
    @DisplayName("WrongGuessesLeft should be zero when the game is lost")
    public void testWrongGuessesLeftShouldBeZeroWhenTheGameIsLost(){
        //GIVEN
        guessWrongSixTimes();

        //WHEN
        testLogic.guess('z');

        //THEN
        assertEquals(0, testLogic.getWrongGuessesLeft());
    }

    @Test
    @DisplayName("DisplayedWord should only show underscores when the game is started")
    public void testDisplayedWordShouldBeOnlyUnderscoresWhenTheGameIsStarted(){
        //GIVEN

        //WHEN

        //THEN
        assertEquals("____", testLogic.getDisplayedWord(), "When the guessed " +
                "word is four letter long, the displayed word should be four underscores");
    }

    @Test
    @DisplayName("DisplayedWord should show the entire word when the game is won")
    public void testDisplayedWordShouldBeEqualToTheGuessedWordWhenTheGameIsWon(){
        //GIVEN

        //WHEN
        testLogic.guess('t');
        testLogic.guess('s');
        testLogic.guess('e');

        //THEN
        assertEquals("test", testLogic.getDisplayedWord());
    }

    @Test
    @DisplayName("DisplayedWord should show the entire word when the game is lost")
    public void testDisplayedWordShouldBeEqualToTheGuessedWordWhenTheGameIsLost(){
        //GIVEN
        guessWrongSixTimes();

        //WHEN
        testLogic.guess('z');

        //THEN
        assertEquals("test", testLogic.getDisplayedWord());
    }

    @Test
    @DisplayName("DisplayedWord should show every instance of a letter when it is guessed")
    public void testDisplayedWordShouldChangeCorrectlyWhenLettersWithMultipleInstancesAreGuessed(){
        //GIVEN

        //WHEN
        testLogic.guess('t');

        //THEN
        assertEquals("t__t", testLogic.getDisplayedWord(), "When guessing 't' " +
                "with the word test, both instances of the letter should be displayed");
    }

    @Test
    @DisplayName("DisplayedWord should only change when a correct guess is made")
    public void testDisplayedWordShouldChangeCorrectlyWhenMixedGuessesAreMade(){
        //GIVEN

        //WHEN
        testLogic.guess('f');
        testLogic.guess('s');
        testLogic.guess('g');

        //THEN
        assertEquals("__s_", testLogic.getDisplayedWord(), "When guessing " +
                "f, s and g with the word test, only the third underscore should be replaced");
    }

    private void guessWrongSixTimes() {
        testLogic.guess('a');
        testLogic.guess('b');
        testLogic.guess('c');
        testLogic.guess('d');
        testLogic.guess('f');
        testLogic.guess('g');
    }

}
