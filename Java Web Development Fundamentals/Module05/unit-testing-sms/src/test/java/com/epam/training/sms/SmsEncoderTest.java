package com.epam.training.sms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmsEncoderTest {

    private SmsEncoder underTest;

    @BeforeEach
    void init() {
        underTest = new SmsEncoder();
    }

    @Nested
    @DisplayName("encode()")
    class EncodeTests {
        @Test
        @DisplayName("encode: single key ('a' -> 2)")
        void encode_singleKeyCharacter_a_returns2() {
            String actual = underTest.encode("a");
            assertEquals("2", actual);
        }
        @Test
        @DisplayName("encode: double key ('b' -> 22)")
        void encode_doubleKeyCharacter_b_returns22() {
            String actual = underTest.encode("b");
            assertEquals("22", actual);
        }
        @Test
        @DisplayName("encode: multiple characters: (java -> 528882)")
        void encode_multipleCharacters_java_returns528882() {
            String actual = underTest.encode("java");
            assertEquals("528882", actual);
        }
        @Test
        @DisplayName("encode: multiple characters same key requires space (bar -> 22 2777)")
        void encode_multipleSameKey_bar_insertsSpace() {
            String actual = underTest.encode("bar");
            assertEquals("22 2777", actual);
        }
        @Test
        @DisplayName("encode: empty input returns empty string")
        void encode_emptyInput_returnsEmpty() {
            String actual = underTest.encode("");
            assertEquals("", actual);
        }
        @ParameterizedTest
        @ValueSource(strings = {"A", "é", "?", "\n"})
        @DisplayName("encode: invalid input throws IllegalArgumentException")
        void encode_invalidInput_throwsIllegalArgumentException(String plaintext) {
            assertThrows(IllegalArgumentException.class, () -> underTest.encode(plaintext));
        }
    }

    @Nested
    @DisplayName("decode()")
    class DecodeTests {
        @Test
        @DisplayName("decode: single key (2 -> 'a')")
        void decode_singleKey_2_returnsA() {
            String actual = underTest.decode("2");
            assertEquals("a", actual);
        }
        @Test
        @DisplayName("decode: double key (22 -> 'b')")
        void decode_doubleKey_22_returnsB() {
            String actual = underTest.decode("22");
            assertEquals("b", actual);
        }
        @Test
        @DisplayName("decode: multiple characters (528882 -> java)")
        void decode_multipleCharacters_528882_returnsJava() {
            String actual = underTest.decode("528882");
            assertEquals("java", actual);
        }
        @Test
        @DisplayName("decode: multiple characters same key requires space (22 2777 -> bar)")
        void decode_multipleSameKey_22_2777_returnsBar() {
            String actual = underTest.decode("22 2777");
            assertEquals("bar", actual);
        }
        @Test
        @DisplayName("decode: empty input returns empty string")
        void decode_emptyInput_returnsEmpty() {
            String actual = underTest.decode("");
            assertEquals("", actual);
        }

        @ParameterizedTest
        @ValueSource(strings = {"deadbeef", "22x", "abc", "1-1", "2\n2", "*", "9a9"})
        @DisplayName("decode: invalid input throws IllegalArgumentException")
        void decode_invalidInput_throwsIllegalArgumentException(String ciphertext) {
            assertThrows(IllegalArgumentException.class, () -> underTest.decode(ciphertext));
        }
    }
}
