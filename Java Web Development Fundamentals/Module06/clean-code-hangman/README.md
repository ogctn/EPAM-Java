# Introduction
------------

In this exercise students get a working implementation of the Hangman game. The implementation intentionally does not follow Clean Code principles.  

The task is to improve the application, so that the student solution follows clean code principles.

New classes and packages can be introduced while refactoring the code.

## Game Rules

Details about the game rules can be found at the following link: [Hangman (game) - Wikipedia](https://en.wikipedia.org/wiki/Hangman_(game))


## High-level Functionality

The application should function as listed below:

*   Welcome the player and start the game.
*   Display the current state of the game: the word (each letter is hidden at the beginning), letters tried by user, and number of remaining guesses.
*   Prompt the user to input their guessed letter.
    *   The guess has to be a letter from the English alphabet and can't be one that was already used.
*   After guessing, the state of the game is evaluated and the following action is taken:
    *   If the game is not over yet, print the updated status of the game and ask for the next guess.
    *   If the game is over, print the outcome and the word.

Additional details about the implementation:

*   The application class stores the possible words in an array and selects one randomly from it when the game starts
*   The game ends in defeat after 7 wrong guesses


## User Interaction

Upon application start the welcome message is displayed, a new game starts and the player is prompted to input their first guess.

    Welcome to the Hangman game!
    
    The word: _ _ _ _ _ _ _ _
    Letters tried: []
    Wrong guesses until game over: 7
    Enter your guess:

After guessing, if the game is not finished yet, the updated progress is shown and the player can make their next guess.

    The word: c _ _ _ _ _ _ r
    Letters tried: [c, g, r, s]
    Wrong guesses until game over: 5
    Enter your guess:

Upon correctly guessing all the letters, the game ends. The application displays a victory message and the guessed word.

    Congratulations, you won!
    The word was: computer

If the player runs out of guesses before finding all the letters, the game ends with defeat and the guessed word is displayed.

    You have no more tries left, you lost the game
    The word was: computer

When trying to enter an already guessed or an invalid character, an error message should be displayed.

    The word: c _ _ _ _ _ _ _
    Letters tried: [c]
    Wrong guesses until game over: 7
    Enter your guess: c
    
    Error: the provided character has already been guessed

## Technical Specification

(package: com.epam.training.hangman)
 
| Class          | Description                                                                                                                                                                                                             |
|----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `HangmanLogic` | Contains the main logic of the game: guessing, updating the game state and the result.                                                                                                                                  |
|                | `void guess(char letter)`: handles the logic of guessing and updates the State accordingly. Throws `IllegalArgumentException`, if the guessed letter is not from the English alphabet or have already been used before. |
|                | String getDisplayedWord() returns the word to be displayed to the user                                                                                                                                                  |
|                | `State getState()` returns the state of the game.                                                                                                                                                                       |
|                | `List<Character> getLettersTried()` get the list of letters that the user has already tried to guess                                                                                                                    |
|                | `int getWrongGuessesLeft()` returns the number of wrong guesses so far                                                                                                                                                  |
| `State`        | Enum containing the three possible states of the game.                                                                                                                                                                  |


## Acceptance

Satisfy the following requirements:

*   good, descriptive names
*   object-oriented structure
*   functions do one thing
*   all the operations in a method are at the same level of abstraction
*   no command & query operations in the same method
*   functions have no side effects
*   DRY
*   no flag arguments
*   no SonarLint errors

## Notes

For the time being, Autocode may give maximum points for code quality, even when no Sonar errors are fixed. Ignore the points and focus on fixing all the errors.

You can ignore `System.out` warnings from SonarLint.
