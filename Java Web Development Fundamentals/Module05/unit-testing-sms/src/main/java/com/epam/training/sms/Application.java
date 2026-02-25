package com.epam.training.sms;

import java.util.Scanner;

class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmsEncoder encoder = new SmsEncoder();

        System.out.print("Please enter plaintext: ");

        String plaintext = scanner.nextLine();

        String ciphertext = encoder.encode(plaintext);
        String decoded = encoder.decode(ciphertext);

        System.out.printf("plaintext: %s%n"
                + "ciphertext: %s%n"
                + "decoded: %s", plaintext, ciphertext, decoded);
    }
}
