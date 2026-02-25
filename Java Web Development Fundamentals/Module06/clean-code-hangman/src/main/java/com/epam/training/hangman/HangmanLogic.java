package com.epam.training.hangman;

import java.util.*;

public class HangmanLogic {

    private static final int MAX_WRONG_GUESSES = 7;
    private final String secretWord;
    private final List<Character> lettersTriedInOrder = new ArrayList<>();
    private final Set<Character> lettersTried = new HashSet<>();
    private final Set<Character> correctLetters = new HashSet<>();
    private int wrongGuesses = 0;
    private State state = State.IN_PROGRESS;

    public HangmanLogic(String word) {
        if (word == null || word.isBlank())
            throw (new IllegalArgumentException("word must not be blank"));

        String letterInLower = word.toLowerCase(Locale.ROOT);
        for (int i = 0; i < letterInLower.length(); i++) {
            char c = letterInLower.charAt(i);
            if (c < 'a' || c > 'z')
                throw (new IllegalArgumentException("word must contain only English letters"));
        }
        this.secretWord = letterInLower;
    }

    public void guess(char letter) {
        if (state != State.IN_PROGRESS)
            return;

        char letterInLower = Character.toLowerCase(letter);
        if (letterInLower < 'a' || letterInLower > 'z')
            throw (new IllegalArgumentException("Error: the provided character is not a letter"));
        if (lettersTried.contains(letterInLower))
            throw (new IllegalArgumentException("Error: the provided character has already been guessed"));

        lettersTried.add(letterInLower);
        lettersTriedInOrder.add(letterInLower);

        if (secretWord.indexOf(letterInLower) >= 0)
            correctLetters.add(letterInLower);
        else
            wrongGuesses++;

        if (isFullyGuessed())
            state = State.WON;
        else if (wrongGuesses >= MAX_WRONG_GUESSES) {
            wrongGuesses = MAX_WRONG_GUESSES;
            state = State.LOST;
        }
    }

    public String getDisplayedWord() {
        if (state != State.IN_PROGRESS)
            return (secretWord);

        StringBuilder sb = new StringBuilder(secretWord.length());
        for (int i = 0; i < secretWord.length(); i++) {
            char c = secretWord.charAt(i);
            sb.append(correctLetters.contains(c) ? c : '_');
        }
        return sb.toString();
    }

    public State getState() { return (state); }
    public List<Character> getLettersTried() { return List.copyOf(lettersTriedInOrder); }
    public int getWrongGuessesLeft() { return Math.max(0, MAX_WRONG_GUESSES - wrongGuesses); }

    private boolean isFullyGuessed() {
        for (int i = 0; i < secretWord.length(); i++) {
            if (!correctLetters.contains(secretWord.charAt(i)))
                return (false);
        }
        return (true);
    }

}
