package com.epam.rd.autotasks;


class LoopStatements {
    public static int sumOfFibonacciNumbers(int n) {

        int back = 0, front = 1, sum = 0, next;

        if (n < 0)
            throw new IllegalArgumentException();

        for (int i = 0; i < n; i++) {
            sum += back;
            next = back + front;
            back = front;
            front = next;
        }
        return (sum);
    }
}
