package com.epam.rd.autotasks.pizzasplit;

import java.util.Scanner;

public class PizzaSplit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int people = sc.nextInt();
        int slices = sc.nextInt();

        for(int i = 1; i <= people * slices; i++) {
            if ((i * slices) % people == 0) {
                System.out.println(i);
                break;
            }
        }
    }
}
