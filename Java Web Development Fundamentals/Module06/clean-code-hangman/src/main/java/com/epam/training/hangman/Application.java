package com.epam.training.hangman;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Random r = new Random();
        String[] db = {"hangman", "apple", "bee", "clean", "computer", "office", "recursion"};
        final String w = db[r.nextInt(db.length)];
        HangmanLogic l = new HangmanLogic(w);

        System.out.println("Welcome to the Hangman game!\n");
        while (l.getState() == State.IN_PROGRESS) {
            System.out.println("The word: " + l.getDisplayedWord());
            System.out.println("Letters tried: " + l.getLettersTried());
            System.out.println("Wrong guesses until game over: " + (7 - l.getWrongGuessesLeft()));

            while (true) {
                String in = "";
                boolean stay = true;
                while (stay) {
                    System.out.print("Enter your guess: ");
                    in = scanner.next();
                    System.out.println();
                    if (in.length() == 1)
                        stay = false;
                    else
                        System.out.println("Error: please enter a single character only!\n");
                }
                try {
                    l.guess(in.charAt(0));
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }
        }
        printResult(l.getState(), w);
    }

    private static void printResult(final State state, final String correctWord) {
        if (state == State.WON) {
            System.out.println("Congratulations, you won!");
            System.out.println("The word was: " + correctWord);
        } else {
            System.out.println("You have no more tries left, you lost the game");
            System.out.println("The word was: " + correctWord);
        }
    }

}