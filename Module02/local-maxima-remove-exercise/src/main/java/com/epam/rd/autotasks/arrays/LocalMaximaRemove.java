package com.epam.rd.autotasks.arrays;

import java.util.Arrays;

public class LocalMaximaRemove {

    public static void main(String[] args) {
        int[] array = new int[]{18, 1, 3, 6, 7, -5};
        System.out.println(Arrays.toString(removeLocalMaxima(array)));
    }

    public static int[] removeLocalMaxima(int[] array){

        int c = 0;
        if (array[0] > array[1])
            c++;
        for (int i = 1; i < array.length - 1; i++) {
            if (array[i] > array[i - 1] && array[i] > array[i + 1])
                c++;
        }
        if (array[array.length - 1] > array[array.length - 2])
            c++;

        int[] result = new int[array.length - c];
        c = 0;
        if (!(array[0] > array[1]))
            result[c++] = array[0];
        for (int i = 1; i < array.length - 1; i++) {
            if (!(array[i] > array[i - 1]) || !(array[i] > array[i + 1]))
                result[c++] = array[i];
        }
        if (!(array[array.length - 1] > array[array.length - 2]))
            result[c++] = array[array.length - 1];

        return(result);
    }
}
