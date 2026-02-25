# Introduction

Early mobile phones with 3x4 numeric keypads used the following method to input text:

Each key has several letters associated with it.
To select a letter, the user presses the corresponding key as many times as the letter's position on that key.

A letter selection is confirmed when one of the following occurs:

*   The user starts entering a letter using a different key.
*   A certain amount of time passes without any input.

## Mobile Phone Keypad

See the standard keypad layout below.

![](https://gitlab.com/autocode-assessment/exercise_fundamentals/-/raw/main/unit-testing-sms/phone-keypad.png)

## Examples

Below are example input sequences and their corresponding decoded messages:
 
| Text | Keycodes |
|------|----------|
| go   | 4666     |
| java | 528882   |
| bar  | 22 2777  |

## Example Console Input and Output

    Please enter plaintext: java
    plaintext: java
    ciphertext: 528882
    decoded: java

## Implementation 

The `SmsEncoder` class defines two methods:

- `String encode(String plaintext)` - Encodes the given text.
   Throws `IllegalArgumentException` if the text contains a character that cannot be encoded.
   Valid characters are English lowercase letters ('a'-'z'). Invalid example: 'é'.
- `String decode(String ciphertext)` - Decodes the SMS keycodes.
  Throws `IllegalArgumentException` if the input contains characters that cannot be recognized as keys.

# Task

Your task is to write unit tests for the `SmsEncoder` methods. Please cover the following cases:
- Encoding a character mapped to a single key ('a' -> 2)
- Encoding a character mapped to a double key ('b' -> 22)
- Encoding multiple characters
- Encoding multiple characters that are mapped to the same key, requiring a space in the encoded text
- Empty input
- Invalid input

Please write similar tests for the decode method.

## Test Method Naming

- Use simple method names that clearly indicate the purpose of each test.
- Method names should include the method being tested (encode or decode).
- Add `@DisplayName` annotation to provide a more descriptive name for each test case.
