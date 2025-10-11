package com.epam.rd.autotasks.array;


public class IntArrayUtil {

	public static void swapEven(int[] array) {

        if (array == null || array.length == 0)
            return;

        int len = array.length;
        for (int i = 0; i < len/2; i++) {
            int tmp = array[i];
            if ((tmp % 2 == 0) && (array[len - 1 - i] % 2 == 0)) {
                array[i] = array[len - 1 - i];
                array[len - 1 - i] = tmp;
            }
        }
	}

}
