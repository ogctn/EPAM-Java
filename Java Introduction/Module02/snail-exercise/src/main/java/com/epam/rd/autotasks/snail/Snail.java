package com.epam.rd.autotasks.snail;

import java.util.Scanner;

public class Snail
{
    public static void main(String[] args)
    {
       Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int h = sc.nextInt();
        int days = 0;
        int step = a - b;

        if (a >= h) {
            days = 1;
        } else if (step > 0) {
            days = 1 + (int) Math.ceil((double)((h - a) / step));
        } else {
            days = 0;
        }
        System.out.println(days == 0 ? "Impossible" : days);
    }
}
