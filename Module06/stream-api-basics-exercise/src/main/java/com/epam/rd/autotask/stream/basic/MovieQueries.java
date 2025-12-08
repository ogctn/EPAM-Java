package com.epam.rd.autotask.stream.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieQueries {

    private final List<String> movies;

    public MovieQueries(List<String> movies) {
        if (movies == null)
            throw (new IllegalArgumentException());
        this.movies = movies;
    }

    public long getNumberOfMovies() {
        return (movies.size());
    }

    public long getNumberOfMoviesThatStartsWith(String start) {
        return movies.stream()
                .filter(s -> s.startsWith(start))
                .count();
    }

    public long getNumberOfMoviesThatStartsWithAndEndsWith(String start, String end) {
        return movies.stream()
                .filter(s -> s.startsWith(start))
                .filter(s -> s.endsWith(end))
                .count();
    }

    public List<Integer> getLengthOfTitleOfMovies() {
        return movies.stream()
                .map(String::length)
                .collect(Collectors.toList());
    }

    public int getNumberOfLettersInShortestTitle() {

        return movies.stream()
                .map(String::length)
                .sorted()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Optional<String> getFirstTitleThatContainsThreeWords() {
        return movies.stream()
                .filter(title -> title.trim().split("\\s+").length == 3)
                .findFirst();
    }

    public List<String> getFirstFourTitlesThatContainAtLeastTwoWords() {
        return movies.stream()
                .filter(title -> title.trim().split("\\s+").length > 2)
                .limit(4)
                .collect(Collectors.toList());
    }

    public void printAllTitleToConsole() {
        movies.forEach(System.out::println);
    }
}
