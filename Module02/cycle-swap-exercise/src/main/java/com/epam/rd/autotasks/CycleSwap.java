package com.epam.rd.autotasks;

import java.util.Arrays;

class CycleSwap {
    static void cycleSwap(int[] array) {
        if (array.length < 2)
            return;
        int tmp = array[array.length - 1];
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = tmp;
    }

    static void cycleSwap(int[] array, int shift) {
        if (array.length < 2)
            return;
        int[] tmp = new int[shift];
        System.arraycopy(array, array.length - shift, tmp, 0, shift);
        System.arraycopy(array, 0, array, shift, array.length - shift);
        System.arraycopy(tmp, 0, array, 0 , shift);
    }

    public static void main(String[] args) {
        {
            int[] array = new int[]{1, 3, 2, 7, 4};
            CycleSwap.cycleSwap(array);
            System.out.println(Arrays.toString(array)); // Should be [4, 1, 3, 2, 7]
        }
        {
            int[] array = new int[]{1, 3, 2, 7, 4};
            CycleSwap.cycleSwap(array, 2);
            System.out.println(Arrays.toString(array)); // Should be [7, 4, 1, 3, 2]
        }
        {
            int[] array = new int[]{1, 3, 2, 7, 4};
            CycleSwap.cycleSwap(array, 5);
            System.out.println(Arrays.toString(array)); // Should be [1, 3, 2, 7, 4]
        }
    }
}
