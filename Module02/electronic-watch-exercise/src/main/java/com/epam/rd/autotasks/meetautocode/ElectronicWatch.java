package com.epam.rd.autotasks.meetautocode;

import java.util.Scanner;

public class ElectronicWatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int seconds = scanner.nextInt();
        int ss = seconds % 60;
        int mm = (seconds / 60) % 60;
        int hh = (seconds / 60 / 60) % 24;

        System.out.println(
                hh + ":" +
                (mm < 10 ? "0" : "") + mm +
                ":" + (ss < 10 ? "0" : "") + ss
        );
    }
}
