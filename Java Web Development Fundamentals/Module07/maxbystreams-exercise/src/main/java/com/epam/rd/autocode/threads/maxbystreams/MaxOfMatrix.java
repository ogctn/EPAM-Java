package com.epam.rd.autocode.threads.maxbystreams;

import java.util.Arrays;

public class MaxOfMatrix {

    /**
     * Pauses current thread or{@code pause} millis
     *
     * @param pause time in millis
     */
    private static void pause(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String oneThreadSearch(int[][] matrix, int pause) {
        long before = System.currentTimeMillis();
        return (Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .reduce(Integer.MIN_VALUE, (a, b) -> {
                    pause(pause);
                    return (Math.max(a, b));
                })
                + " " + (System.currentTimeMillis() - before)
        );
    }

    public static String multipleThreadSearch(int[][] matrix, int pause) {
        long before = System.currentTimeMillis();
        return (Arrays.stream(matrix)
                .parallel()
                .mapToInt(r -> Arrays.stream(r)
                        .parallel()
                        .reduce(Integer.MIN_VALUE, (a, b) -> {
                            pause(pause);
                            return (Math.max(a, b));
                        }))
                .reduce(Integer.MIN_VALUE, (a, b) -> {
                    pause(pause);
                    return (Math.max(a, b));
                })
                + " " + (System.currentTimeMillis() - before)
        );
    }

}
