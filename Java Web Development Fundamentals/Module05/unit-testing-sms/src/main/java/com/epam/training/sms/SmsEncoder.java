package com.epam.training.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsEncoder {

    private static final Map<Character, String> CHAR_TO_CODE = new HashMap<>();

    static {
        // 0
        CHAR_TO_CODE.put(' ', "0");
        CHAR_TO_CODE.put('0', "00");
        // 1
        CHAR_TO_CODE.put('1', "1");
        // 2
        CHAR_TO_CODE.put('a', "2");
        CHAR_TO_CODE.put('b', "22");
        CHAR_TO_CODE.put('c', "222");
        CHAR_TO_CODE.put('2', "2222");
        // 3
        CHAR_TO_CODE.put('d', "3");
        CHAR_TO_CODE.put('e', "33");
        CHAR_TO_CODE.put('f', "333");
        CHAR_TO_CODE.put('3', "3333");
        // 4
        CHAR_TO_CODE.put('g', "4");
        CHAR_TO_CODE.put('h', "44");
        CHAR_TO_CODE.put('i', "444");
        CHAR_TO_CODE.put('4', "4444");
        // 5
        CHAR_TO_CODE.put('j', "5");
        CHAR_TO_CODE.put('k', "55");
        CHAR_TO_CODE.put('l', "555");
        CHAR_TO_CODE.put('5', "5555");
        // 6
        CHAR_TO_CODE.put('m', "6");
        CHAR_TO_CODE.put('n', "66");
        CHAR_TO_CODE.put('o', "666");
        CHAR_TO_CODE.put('6', "6666");
        // 7
        CHAR_TO_CODE.put('p', "7");
        CHAR_TO_CODE.put('q', "77");
        CHAR_TO_CODE.put('r', "777");
        CHAR_TO_CODE.put('s', "7777");
        CHAR_TO_CODE.put('7', "77777");
        // 8
        CHAR_TO_CODE.put('t', "8");
        CHAR_TO_CODE.put('u', "88");
        CHAR_TO_CODE.put('v', "888");
        CHAR_TO_CODE.put('8', "8888");
        // 9
        CHAR_TO_CODE.put('w', "9");
        CHAR_TO_CODE.put('x', "99");
        CHAR_TO_CODE.put('y', "999");
        CHAR_TO_CODE.put('z', "9999");
        CHAR_TO_CODE.put('9', "99999");
    }

    private static final Map<Character, List<Character>> CODE_TO_CHAR = new HashMap<>();

    static {
        CODE_TO_CHAR.put('0', List.of(' ', '0'));
        CODE_TO_CHAR.put('1', List.of('1'));
        CODE_TO_CHAR.put('2', List.of('a', 'b', 'c', '2'));
        CODE_TO_CHAR.put('3', List.of('d', 'e', 'f', '3'));
        CODE_TO_CHAR.put('4', List.of('g', 'h', 'i', '4'));
        CODE_TO_CHAR.put('5', List.of('j', 'k', 'l', '5'));
        CODE_TO_CHAR.put('6', List.of('m', 'n', 'o', '6'));
        CODE_TO_CHAR.put('7', List.of('p', 'q', 'r', 's', '7'));
        CODE_TO_CHAR.put('8', List.of('t', 'u', 'v', '8'));
        CODE_TO_CHAR.put('9', List.of('w', 'x', 'y', 'z', '9'));

    }

    public String encode(String plaintext) {
        StringBuilder result = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            String currentCode = CHAR_TO_CODE.get(c);
            if (currentCode == null) {
                throw new IllegalArgumentException("Substitute string not found for character " + c);
            }
            boolean needsSpace = result.length() != 0 && lastCharacterIsTheSameAsCurrent(result, currentCode);
            if (needsSpace) {
                result.append(' ');
            }
            result.append(currentCode);
        }
        return result.toString();
    }

    private boolean lastCharacterIsTheSameAsCurrent(StringBuilder result, String current) {
        return result.charAt(result.length() - 1) == current.charAt(0);
    }

    public String decode(String ciphertext) {
        if ("".equals(ciphertext)) {
            return "";
        }

        if (!ciphertext.matches("[\\d ]*")) {
            throw new IllegalArgumentException("Ciphertext must match [\\d ]*");
        }

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < ciphertext.length() - 1) {
            if (ciphertext.charAt(i) == ' ') {
                i++;
            } else {
                int repetitionCount = getRepetitionCount(ciphertext, i);
                result.append(getCharByCode(ciphertext.charAt(i), repetitionCount));
                i += repetitionCount;
            }
        }
        if (i == ciphertext.length() - 1) {
            result.append(getCharByCode(ciphertext.charAt(i), 1));
        }
        return result.toString();
    }

    private int getRepetitionCount(String ciphertext, int position) {
        int repetitionCount = 1;
        for (int i = position; i + 1 < ciphertext.length() && ciphertext.charAt(i) == ciphertext.charAt(i + 1); i++) {
            repetitionCount++;
        }
        return repetitionCount;
    }

    private char getCharByCode(char code, int repetitionCount) {
        List<Character> mappedCharacters = CODE_TO_CHAR.get(code);
        return mappedCharacters.get(repetitionCount - 1);
    }
}
