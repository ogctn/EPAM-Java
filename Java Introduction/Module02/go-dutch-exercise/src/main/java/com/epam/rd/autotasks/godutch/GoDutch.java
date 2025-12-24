package com.epam.rd.autotasks.godutch;

import java.util.Scanner;

public class GoDutch {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int bill = sc.nextInt();
        int friendCount = sc.nextInt();

        if (bill < 0) {
            System.out.println("Bill total amount cannot be negative");
            return;
        } else if (! (friendCount > 0)) {
            System.out.println("Number of friends cannot be negative or zero");
            return;
        } else {
            System.out.println((int)(((bill * 1.1 / friendCount))));
        }
    }
}
