package com.epam.rd.autotasks;

import java.util.Arrays;

class Spiral {
    static int[][] spiral(int rows, int columns) {
        int[][] body = new int[rows][columns];
        int max = rows * columns;
        int wall_r = columns - 1;
        int wall_l = 0;
        int wall_t = 0;
        int wall_b = rows - 1;

        int val = 1;
        while (val < max + 1) {
            for (int k = wall_l; k < wall_r + 1;  k++){
                if (val > max)
                    return (body);
                body[wall_t][k] = val++;
            }
            ++wall_t;
            for (int k = wall_t; k < wall_b + 1 ; k++) {
                if (val > max)
                    return (body);
                body[k][wall_r] = val++;
            }
            --wall_r;
            for (int k = wall_r; k > wall_l - 1; k--) {
                if (val > max)
                    return (body);
                body[wall_b][k] = val++;
            }
            --wall_b;
            for (int k = wall_b; k > wall_t - 1; k--) {
                if (val > max)
                    return (body);
                body[k][wall_l] = val++;
            }
            ++wall_l;
        }
        return body;
    }


    public static void main(String[] args) {
        /*
            Should be:
                1   2   3   4
               10  11  12   5
                9   8   7   6
        */
        {
            int[][] spiral = Spiral.spiral(3, 4);

            for (int i = 0; i < spiral.length; i++) {
                for (int j = 0; j < spiral[i].length; j++) {
                    System.out.print(String.format("%4s", spiral[i][j]));
                }
                System.out.println();
            }
        }
        /*
            Should be:
               1   2   3
              10  11   4
               9  12   5
               8   7   6
        */
        {
            int[][] spiral = Spiral.spiral(4, 3);

            for (int i = 0; i < spiral.length; i++) {
                for (int j = 0; j < spiral[i].length; j++) {
                    System.out.print(String.format("%4s", spiral[i][j]));
                }
                System.out.println();
            }
        }
        /*
            Should be:
               1   2   3   4   5   6
              18  19  20  21  22   7
              17  28  29  30  23   8
              16  27  26  25  24   9
              15  14  13  12  11  10
        */
        {
            int[][] spiral = Spiral.spiral(5, 6);

            for (int i = 0; i < spiral.length; i++) {
                for (int j = 0; j < spiral[i].length; j++) {
                    System.out.print(String.format("%4s", spiral[i][j]));
                }
                System.out.println();
            }
        }
        /*
            Should be:
               1   2   3   4   5
              16  17  18  19   6
              15  24  25  20   7
              14  23  22  21   8
              13  12  11  10   9
        */
        {
            int[][] spiral = Spiral.spiral(5, 5);

            for (int i = 0; i < spiral.length; i++) {
                for (int j = 0; j < spiral[i].length; j++) {
                    System.out.print(String.format("%4s", spiral[i][j]));
                }
                System.out.println();
            }
        }
    }
}
