package com.epam.rd.autotasks;



public class Main {
    public static void main(String[] args) {

        try {
            System.out.println(LoopStatements.sumOfFibonacciNumbers(-8));
        } catch (IllegalArgumentException e) {
            System.out.println("Hata: " + e.getMessage());

            System.out.println(LoopStatements.sumOfFibonacciNumbers(11));


        }
    }
}