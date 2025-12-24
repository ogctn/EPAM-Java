package com.epam.rd.autotasks.array;

public class IntArrayUtil {

	public static int maximumDistance(int[] array) {

        if (array == null || array.length < 2)
            return (0);

        int first = 0, last = 0, max;

        max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                first = i;
                last = 0;
            }
            else if (array[i] == max)
                last = i;
        }
		return (last == 0 ? 0 : (last - first));

	}
}
