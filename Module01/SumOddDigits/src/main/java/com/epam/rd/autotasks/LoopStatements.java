package com.epam.rd.autotasks;


class LoopStatements {
    public static int sumOddDigits(int n) {

        int digit, sum = 0;

        if (n <= 0)
            throw new IllegalArgumentException();
        while (n > 0) {
            digit = n % 10;

            if (digit % 2 == 1)
                sum += digit;

            n /= 10;
        }
        return (sum);
    }

}
