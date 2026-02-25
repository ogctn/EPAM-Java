package com.epam.training.unclean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.stream.Stream;

public class ApplicationTest {

    private Application underTest = new Application();

    public static Stream<Arguments> listTestcaseGenerator() {
        return Stream.of(
                Arguments.of(Named.of("[5, 3, 4]", List.of(5, 3, 4)), Named.of("when argument is [1, 2, 1, 5, 3, 2, 2, 2, 2, 4, 2]", List.of(1, 2, 1, 5, 3, 2, 2, 2, 2, 4, 2))),
                Arguments.of(Named.of("[3]", List.of(3)), Named.of("when argument is [1, 2, 1, 2, 1, 2, 1, 2, 3]", List.of(1, 2, 1, 2, 1, 2, 1, 2, 3))),
                Arguments.of(Named.of("[3]", List.of(3)), Named.of("when argument is [1, 1, 1, 2, 2, 2, 3]", List.of(1, 1, 1, 2, 2, 2, 3))),
                Arguments.of(Named.of("[1, 2]", List.of(1, 2)), Named.of("when argument is [1, 2]", List.of(1, 2))),
                Arguments.of(Named.of("[1]", List.of(1)), Named.of("[1]", List.of(1)))
        );
    }

    @ParameterizedTest
    @MethodSource("listTestcaseGenerator")
    @DisplayName("Filter should return ")
    public void testFilterWithLongList(List<Integer> expected, List<Integer> argument) {
        // Arrange
        // Act
        List<Integer> actual = underTest.filter(argument);
        // Assert
        Assertions.assertIterableEquals(expected, actual);
    }

    private static Stream<Arguments> emptyReturnValueTestcaseGenerator() {
        return Stream.of(
            Arguments.of(Named.of("[1, 2, 1, 2, 1, 2, 1, 2]", List.of(1, 2, 1, 2, 1, 2, 1, 2))),
            Arguments.of(Named.of("[0, 0, 0, 0]", List.of(0, 0, 0, 0))),
            Arguments.of(Named.of("[]", List.of())),
            Arguments.of(Named.of("[1, 1, 1, 2, 2, 2, 3, 3, 3, 3]", List.of(1, 1, 1, 2, 2, 2, 3, 3, 3, 3)))
        );
    }

    @ParameterizedTest
    @DisplayName("Filter should return [] when argument is ")
    @MethodSource("emptyReturnValueTestcaseGenerator")
    public void testFilterWithArgumentsReturningEmptyList(List<Integer> argument) {
        // Arrange
        // Act
        List<Integer> actual = underTest.filter(argument);
        // Assert
        Assertions.assertIterableEquals(List.of(), actual);
    }

    @Test
    @DisplayName("Filter should raise IllegalArgumentException when argument is null")
    public void testFilterWithNull() {
        // Arrange
        List<Integer> argument = null;
        // Act

        // Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->underTest.filter(argument));
    }
}
