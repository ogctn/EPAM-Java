package com.epam.rd.autotask.stream.intermediate;

import com.epam.rd.autotask.stream.intermediate.model.Genre;
import com.epam.rd.autotask.stream.intermediate.model.Movie;
import com.epam.rd.autotask.stream.intermediate.model.Person;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieQueries {

    private final List<Movie> movies;

    public MovieQueries(List<Movie> movies) {
        if (movies == null)
            throw (new IllegalArgumentException());
        this.movies = movies;
    }

    public boolean checkGenreOfAllMovies(Genre genre) {
        return movies.stream()
                .allMatch(m -> m.getGenres().contains(genre));
    }

    public boolean checkGenreOfAnyMovies(Genre genre) {
        return movies.stream()
                .anyMatch(m -> m.getGenres().contains(genre));
    }

    public boolean checkMovieIsDirectedBy(Person person) {
        return movies.stream()
                .anyMatch(m -> m.getDirectors().contains(person));
    }

    public int calculateTotalLength() {
        return movies.stream()
                .mapToInt(Movie::getLength)
                .sum();
    }

    public long moviesLongerThan(int minutes) {
        return movies.stream()
                .filter(m -> m.getLength() > minutes)
                .count();
    }

    public List<Person> listOfWriters() {
        return movies.stream()
                .map(Movie::getWriters)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public String[] movieTitlesWrittenBy(Person person) {
        return movies.stream()
                .filter(m -> m.getWriters().contains(person))
                .map(Movie::getTitle)
                .toArray(String[]::new);
    }

    public List<Integer> listOfLength() {
        return movies.stream()
                .map(Movie::getLength)
                .collect(Collectors.toList());
    }

    public Movie longestMovie() {
        return movies.stream()
                .reduce((m1, m2) -> m1.getLength() > m2.getLength() ? m1 : m2)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Movie oldestMovie() {
        return movies.stream()
                .min(Comparator.comparingInt(Movie::getReleaseYear))
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Movie> sortedListOfMoviesBasedOnReleaseYear() {
        return movies.stream()
                .sorted(Comparator.comparingInt(Movie::getReleaseYear))
                .collect(Collectors.toList());
    }

    public List<Movie> sortedListOfMoviesBasedOnTheDateOfBirthOfOldestDirectorsOfMovies() {
        return movies.stream()
                .sorted( (m1, m2) -> {
                    Optional<LocalDate> oldest1 = m1.getDirectors().stream()
                            .map(Person::getDateOfBirth)
                            .reduce((bd1, bd2) -> bd1.isBefore(bd2) ? bd1 : bd2);

                    Optional<LocalDate> oldest2 = m2.getDirectors().stream()
                            .map(Person::getDateOfBirth)
                            .reduce((bd1, bd2) -> bd1.isBefore(bd2) ? bd1 : bd2);

                    return oldest1.orElse(LocalDate.MAX).compareTo(oldest2.orElse(LocalDate.MAX));
                })
                .collect(Collectors.toList());
    }

    public List<Movie> moviesReleasedEarlierThan(int releaseYear) {
        return movies.stream()
                .filter(m -> m.getReleaseYear() <= releaseYear)
                .collect(Collectors.toList());
    }

}
